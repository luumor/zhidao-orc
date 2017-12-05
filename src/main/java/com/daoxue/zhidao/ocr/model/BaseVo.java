package com.daoxue.zhidao.ocr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lum
 *
 */
public class BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2408532730862941094L;

	private Integer id;
	
	private String token;

	private Date createTime;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
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

}
