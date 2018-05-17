package com.hoolai.bi.report.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author 李志忠
 * 
 */
public final class HttpUtil {

	private static Log log = LogFactory.getLog(HttpUtil.class);

	/**
	 * 是否为爬虫
	 * 
	 * @param ua
	 * @return
	 */
	public boolean isSpider(String userAgent) {
		if (userAgent.indexOf("bot") != -1 || userAgent.indexOf("spider") != -1
				|| userAgent.indexOf("Yahoo! Slurp") != -1)
			return true;
		else
			return false;
	}

	/**
	 * 设置cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
		
		return;
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 * 将cookie封装到Map里面
	 * 
	 * @param request
	 * @return
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 获得请求的路径
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getBasePath(HttpServletRequest request) {
		StringBuffer basePath = new StringBuffer(request.getScheme()).append(
				"://").append(request.getServerName());
		if (request.getServerPort() != 80) {
			basePath.append(":").append(request.getServerPort());
		}
		basePath.append(request.getContextPath());

		return basePath.toString();
	}

	/**
	 * 获取ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串Ｉｐ值
		// 是取X-Forwarded-For中第一个非unknown的有效IP字符串
		String[] str = ip.split(",");
		if (str != null && str.length > 1) {
			ip = str[0];
		}
		return ip;
	}

	/**
	 * 查询字符串
	 * 
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getQueryString(HttpServletRequest request,
			final String name, final String defaultValue) {
		String characterEncoding = request.getCharacterEncoding();
		String method = request.getMethod();
		String value = request.getParameter(name);
		boolean isGet = StringUtils.isNotEmpty(method)
				&& method.equalsIgnoreCase("get");
		boolean isUTF8 = StringUtils.isNotEmpty(characterEncoding)
				&& (characterEncoding.equalsIgnoreCase("UTF-8") || characterEncoding
						.equalsIgnoreCase("utf8"));
		if (StringUtils.isNotEmpty(value) && isGet && isUTF8) {
			try {
				value = new String(value.getBytes("iso8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (value == null) {
			value = (String) request.getAttribute(name);
		}
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * 获取int类型参数
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static int getRequestInt(HttpServletRequest request,
			final String name) {
		return HttpUtil.getRequestInt(request, name, 0);
	}

	/**
	 * 获取int类型参数
	 * 
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static int getRequestInt(HttpServletRequest request,
			final String name, int defaultValue) {
		String value = HttpUtil.getRequestString(request, name, null);
		if (value == null)
			return defaultValue;

		try {
			return Integer.parseInt(value.trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static long getRequestLong(HttpServletRequest request,
			final String name) {
		return HttpUtil.getRequestLong(request, name, 0);
	}

	public static long getRequestLong(HttpServletRequest request,
			final String name, long defaultValue) {
		String value = HttpUtil.getRequestString(request, name, null);
		if (value == null)
			return defaultValue;
		return NumberUtils.toLong(value, defaultValue);
	}

	/**
	 * 获取String类型参数
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getRequestString(HttpServletRequest request,
			final String name) {
		return HttpUtil.getRequestString(request, name, null);
	}

	/**
	 * 获取String类型参数
	 * 
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getRequestString(HttpServletRequest request,
			final String name, final String defaultValue) {
		String value = request.getParameter(name);
		if (value == null) {
			value = (String) request.getAttribute(name);
		}
		if (value == null) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * 封装HttpServletRequest.getRequestURI,返回小写URI
	 * 
	 * @param req
	 * @return
	 */
	public static String getRequestURI(HttpServletRequest req) {
		String uri = req.getRequestURI();
		if (StringUtils.isNotEmpty(uri))
			return uri.toLowerCase();

		return uri;
	}

	/**
	 * Returns an InputStream from a connection. Retries 3 times.
	 * 
	 * @param connection
	 * @return
	 */
	public static InputStream getSafeInputStream(HttpURLConnection connection) {
		InputStream inputstream = null;

		for (int i = 0; i < 3;) {
			try {
				inputstream = connection.getInputStream();
				break;
			} catch (IOException ioexception1) {
				log.error("error opening connection " + ioexception1);
				i++;
			}
		}

		return inputstream;
	}

	/**
	 * Returns an InputStream from a connection. Retries 3 times.
	 * 
	 * @param url
	 * @return
	 */
	public static InputStream getSafeInputStream(String url) {
		HttpURLConnection httpurlconnection = null;
		InputStream inputstream = null;
		try {
			httpurlconnection = getValidConnection(new URL(url));
			inputstream = getSafeInputStream(httpurlconnection);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.error(e);
			return null;
		}

		return inputstream;
	}

	/**
	 * 验证是否登录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Object getSession(HttpServletRequest request, String attr) {
		HttpSession session = request.getSession();
		return session.getAttribute(attr);
	}

	/**
	 * 验证并返回HttpURLConnection
	 * 
	 * @param url
	 * @return
	 */
	public static HttpURLConnection getValidConnection(URL url) {
		HttpURLConnection httpurlconnection = null;

		try {
			URLConnection urlconnection = url.openConnection();
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
			log.error("unable to connect: " + ioexception);
			if (httpurlconnection != null) {
				httpurlconnection.disconnect();
			}
			return null;
		}

		return httpurlconnection;
	}
	
	public static HttpParams getHttpParams(String url){
		return new HttpParams(url);
	}
	
	public static HttpParams getHttpParams(String url,String decoding){
		return new HttpParams(url,decoding);
	}

}
