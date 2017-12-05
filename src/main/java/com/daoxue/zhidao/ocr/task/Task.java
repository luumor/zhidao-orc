package com.daoxue.zhidao.ocr.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.daoxue.zhidao.ocr.service.InitializeService;

/**
 * @author Lum
 *
 */
@Component
public class Task {

	private static final Logger logger = LoggerFactory.getLogger(Task.class);

	@Autowired
	private InitializeService initializeService;

	/**
	 * 五分钟后启动定时任务，查看数据库是否有遗漏未上传数据
	 */
	@Scheduled(initialDelay = 60000 * 5, fixedRate = 60000 * 5)
	public void recoverPaperData() {
		logger.info("定时任务启动..... | 开始扫描是否有遗漏数据。");
		initializeService.startOcrTask();
	}

}
