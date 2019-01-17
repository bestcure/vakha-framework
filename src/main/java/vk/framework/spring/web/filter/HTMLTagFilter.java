package vk.framework.spring.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;

import vk.framework.spring.web.filter.HTMLTagFilterRequestWrapper;

public class HTMLTagFilter implements Filter {
	private List<String> exceptionUrl;

	public HTMLTagFilter(String... exceptionUrlPattern) {
		this.exceptionUrl = Arrays.asList(exceptionUrlPattern);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		AntPathMatcher pathMatcher = new AntPathMatcher();
		String requestUrl = ((HttpServletRequest) request).getServletPath();
		Iterator arg5 = this.exceptionUrl.iterator();

		String url;
		do {
			if (!arg5.hasNext()) {
				chain.doFilter(new HTMLTagFilterRequestWrapper((HttpServletRequest) request), response);
				return;
			}

			url = (String) arg5.next();
		} while (!pathMatcher.matchStart(url, requestUrl));

		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}
}