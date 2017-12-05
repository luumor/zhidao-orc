package com.daoxue.zhidao.ocr.service.impl;

import com.daoxue.zhidao.ocr.mapper.TokenMapper;
import com.daoxue.zhidao.ocr.model.Token;
import com.daoxue.zhidao.ocr.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TokenServiceImpl implements TokenService {

	@Autowired
	private TokenMapper tokenMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.daoxue.zhidao.ocr.service.TokenService#findToken(com.daoxue.zhidao.ocr.
	 * model.Token)
	 */
	@Override
	public Token findToken(Token token) {
		return tokenMapper.findToken(token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.daoxue.zhidao.ocr.service.TokenService#insertToken(com.daoxue.zhidao.ocr.
	 * model.Token)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Integer insertToken(Token token) {
		return tokenMapper.insertToken(token);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.daoxue.zhidao.ocr.service.TokenService#updateToken(com.daoxue.zhidao.ocr.
	 * model.Token)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public Integer updateToken(Token token) {
		return tokenMapper.updateToken(token);
	}

}
