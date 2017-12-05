package com.daoxue.zhidao.ocr.mapper;

import com.daoxue.zhidao.ocr.model.Token;

/**
 * @author Lum
 *
 */
public interface TokenMapper {

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
