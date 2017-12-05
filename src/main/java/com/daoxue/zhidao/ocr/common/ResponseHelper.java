package com.daoxue.zhidao.ocr.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lum
 *
 */
public class ResponseHelper {

	public static Map<String, Object> map = new HashMap<>();

	/**
	 * 响应成功
	 * 
	 * @return
	 */
	public static Map<String, Object> successResp() {
		map.clear();
		map.put("code", ResponseCodeMsg.SUCCESS_CODE);
		map.put("msg", ResponseCodeMsg.SUCCESS_MSG);
		return map;
	}

	public static Map<String, Object> successResp(String msg) {
		map.clear();
		map.put("code", ResponseCodeMsg.SUCCESS_CODE);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 缺失参数
	 * 
	 * @return
	 */
	public static Map<String, Object> missingArgsResp() {
		map.clear();
		map.put("code", ResponseCodeMsg.MISSING_ARGS_CODE);
		map.put("msg", ResponseCodeMsg.MISSING_ARGS_MSG);
		return map;
	}

	/**
	 * 无权限
	 * 
	 * @return
	 */
	public static Map<String, Object> noAuthResp() {
		map.clear();
		map.put("code", ResponseCodeMsg.NO_AUTHORITY_CODE);
		map.put("msg", ResponseCodeMsg.NO_AUTHORITY_MSG);
		return map;
	}

	/**
	 * 服务器异常
	 * 
	 * @return
	 */
	public static Map<String, Object> serviceExceptionResp() {
		map.clear();
		map.put("code", ResponseCodeMsg.SERVICE_EXCEPTION_CODE);
		map.put("msg", ResponseCodeMsg.SERVICE_EXCEPTION_MSG);
		return map;
	}
}
