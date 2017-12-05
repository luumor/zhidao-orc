package com.daoxue.zhidao.ocr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daoxue.zhidao.ocr.common.ResponseHelper;
import com.daoxue.zhidao.ocr.model.Token;
import com.daoxue.zhidao.ocr.service.TokenService;
import com.daoxue.zhidao.ocr.util.UuidUtil;

/**
 * @author Lum
 *
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthorityController {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(AuthorityController.class);

	@Autowired
	private TokenService tokenService;

	@PostMapping("/getRandom")
	@ResponseBody
	public Map<String, Object> getRandom(Token token) {

		Map<String, Object> map = ResponseHelper.successResp();
		String signStr = UuidUtil.get32UUID();
		map.put("signStr", signStr);
		token.setSignStr(signStr);
		tokenService.insertToken(token);
		return map;
	}

	@PostMapping("/getToken")
	@ResponseBody
	public Map<String, Object> paperList(Token token, HttpServletRequest req) {
		Map<String, Object> map = new HashMap<>();
		if (token == null || StringUtils.isBlank(token.getSignStr()) || StringUtils.isBlank(token.getEncryptStr())
				|| token.getTimestamp() == null) {
			map = ResponseHelper.successResp();
		} else {
			Token tokenReal = tokenService.findToken(token);

			if (tokenReal == null || StringUtils.isBlank(tokenReal.getRandomStr())) {
				map = ResponseHelper.noAuthResp();

			} else {
				if (DigestUtils.md5Hex(token.getSignStr() + tokenReal.getRandomStr() + token.getTimestamp())
						.equalsIgnoreCase(token.getEncryptStr())) {
					map = ResponseHelper.successResp();
					String tokenStr = UuidUtil.get32UUID();
					map.put("token", tokenStr);
					tokenReal.setToken(tokenStr);
					tokenService.updateToken(tokenReal);
				} else {
					map = ResponseHelper.noAuthResp();
				}
			}
		}
		return map;
	}

}
