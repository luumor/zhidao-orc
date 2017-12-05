package com.daoxue.zhidao.ocr.service.impl;

import com.daoxue.zhidao.ocr.mapper.PaperInfoMapper;
import com.daoxue.zhidao.ocr.model.PaperInfo;
import com.daoxue.zhidao.ocr.model.Token;
import com.daoxue.zhidao.ocr.service.PaperInfoService;
import com.daoxue.zhidao.ocr.service.TokenService;
import com.daoxue.zhidao.ocr.task.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaperInfoServiceImpl implements PaperInfoService {

	@Autowired
	private PaperInfoMapper paperInfoMapper;

	@Autowired
	private AsyncTask asyncTask;

	@Autowired
	private TokenService tokenService;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.daoxue.zhidao.ocr.service.PaperInfoService#findPaperList(com.daoxue.
	 * zhidao.ocr.model.PaperInfo)
	 */
	@Override
	public List<PaperInfo> findPaperList(PaperInfo paper) {
		return paperInfoMapper.findPaperList(paper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.daoxue.zhidao.ocr.service.PaperInfoService#insertPaper(com.daoxue.zhidao.
	 * ocr.model.PaperInfo)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Integer insertPaper(PaperInfo paper) {
		return paperInfoMapper.insertPaper(paper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.daoxue.zhidao.ocr.service.PaperInfoService#updatePaper(com.daoxue.zhidao.
	 * ocr.model.PaperInfo)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Integer updatePaper(PaperInfo paper) {
		return paperInfoMapper.updatePaper(paper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.daoxue.zhidao.ocr.service.PaperInfoService#findGroupPapers(com.daoxue.
	 * zhidao.ocr.model.PaperInfo)
	 */
	// @Override
	// public List<PaperInfo> findGroupPapers(PaperInfo paper) {
	// return paperInfoMapper.findGroupPapers(paper);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.daoxue.zhidao.ocr.service.PaperInfoService#insertPaperBatch(com.daoxue.
	 * zhidao.ocr.model.PaperInfo)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Integer insertPaperBatch(PaperInfo paper) throws InterruptedException, ExecutionException {

		Token token = new Token();
		token.setToken(paper.getToken());
		token = tokenService.findToken(token);
		token.setStatus(0);
		tokenService.updateToken(token);
		// 启动 识别任务
		asyncTask.ocrTask(paper);
		return paperInfoMapper.insertPaperBatch(paper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.daoxue.zhidao.ocr.service.PaperInfoService#batchUpdate(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Integer batchUpdate(List<Integer> list, Integer status) {
		return paperInfoMapper.batchUpdate(list, status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.daoxue.zhidao.ocr.service.PaperInfoService#updatePaperBatch(com.daoxue.
	 * zhidao.ocr.model.PaperInfo)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Integer updatePaperBatch(PaperInfo paper) {
		return paperInfoMapper.updatePaperBatch(paper);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.daoxue.zhidao.ocr.service.PaperInfoService#findPaperBatches(com.daoxue.
	 * zhidao.ocr.model.PaperInfo)
	 */
	@Override
	public List<PaperInfo> findPaperBatches(PaperInfo paper) {
		return paperInfoMapper.findPaperBatches(paper);
	}

}
