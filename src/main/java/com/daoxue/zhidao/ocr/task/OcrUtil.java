package com.daoxue.zhidao.ocr.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.daoxue.zhidao.ocr.common.ResponseCodeMsg;
import com.daoxue.zhidao.ocr.model.PaperInfo;
import com.daoxue.zhidao.ocr.util.HttpClientUtil;
import com.daoxue.zhidao.ocr.util.PropertiesUtil;

import Decoder.BASE64Encoder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Lum
 *
 */
public class OcrUtil<T> implements Callable<List<PaperInfo>> {

	private String ocrUrl;

	private Logger logger = Logger.getLogger(OcrUtil.class);

	private List<PaperInfo> papers;

	public OcrUtil(List<PaperInfo> papers, String ocrUrl) {
		this.papers = papers;
		this.ocrUrl = ocrUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public List<PaperInfo> call() throws Exception {

		logger.info("call()方法被自动调用，任务的结果是：" + papers + "-=== " + Thread.currentThread().getName() + "-------"
				+ Thread.currentThread().getId());
		return OcrTask(papers, ocrUrl);
	}

	public List<PaperInfo> OcrTask(List<PaperInfo> papers, String ocrUrl) {

		logger.info("Current thread id is | " + Thread.currentThread().getId() + ";name is |"
				+ Thread.currentThread().getName() + ";task count is " + papers.size());

		// 避免反复创建对象
		// 正面答案
		JSONArray positiveArray = new JSONArray();
		// 反面答案
		JSONArray sideArray = new JSONArray();
		// 识别结果json对象
		JSONObject root = new JSONObject();
		for (int h = 0; h < papers.size(); h++) {
			// 清空json对象
			positiveArray.clear();
			sideArray.clear();
			root.clear();

			// hanvon OCR识别服务地址
			String service = PropertiesUtil.readConfigValue("HanvonAC.Service");
			// OSS答题卡图片访问地址
			String imgService = PropertiesUtil.readConfigValue("AliOSS.askUrl");
			// 循环获取file数组中得文件
			for (int i = 0; i < 2; i++) {

				String path = "";
				if (i == 0) {
					path = imgService + papers.get(h).getOneSide();
				}

				if (i == 1) {
					path = imgService + papers.get(h).getOtherSide();
				}

				// 识别
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("key", "HanvonTestDaoxueJiaoyu"));

				byte[] byteData = HttpClientUtil.getRemoteFile(path);
				// 文件下载失败，跳过下面逻辑
				if (byteData.length == 0) {
					root.put("message", "oss 下载文件失败！");
					root.put("code", ResponseCodeMsg.SERVICE_EXCEPTION_CODE);
					root.put("success", false);
					root.put("path", path);
					break;
				}

				params.add(new BasicNameValuePair("base64img", GetImageStr(byteData)));
				String result = HttpClientUtil.post(service, params);
				// 如果汉王识别失败，则跳过下面循环
				if (("500").equals(result)) {
					root.put("message", "获取hanvon 图片识别异常！");
					root.put("success", false);
					root.put("code", ResponseCodeMsg.SERVICE_EXCEPTION_CODE);
					continue;
				}

				System.out.println("原始识别结果===========" + result);
				try {
					// hanvon识别接口正常返回，设置状态为1;
					papers.get(h).setStatus(1);
					// 设置success为true
					root.put("success", true);
					JSONObject ocrResult = JSONObject.fromObject(result);

					// 识别异常
					if (ocrResult.containsKey("code") && ocrResult.getInt("code") > 0) {
						root.put("ocrErrorImg", path);
					} else {
						// 识别成功，判断正反面
						String examCode = ocrResult.getJSONObject("examCode").getString("value");
						String stuCode = ocrResult.getJSONObject("stuCode").getString("value");
						// 正面
						if (!examCode.equals("") || !stuCode.equals("")) {
							positiveArray = ocrResult.getJSONArray("answers");
							root.put("examCode", ocrResult.getJSONObject("examCode"));
							root.put("fullName", ocrResult.getJSONObject("fullName"));
							root.put("stuCode", ocrResult.getJSONObject("stuCode"));
							root.put("positiveImg", path);
							// 反面
						} else {
							sideArray = ocrResult.getJSONArray("answers");
							root.put("sideImg", path);
						}
					}
				} catch (Exception e) {
					root.put("message", "hanvon 图片识别返回值异常！");
					root.put("code", ResponseCodeMsg.SERVICE_EXCEPTION_CODE);
					continue;
				}
			}

			if (positiveArray == null || positiveArray.size() == 0) {
				root.put("message", "正面识别失败");

			} else {
				if (sideArray == null || sideArray.size() == 0) {
					root.put("message", "反面识别失败");
					// 补充反面2条数据
					sideArray = new JSONArray();
					JSONObject sideItem = new JSONObject();
					sideItem.put("value", "*");
					sideItem.put("score", new int[] { 0 });
					sideArray.add(sideItem);
					sideArray.add(sideItem);
				}
				// 拼接正反面答案
				int number = positiveArray.size();
				for (int i = 0; i < sideArray.size(); i++) {
					JSONObject item = sideArray.getJSONObject(i);
					item.put("number", number + (i + 1));
					positiveArray.add(item);
				}
				root.put("answers", positiveArray);
			}
			papers.get(h).setOcrResult(root.toString());
		}
		return papers;
	}

	// 将本地图片文件转成Base64字符串
	public String GetImageStr(byte[] data) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

}
