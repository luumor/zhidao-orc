package com.daoxue.zhidao.ocr.service;

import com.daoxue.zhidao.ocr.model.Token;

public interface TokenService {
	/**
	 * 查询token
	 * 
	 * @param token
	 * @return
	 */
	public Token findToken(Token token);

	/**
	 * 插入token
	 * 
	 * @param token
	 * @return
	 */
	public Integer insertToken(Token token);

	/**
	 * 修改token
	 * 
	 * @param token
	 * @return
	 */
	public Integer updateToken(Token token);
}
