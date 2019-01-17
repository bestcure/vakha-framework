package vk.framework.spring.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.CookieGenerator;

import vk.framework.spring.util.ServerUtils;

public class SessionCookieUtil {
	public static void setSessionAttribute(HttpServletRequest request, String keyStr, String valStr) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute(keyStr, valStr);
	}

	public static void setSessionAttribute(HttpServletRequest request, String keyStr, Object obj) throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute(keyStr, obj);
	}

	public static Object getSessionAttribute(HttpServletRequest request, String keyStr) throws Exception {
		HttpSession session = request.getSession();
		return session.getAttribute(keyStr);
	}

	public static String getSessionValuesString(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String returnVal = "";

		String sessionKey;
		for (Enumeration e = session.getAttributeNames(); e.hasMoreElements(); returnVal = returnVal + "[" + sessionKey
				+ " : " + session.getAttribute(sessionKey) + "]") {
			sessionKey = (String) e.nextElement();
		}

		return returnVal;
	}

	public static void removeSessionAttribute(HttpServletRequest request, String keyStr) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute(keyStr);
	}

	public static void invalidateSession(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		session.invalidate();
	}

	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieNm,
			String cookieVal, int minute) throws UnsupportedEncodingException {
		setCookie(request, response, cookieNm, cookieVal, minute, false);
	}

	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieNm,
			String cookieVal) throws UnsupportedEncodingException {
		setCookie(request, response, cookieNm, cookieVal, 0, false);
	}

	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieNm,
			String cookieVal, boolean isDomain) throws UnsupportedEncodingException {
		setCookie(request, response, cookieNm, cookieVal, 0, isDomain);
	}

	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieNm,
			String cookieVal, String path, boolean isDomain) throws UnsupportedEncodingException {
		setCookie(request, response, cookieNm, cookieVal, path, isDomain ? ServerUtils.getRootDomain(request) : "");
	}

	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieNm,
			String cookieVal, int minute, boolean isDomain) throws UnsupportedEncodingException {
		String cookieValue = URLEncoder.encode(cookieVal, "utf-8");
		CookieGenerator cookie = new CookieGenerator();
		if (isDomain) {
			cookie.setCookieDomain(ServerUtils.getRootDomain(request));
		}

		if (minute > 0) {
			cookie.setCookieMaxAge(Integer.valueOf(60 * minute));
		}

		cookie.setCookieName(cookieNm);
		cookie.setCookiePath("/");
		cookie.addCookie(response, cookieValue);
	}

	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String cookieNm,
			String cookieVal, String path, String domain) throws UnsupportedEncodingException {
		String cookieValue = URLEncoder.encode(cookieVal, "utf-8");
		CookieGenerator cookie = new CookieGenerator();
		if (StringUtils.isNoneEmpty(new CharSequence[]{domain})) {
			cookie.setCookieDomain(domain);
		}

		cookie.setCookieName(cookieNm);
		cookie.setCookiePath(path);
		cookie.addCookie(response, cookieValue);
	}

	public static String getCookie(HttpServletRequest request, String cookieNm) throws Exception {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return "";
		} else {
			String cookieValue = null;

			for (int i = 0; i < cookies.length; ++i) {
				if (cookieNm.equals(cookies[i].getName())) {
					cookieValue = URLDecoder.decode(cookies[i].getValue(), "utf-8");
					break;
				}
			}

			return cookieValue;
		}
	}

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieNm)
			throws UnsupportedEncodingException {
		CookieGenerator cookie = new CookieGenerator();
		cookie.setCookieName(cookieNm);
		cookie.setCookieMaxAge(Integer.valueOf(0));
		cookie.setCookiePath("/");
		cookie.addCookie(response, (String) null);
	}

	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieNm,
			boolean isDomain) throws UnsupportedEncodingException {
		CookieGenerator cookie = new CookieGenerator();
		cookie.setCookieName(cookieNm);
		if (isDomain) {
			cookie.setCookieDomain(ServerUtils.getRootDomain(request));
		}

		cookie.setCookieMaxAge(Integer.valueOf(0));
		cookie.setCookiePath("/");
		cookie.addCookie(response, (String) null);
	}
}