package com.daoxue.zhidao.ocr.common;

/**
 * @author Lum
 *
 */
public interface ResponseCodeMsg {

	/**
	 * 响应成功
	 */
	Integer SUCCESS_CODE = 200;

	String SUCCESS_MSG = "请求成功";

	/**
	 * 缺失参数
	 */
	Integer MISSING_ARGS_CODE = 1001;

	String MISSING_ARGS_MSG = "缺失参数！";

	/**
	 * 无权限
	 */
	Integer NO_AUTHORITY_CODE = 1002;

	String NO_AUTHORITY_MSG = "无权限进行此操作！";
	
	/**
	 * 服务器异常
	 */
	Integer SERVICE_EXCEPTION_CODE = 500;

	String SERVICE_EXCEPTION_MSG = "服务器异常！";
	
	/**
	 * 文件下载异常
	 */
	Integer DOWNFILE_FAIL_CODE = 1010;

	String DOWNFILE_FAIL_MSG = "文件下载异常！";
}
