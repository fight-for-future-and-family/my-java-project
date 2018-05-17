package com.hoolai.bi.report.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SpiderUtilities {

	private static Log log = LogFactory.getLog(SpiderUtilities.class);

	/**
	 * 抽取网页源代码
	 * 
	 * @param url
	 * @return
	 */
	public static byte[] getBytesByClient(String url) {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Referer", url);
		getMethod
				.setRequestHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 5.1; rv:6.0) Gecko/20100101 Firefox/6.0");
		getMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		getMethod.setRequestHeader("Connection", "close");
		getMethod
				.setRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(60000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);

		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}

			InputStream inputstream = getMethod.getResponseBodyAsStream();

			return IOUtils.toByteArray(inputstream);
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}

	/**
	 * Returns an InputStream from a connection. Retries 3 times.
	 * 
	 * @param connection
	 *            a connection
	 * @return an InputStream
	 * @since 26 novembre 2001
	 */
	public static InputStream getSafeInputStream(HttpURLConnection connection) {

		InputStream inputstream = null;

		for (int i = 0; i < 3;) {
			try {
				inputstream = connection.getInputStream();
				break;
			} catch (IOException ioexception1) {
				log.info("error opening connection " + ioexception1);
				i++;
			}
		}

		return inputstream;
	}

	public static InputStream getSafeInputStream(String url) {
		HttpURLConnection connection = getValidConnection(url);
		if (connection == null) {
			return null;
		}

		return getSafeInputStream(connection);
	}

	public static String getStringResource(HttpURLConnection connection,
			String charsetName) {
		// log.info("fetchURL started");

		InputStream inputstream = getSafeInputStream(connection);
		if (inputstream == null) {
			return null;
		}

		// load the Stream in a StringBuffer
		InputStreamReader isr = null;
		StringBuffer content = new StringBuffer();

		try {
			isr = new InputStreamReader(inputstream, charsetName);
			char buf[] = new char[1024];
			int cnt = 0;
			while ((cnt = isr.read(buf, 0, 1024)) != -1) {
				content.append(buf, 0, cnt);
			}
			isr.close();
			inputstream.close();
		} catch (IOException ioexception) {
			log.info(ioexception);
		}

		return content.toString();
	}

	/**
	 * 抽取网页源代码
	 * 
	 * @param url
	 * @return
	 */
	public static String getStringResource(String url) {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

		String resource = null;
		try {
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}
			resource = getMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
		}

		return resource;
	}

	/**
	 * 抽取网页源代码
	 * 
	 * @param url
	 * @return
	 */
	public static String getStringResource(String url, String charsetName) {
		HttpURLConnection connection = getValidConnection(url);
		if (connection == null) {
			return null;
		}
		return getStringResource(connection, charsetName);
	}

	public static HttpURLConnection getValidConnection(String url) {
		HttpURLConnection httpurlconnection = null;

		try {
			URLConnection urlconnection = new URL(url).openConnection();
			urlconnection.connect();

			if (!(urlconnection instanceof HttpURLConnection)) {
				log.info("Not an http connection: " + url);
				// urlconnection.disconnect();
				return null;
			}

			httpurlconnection = (HttpURLConnection) urlconnection;
			// httpurlconnection.setFollowRedirects(true);

			int responsecode = httpurlconnection.getResponseCode();

			switch (responsecode) {
			// here valid codes!
			case HttpURLConnection.HTTP_OK:
			case HttpURLConnection.HTTP_MOVED_PERM:
			case HttpURLConnection.HTTP_MOVED_TEMP:
				break;
			default:
				log.info("Invalid response code: " + responsecode + " " + url);
				httpurlconnection.disconnect();
				return null;
			}
		} catch (IOException ioexception) {
			log.info("unable to connect: " + ioexception);
			if (httpurlconnection != null) {
				httpurlconnection.disconnect();
			}
			return null;
		}

		return httpurlconnection;
	}

	/**
	 * 抽取网页源代码
	 * 
	 * @param url
	 * @return
	 */
	public static String getStringResourceByClient(String url,
			String charsetName) {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Referer", url);
		getMethod
				.setRequestHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 5.1; rv:6.0) Gecko/20100101 Firefox/6.0");
		getMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		getMethod.setRequestHeader("Connection", "close");
		getMethod
				.setRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(60000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);

		try {
			int statusCode = 0;
			for (int i = 0; i < 3; i++) {
				// 执行getMethod
				try {
					statusCode = httpClient.executeMethod(getMethod);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (statusCode == HttpStatus.SC_OK) {
					break;
				}
			}

			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}

			InputStream inputstream = getMethod.getResponseBodyAsStream();

			// load the Stream in a StringBuffer
			InputStreamReader isr = null;
			StringBuffer content = new StringBuffer();

			try {
				isr = new InputStreamReader(inputstream, charsetName);
				char buf[] = new char[1024];
				int cnt = 0;
				while ((cnt = isr.read(buf, 0, 1024)) != -1) {
					content.append(buf, 0, cnt);
				}
				isr.close();
				inputstream.close();

				return content.toString();
			} catch (IOException ioexception) {
				log.info(ioexception);
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}

	public static String getStringResourceByClient(String url, String referer,
			String charsetName) {
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("Referer", referer);
		getMethod
				.setRequestHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 5.1; rv:6.0) Gecko/20100101 Firefox/6.0");
		getMethod.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
		getMethod.setRequestHeader("Connection", "close");
		getMethod
				.setRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		getMethod.setRequestHeader("Accept-Language", "zh-cn");
		// 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(60000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(60000);

		try {
			int statusCode = 0;
			for (int i = 0; i < 3; i++) {
				// 执行getMethod
				try {
					statusCode = httpClient.executeMethod(getMethod);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (statusCode == HttpStatus.SC_OK) {
					break;
				}
			}

			if (statusCode != HttpStatus.SC_OK) {
				return null;
			}

			InputStream inputstream = getMethod.getResponseBodyAsStream();

			// load the Stream in a StringBuffer
			InputStreamReader isr = null;
			StringBuffer content = new StringBuffer();

			try {
				isr = new InputStreamReader(inputstream, charsetName);
				char buf[] = new char[1024];
				int cnt = 0;
				while ((cnt = isr.read(buf, 0, 1024)) != -1) {
					content.append(buf, 0, cnt);
				}
				isr.close();
				inputstream.close();

				return content.toString();
			} catch (IOException ioexception) {
				log.info(ioexception);
			}
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			e.printStackTrace();
		} finally {
			// 释放连接
			getMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return null;
	}

	/**
	 * Description of the Method
	 * 
	 * @param url
	 *            Description of Parameter
	 * @param basepath
	 *            Description of Parameter
	 * @return Description of the Returned Value
	 */
	public static File URLToLocalFile(File basepath, URL url) {

		String strPort;
		int port = url.getPort();
		if (port != -1) {
			strPort = ";" + port;
		} else {
			strPort = "";
		}

		String localpathname = url.getHost() + strPort + url.getPath();

		// il path assoluto locale (nella dir di output)
		File localfile = new File(basepath, localpathname);
		// log.info("localfile: " + localfile);

		return localfile;
	}

}
