package com.daoxue.zhidao.ocr.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.daoxue.zhidao.ocr.common.ResponseHelper;
import com.daoxue.zhidao.ocr.model.Token;
import com.daoxue.zhidao.ocr.service.TokenService;

/**
 * @author Lum
 *
 */
@Component
@Scope
public class LoginWebMvcInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.
	 * servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.
	 * http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.
	 * http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object arg2) throws Exception {

		System.out.println(req.getRequestURL().toString());
		if (req.getRequestURL().toString().contains("auth")) {
			return true;
		}
		String tokenParam = (String) req.getParameter("token");
		if (StringUtils.isBlank(tokenParam)) {
			resp.getWriter().write(JSON.toJSONString(ResponseHelper.missingArgsResp()));
			return false;
		}
		
		boolean flag = false;
		Token token = new Token();
		token.setToken(tokenParam);
		token = tokenService.findToken(token);
		if (token != null) {
			flag = true;
		}

		if (!flag) {
			resp.getWriter().write(JSON.toJSONString(ResponseHelper.noAuthResp()));
		}

		return flag;
	}

}
