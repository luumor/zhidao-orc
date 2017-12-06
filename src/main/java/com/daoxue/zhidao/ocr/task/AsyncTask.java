package com.daoxue.zhidao.ocr.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daoxue.zhidao.ocr.model.PaperInfo;
import com.daoxue.zhidao.ocr.service.PaperInfoService;
import com.daoxue.zhidao.ocr.util.HttpClientUtil;
import com.google.common.collect.Lists;

/**
 * @author Lum
 *
 */
@Component
public class AsyncTask {

	private static final int THREAD_NUM = 10;

	private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);		

	private Logger logger = Logger.getLogger(AsyncTask.class);

	@Value("${HanvonAC.Service}")
	private String ocrUrl;

	@Autowired
	private PaperInfoService paperInfoService;

	@Value("${SCORE_MODIFY_URL}")
	private String SCORE_MODIFY_URL;

	@Async
	public void ocrTask(PaperInfo paperParam) {
//		ExecutorService executorService = Executors.newCachedThreadPool();
		
//		 ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);		
		
		paperParam.setStatus(0);
		paperParam.setId(null);
		List<PaperInfo> papers = paperInfoService.findPaperList(paperParam);
		// 如果没有 未上传的数据则直接提交
		if (papers == null || papers.size() == 0) {
			paperParam.setStatus(1);
			paperInfoService.updatePaperBatch(paperParam);
			return;
		}

		// 按线程数对list进行分片
		List<List<PaperInfo>> papersPart = Lists.partition(papers, THREAD_NUM);

		// 进行异步任务列表
		List<Future<List<PaperInfo>>> resultList = new ArrayList<Future<List<PaperInfo>>>();

		for (int i = 0; i < papersPart.size(); i++) {



			// 创建一个异步任务
//			@SuppressWarnings({ "rawtypes", "unchecked" })
//			FutureTask<List<PaperInfo>> futureTask = new FutureTask<List<PaperInfo>>(
//					new OcrUtil(papersPart.get(i), ocrUrl));
//			futureTasks.add(futureTask);
//			executorService.submit(futureTask);
			
			
			
            @SuppressWarnings({ "unchecked", "rawtypes" })
			Future<List<PaperInfo>> future = executorService.submit(new OcrUtil(papersPart.get(i), ocrUrl));
            // 将任务执行结果存储到List中  
            resultList.add(future); 
		}
//		 executorService.shutdown();  
		// 批量更新数据
		List<Integer> paperIds = new ArrayList<>();

		// 批量上传数据
		JSONArray jsonArr = new JSONArray();

		boolean flag = true;
		for (Future<List<PaperInfo>> future : resultList) {
			try {
				for (int i = 0; i < future.get().size(); i++) {
					PaperInfo paper = future.get().get(i);
					// 数据存进list批量更新数据库
					paperInfoService.updatePaper(paper);
					logger.info("识别结果是 | " + paper.getOcrResult());
					if (!StringUtils.isBlank(paper.getOcrResult())) {
						JSONObject json = JSONObject.parseObject(paper.getOcrResult());
						// 判断该批数据是否有异常信息（1.未正常从oss下载 2.未正常调用hanvan接口）
						if (flag) {
							flag = json.getBooleanValue("success");
						}
						if (!StringUtils.isBlank(json.getString("answers"))
								&& !StringUtils.isBlank(json.getString("examCode"))) {
							paperIds.add(paper.getId());
							// 数据存入json数据，批量上传
							jsonArr.add(json);
						}
					}
				}
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 开始批量上传
		String result = HttpClientUtil.postJSON(SCORE_MODIFY_URL, jsonArr.toJSONString());
		// 如果上传接口无异常，则更新数据
		if (!StringUtils.isBlank(result) && paperIds.size() > 0) {
			// 批量更新已上传数据的信息
			paperInfoService.batchUpdate(paperIds, 2);

			// 如果所有数据均得到识别，且得到正常上传；更新该批次信息
			if (flag) {
				paperParam.setStatus(1);
				paperInfoService.updatePaperBatch(paperParam);
			}

		}
//		shutdownAndAwaitTermination(executorService);

	}

	void shutdownAndAwaitTermination(ExecutorService pool) {
		pool.shutdown(); // Disable new tasks from being submitted
//		try {
//			// Wait a while for existing tasks to terminate
//			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
//				pool.shutdownNow(); // Cancel currently executing tasks
//				// Wait a while for tasks to respond to being cancelled
//				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
//					System.err.println("Pool did not terminate");
//			}
//		} catch (InterruptedException ie) {
//			// (Re-)Cancel if current thread also interrupted
//			pool.shutdownNow();
//			// Preserve interrupt status
//			Thread.currentThread().interrupt();
//		}
	}
}
