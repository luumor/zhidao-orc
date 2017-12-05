package com.daoxue.zhidao.ocr.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class HttpClientUtil {

	/**
	 * Get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String get(String url, List<NameValuePair> params) {
		String body = null;
		try {
			// Get请求
			HttpGet httpget = new HttpGet(url);
			// 设置参数
			String str = EntityUtils.toString(new UrlEncodedFormEntity(params, "UTF-8"));
			String f = url.indexOf("?") > -1 ? "&" : "?";
			httpget.setURI(new URI(httpget.getURI().toString() + f + str));
			// 发送请求
			CloseableHttpClient hc = getHttpClient();
			HttpResponse httpresponse = hc.execute(httpget);

			if (httpresponse.getStatusLine().getStatusCode() == 200) {
				// 获取返回数据
				HttpEntity entity = httpresponse.getEntity();
				body = EntityUtils.toString(entity);
				if (entity != null) {
					entity.consumeContent();
				}
			} else {
				body = "500";
			}
			hc.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * Get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String get(String url, List<NameValuePair> params, Map<String, String> headers) {
		String body = null;
		try {
			// Get请求
			HttpGet httpget = new HttpGet(url);
			// 设置参数
			String str = EntityUtils.toString(new UrlEncodedFormEntity(params, "UTF-8"));
			String f = url.indexOf("?") > -1 ? "&" : "?";
			httpget.setURI(new URI(httpget.getURI().toString() + f + str));
			// 发送请求
			CloseableHttpClient hc = getHttpClient();
			if (headers != null && headers.size() > 0) {
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					httpget.setHeader(entry.getKey(), entry.getValue());
				}
			}
			HttpResponse httpresponse = hc.execute(httpget);
			if (httpresponse.getStatusLine().getStatusCode() == 200) {
				// 获取返回数据
				HttpEntity entity = httpresponse.getEntity();
				body = EntityUtils.toString(entity);
				if (entity != null) {
					entity.consumeContent();
				}
			} else {
				body = "500";
			}
			hc.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return body;
	}

	/**
	 * // Post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, List<NameValuePair> params) {
		String body = "500";
		// Post请求
		HttpPost httppost = new HttpPost(url);
		// 设置参数
		UrlEncodedFormEntity req;
		try {
			req = new UrlEncodedFormEntity(params, "UTF-8");

			httppost.setEntity(req);
			// 发送请求
			CloseableHttpClient hc = getHttpClient();
			HttpResponse httpresponse = hc.execute(httppost);
			Integer respCode = httpresponse.getStatusLine().getStatusCode();
			if (respCode == 200) {
				// 获取返回数据
				HttpEntity entity = httpresponse.getEntity();
				body = EntityUtils.toString(entity);
				if (entity != null) {
					entity.consumeContent();
				} else {
					body = "200";
				}
				return body;
			}
			hc.close();
			return body;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	public static synchronized CloseableHttpClient getHttpClient() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(3000);// 连接池最大并发连接数
		cm.setDefaultMaxPerRoute(3000);// 单路由最大并发数
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
		return httpClient;

	}

	public static byte[] getRemoteFile(String path) {
		try {
			// new一个URL对象
			URL url = new URL(path);
			// 打开链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置请求方式为"GET"
			conn.setRequestMethod("GET");
			// 超时响应时间为5秒
			conn.setConnectTimeout(5 * 1000);
			// 通过输入流获取图片数据
			InputStream inStream;
			inStream = conn.getInputStream();
			return readInputStream(inStream);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
		// 得到图片的二进制数据，以二进制封装得到数据，具有通用性

	}

	public static byte[] readInputStream(InputStream inStream) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// 创建一个Buffer字符串
		byte[] buffer = new byte[1024];
		// 每次读取的字符串长度，如果为-1，代表全部读取完毕
		int len = 0;
		// 使用一个输入流从buffer里把数据读取出来
		while ((len = inStream.read(buffer)) != -1) {
			// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
			outStream.write(buffer, 0, len);
		}
		// 关闭输入流
		inStream.close();
		// 把outStream里的数据写入内存
		return outStream.toByteArray();
	}

	/**
	 * // Post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String postJSON(String url, String param) {
		String body = null;
		try {
			// Post请求
			HttpPost httppost = new HttpPost(url);
			httppost.addHeader("Content-type", "application/json; charset=utf-8");
			httppost.setEntity(new StringEntity(param, Charset.forName("UTF-8")));
			// 发送请求
			CloseableHttpClient hc = getHttpClient();
			HttpResponse httpresponse = hc.execute(httppost);
			// 获取返回数据
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			if (entity != null) {
				entity.consumeContent();
			}
		} catch (Exception e) {
			System.out.println(e.getClass().isAssignableFrom(new FileNotFoundException().getClass()));
		}
		return body;
	}

}
