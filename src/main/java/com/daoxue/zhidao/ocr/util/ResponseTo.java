package com.daoxue.zhidao.ocr.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import net.sf.json.JSONObject;

/**
 * @author FH Q313596790 修改时间：2015、12、11
 */
public class ResponseTo {

	/**
	 * 得到request对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	/**
	 * 输出response信息（JSON格式）
	 * 
	 * @param obj
	 * @param response
	 * @throws Exception
	 */
	public static void responseJson(Object obj, HttpServletResponse response) {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			if (obj == null) {
				pw.write(new JSONObject().toString());
			} else {
				pw.write(obj.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.flush();
			pw.close();
		}
	}

}
