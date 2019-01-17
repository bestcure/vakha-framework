package vk.framework.spring.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.CharacterEncodingFilter;

public class CustomCharacterEncodingFilter extends CharacterEncodingFilter {
	private List<String> excludeUrl;

	public CustomCharacterEncodingFilter() {
		super.setEncoding("UTF-8");
		super.setForceRequestEncoding(true);
		super.setForceResponseEncoding(true);
	}

	public CustomCharacterEncodingFilter(String encoding, boolean forceEncoding) {
		super.setEncoding(encoding);
		super.setForceRequestEncoding(forceEncoding);
		super.setForceResponseEncoding(forceEncoding);
	}

	public CustomCharacterEncodingFilter(String encoding, boolean forceEncoding, String excludeUrl) {
		super.setEncoding(encoding);
		super.setForceRequestEncoding(forceEncoding);
		super.setForceResponseEncoding(forceEncoding);
		this.setExcludeUrl(excludeUrl);
	}

	public void setExcludeUrl(String exceptionUrl) {
		this.excludeUrl = Arrays.asList(exceptionUrl.split(","));
	}

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		AntPathMatcher pathMatcher = new AntPathMatcher();
		String requestUrl = request.getRequestURI();
		boolean isExclude = false;
		if (this.excludeUrl != null) {
			Iterator encoding = this.excludeUrl.iterator();

			while (encoding.hasNext()) {
				String url = (String) encoding.next();
				if (pathMatcher.matchStart(url, requestUrl)) {
					isExclude = true;
					break;
				}
			}
		}

		String encoding1 = this.getEncoding();
		if (encoding1 != null && !isExclude) {
			if (this.isForceRequestEncoding() || request.getCharacterEncoding() == null) {
				request.setCharacterEncoding(encoding1);
			}

			if (this.isForceResponseEncoding()) {
				response.setCharacterEncoding(encoding1);
			}
		}

		filterChain.doFilter(request, response);
	}
}