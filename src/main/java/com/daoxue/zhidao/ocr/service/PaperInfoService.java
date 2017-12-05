package com.daoxue.zhidao.ocr.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.daoxue.zhidao.ocr.model.PaperInfo;

public interface PaperInfoService {

	/**
	 * 查询试卷列表
	 * 
	 * @return
	 */
	public List<PaperInfo> findPaperList(PaperInfo paper);

	/**
	 * 插入试卷
	 * 
	 * @param paper
	 * @return
	 */
	public Integer insertPaper(PaperInfo paper);

	/**
	 * 修改试卷
	 * 
	 * @param paper
	 * @return
	 */
	public Integer updatePaper(PaperInfo paper);

	// /**
	// * 查询分组数据结果
	// *
	// * @param paper
	// * @return
	// */
	// public List<PaperInfo> findGroupPapers(PaperInfo paper);

	/**
	 * 新增试卷批次
	 * 
	 * @param paper
	 * @return
	 */
	public Integer insertPaperBatch(PaperInfo paper) throws InterruptedException, ExecutionException;

	/**
	 * 
	 * @param 批量更新数据
	 * @return
	 */
	public Integer batchUpdate(List<Integer> list, Integer status);

	/**
	 * 修改试卷批次状态
	 * 
	 * @param paper
	 * @return
	 */
	public Integer updatePaperBatch(PaperInfo paper);

	/**
	 * 查询批次信息
	 * 
	 * @param paper
	 * @return
	 */
	public List<PaperInfo> findPaperBatches(PaperInfo paper);
}
