package com.daoxue.zhidao.ocr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.daoxue.zhidao.ocr.model.PaperInfo;

/**
 * @author Lum
 *
 */
public interface PaperInfoMapper {

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

	/**
	 * 查询分组数据结果
	 * 
	 * @param paper
	 * @return
	 */
//	public List<PaperInfo> findGroupPapers(PaperInfo paper);

	/**
	 * 
	 * @param 批量更新数据
	 * @return
	 */
	public Integer batchUpdate(@Param("list") List<Integer> list, @Param("status") Integer status);

	/**
	 * 新增试卷批次
	 * 
	 * @param paper
	 * @return
	 */
	public Integer insertPaperBatch(PaperInfo paper);

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
