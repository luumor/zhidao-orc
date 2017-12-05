package com.daoxue.zhidao.ocr.model;

/**
 * @author Lum
 *
 */
public class Token extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -577308973872497743L;

	private Long timestamp;

	private String signStr;

	private String randomStr;

	private String encryptStr;
	
	private Integer status;

	/**
	 * @return the signStr
	 */
	public String getSignStr() {
		return signStr;
	}

	/**
	 * @param signStr
	 *            the signStr to set
	 */
	public void setSignStr(String signStr) {
		this.signStr = signStr;
	}

	/**
	 * @return the randomStr
	 */
	public String getRandomStr() {
		return randomStr;
	}

	/**
	 * @param randomStr
	 *            the randomStr to set
	 */
	public void setRandomStr(String randomStr) {
		this.randomStr = randomStr;
	}

	/**
	 * @return the encryptStr
	 */
	public String getEncryptStr() {
		return encryptStr;
	}

	/**
	 * @param encryptStr
	 *            the encryptStr to set
	 */
	public void setEncryptStr(String encryptStr) {
		this.encryptStr = encryptStr;
	}

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

}
