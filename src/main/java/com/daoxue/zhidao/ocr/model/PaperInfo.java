package com.daoxue.zhidao.ocr.model;

import java.util.Date;

/**
 * @author Lum
 *
 */
public class PaperInfo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7050395199928529049L;

	/**
	 * 正面
	 */
	private String oneSide;


	/**
	 * 反面
	 */
	private String otherSide;

	/**
	 * 考试编号
	 */
	private String number;

	/**
	 * 是否已经识别
	 */
	private Integer status;

	/**
	 * ocr识别结果
	 */
	private String ocrResult;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the oneSide
	 */
	public String getOneSide() {
		return oneSide;
	}

	/**
	 * @param oneSide
	 *            the oneSide to set
	 */
	public void setOneSide(String oneSide) {
		this.oneSide = oneSide;
	}

	/**
	 * @return the otherSide
	 */
	public String getOtherSide() {
		return otherSide;
	}

	/**
	 * @param otherSide
	 *            the otherSide to set
	 */
	public void setOtherSide(String otherSide) {
		this.otherSide = otherSide;
	}

	/**
	 * 
	 */
	public PaperInfo() {
		super();
	}

	/**
	 * @param status
	 */
	public PaperInfo(Integer status) {
		super();
		this.status = status;
	}

	/**
	 * @return the ocrResult
	 */
	public String getOcrResult() {
		return ocrResult;
	}

	/**
	 * @param ocrResult
	 *            the ocrResult to set
	 */
	public void setOcrResult(String ocrResult) {
		this.ocrResult = ocrResult;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PaperInfo [oneSide=" + oneSide + ", otherSide=" + otherSide + ", number=" + number + ", status="
				+ status + ", ocrResult=" + ocrResult + ", createTime=" + createTime + "]";
	}

	
	
	
}
