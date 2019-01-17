package vk.framework.spring.util;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;

public class ServerUtils {
	public static String getRootDomain(HttpServletRequest request) {
		return request.getServerName().replaceAll(".*?\\.(.*?\\.[a-zA-Z]+)", "$1");
	}

	public static String getDoamin(HttpServletRequest request) throws Exception {
		String serverScheme = "http";
		String domain = "http://edu.khsa.or.kr";
		if (request != null) {
			serverScheme = request.getHeader("x-forwarded-proto");
			if (serverScheme == null || serverScheme.equals("")) {
				serverScheme = request.getScheme() != null ? request.getScheme() : serverScheme;
			}

			URI uri = new URI(request.getRequestURL().toString());
			domain = serverScheme + "://" + uri.getAuthority();
			domain = domain + (!request.getContextPath().equals("") ? "/" + request.getContextPath() : "");
		}

		return domain;
	}
}