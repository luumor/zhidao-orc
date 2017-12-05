package com.daoxue.zhidao.ocr.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.daoxue.zhidao.ocr.common.ResponseHelper;
import com.daoxue.zhidao.ocr.model.PaperInfo;
import com.daoxue.zhidao.ocr.service.PaperInfoService;
import com.daoxue.zhidao.ocr.util.OSSClientUtil;
import com.daoxue.zhidao.ocr.util.ResponseTo;
import com.daoxue.zhidao.ocr.util.UuidUtil;

/**
 * 汉王答题卡识别
 * 
 * @author yindong0720
 *
 */
@Controller
@RequestMapping(value = "/hanvon")
public class HanvonController {

	@Autowired
	private PaperInfoService paperInfoService;

	// @Value("${HanvonAC.Service}")
	// private String imgService;

	// @Value("${AliOSS.askUrl}")
	// private String service;

	private Logger logger = Logger.getLogger(HanvonController.class);

	@RequestMapping("/paperList")
	@ResponseBody
	public List<PaperInfo> paperList(PaperInfo paper) {
		List<PaperInfo> papers = paperInfoService.findPaperList(paper);
		return papers;
	}

	@RequestMapping("/addPaper")
	@ResponseBody
	public PaperInfo addPaper(PaperInfo paper) {
		paperInfoService.insertPaper(paper);
		return paper;
	}

	@RequestMapping("/updatePaper")
	@ResponseBody
	public PaperInfo updatePaper(PaperInfo paper) {
		paperInfoService.updatePaper(paper);
		return paper;
	}

	/**
	 * 答题卡识别
	 * 
	 * @return
	 */
	@RequestMapping(value = "/answerCardOcr")
	public void answerCardOcr(@RequestParam("files") MultipartFile[] files, PaperInfo paper,
			HttpServletResponse response) {
		
		// 

		logger.info("答题卡开始上传！");
		JSONObject root = new JSONObject();
		boolean success = false;
		String message = "";
		try {
			// 判断file数组不能为空并且长度大于0
			if (files != null && files.length != 2) {
				// 循环获取file数组中得文件
				if (paper == null) {
					paper = new PaperInfo();
				}
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					// 保存文件
					String filename = file.getOriginalFilename();
					String suffix = filename.substring(filename.lastIndexOf("."));
					String path = "answerCard/" + UuidUtil.get32UUID() + suffix;
					// 上传答题卡图片到OSS
					OSSClientUtil.putObject(path, file.getInputStream());

					// 插入数据库
					if (i == 0) {
						paper.setOneSide(path);
					}
					if (i == 1) {
						paper.setOtherSide(path);

						if (!StringUtils.isBlank(paper.getOneSide()) && !StringUtils.isBlank(paper.getOtherSide())) {
							paperInfoService.insertPaper(paper);
							message = "试卷上传成功！";
							success = true;
						} else {
							message = "试卷上传失败！";
						}
					}

				}
			} else {
				message = "没有检测到答题卡图片";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
			message = e.getMessage();
		} finally {
			root.put("message", message);
			root.put("success", success);
			ResponseTo.responseJson(root, response);
		}
	}

	@RequestMapping(value = "/startOcrTask")
	@ResponseBody
	public Map<String, Object> startOcrTask(PaperInfo paper, HttpServletResponse response) {

		Map<String, Object> map = null;

		if (paper != null && !StringUtils.isBlank(paper.getNumber())) {
			// 插入数据返回id
			try {
				paper.setId(paperInfoService.insertPaperBatch(paper));
				map = ResponseHelper.successResp("试卷开始识别");
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				logger.error("ocr error | " + e);
				map = ResponseHelper.serviceExceptionResp();
			}

		} else {
			map = ResponseHelper.missingArgsResp();
			logger.info("startOcrTask missing args | " + paper);
		}

		return map;
	}

}
