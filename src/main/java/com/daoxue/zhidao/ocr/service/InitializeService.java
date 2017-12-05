package com.daoxue.zhidao.ocr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daoxue.zhidao.ocr.common.ResponseHelper;
import com.daoxue.zhidao.ocr.model.PaperInfo;
import com.daoxue.zhidao.ocr.task.AsyncTask;

/**
 * 如果有未完成的任务，则启动后开始执行任务
 * 
 * @author Lum
 *
 */
@Service
public class InitializeService implements InitializingBean {

	@Autowired
	private PaperInfoService paperInfoService;

	@Autowired
	private AsyncTask asyncTask;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		startOcrTask();
	}

	public Map<String, Object> startOcrTask() {

		Map<String, Object> map = null;

		PaperInfo paper = new PaperInfo(0);

		List<PaperInfo> paperBatches = paperInfoService.findPaperBatches(paper);

		if (paperBatches == null || paperBatches.size() < 1) {
			return ResponseHelper.successResp("没有未识别的试卷批次！");
		}

		for (int i = 0; i < paperBatches.size(); i++) {
			asyncTask.ocrTask(paperBatches.get(i));
		}
		map = ResponseHelper.successResp("试卷开始识别");

		return map;
	}

}
