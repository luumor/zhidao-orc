package com.daoxue.zhidao.ocr.util;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;

/**
 * OSS util
 * @author yindong
 * @date 2016年3月9日
 */
public class OSSClientUtil {

	//唯一实例 
	private static OSSClient ossClient=null;
	public static final String askUrl=PropertiesUtil.readConfigValue("AliOSS.askUrl").toString();
	private static final String endpoint = PropertiesUtil.readConfigValue("AliOSS.Endpoint").toString();
	private static final String accessKeyId = PropertiesUtil.readConfigValue("AliOSS.Access.Key.ID").toString();
	private static final String accessKeySecret = PropertiesUtil.readConfigValue("AliOSS.Access.Key.Secret").toString();
	private static final String bucketName = PropertiesUtil.readConfigValue("AliOSS.Bucket.Name").toString();

	public static OSSClient GetInstance(){
		if (ossClient == null){ 
			ClientConfiguration conf = new ClientConfiguration();
			conf.setSupportCname(true);
			conf.setMaxConnections(100);
			conf.setConnectionTimeout(5000);
			conf.setMaxErrorRetry(3);
			conf.setSocketTimeout(2000);
			return new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
		} 
		return ossClient;
	}

	/**
	 * 检测OSS是否存在当前Object
	 * @param key
	 * @return
	 */
	public static boolean doesObject(String key){
		return GetInstance().doesObjectExist(bucketName, key);
	}

	/**
	 * 检测OSS是否存在该文件，如果不存在，则从源站取图，将此图存至OSS
	 * @param key OSS bucketName下路径
	 * @param path 源资源路径
	 */
	public static boolean checkOSSFile(String key,String path) {
		boolean success=false;
		try {
			if(!doesObject(key)){
				//new一个URL对象  
				URL url = new URL(path);
				//打开链接  
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
				//设置请求方式为"GET"  
				conn.setRequestMethod("GET");
				//超时响应时间为5秒  
				conn.setConnectTimeout(5 * 1000);
				//通过输入流获取图片数据  
				InputStream inStream = conn.getInputStream();

				GetInstance().putObject(bucketName, key,  inStream);
				success=true;
			}else{
				success=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GetInstance().shutdown();
		}
		return success;
	}



	public static boolean putObject(String key,InputStream inStream) {
		boolean success=false;
		try {
			GetInstance().putObject(bucketName, key, inStream);
			success=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GetInstance().shutdown();
		}
		return success;
	}
	

	public static boolean putFile(String key,File file) {
		boolean success=false;
		try {
			GetInstance().putObject(bucketName, key,file);
			success=true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GetInstance().shutdown();
		}
		return success;
	}

}
