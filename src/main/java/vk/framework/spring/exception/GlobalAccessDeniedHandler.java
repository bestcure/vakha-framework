package vk.framework.spring.exception;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class GlobalAccessDeniedHandler implements AccessDeniedHandler {
	private String errorPage;

	public GlobalAccessDeniedHandler(String errorPage) {
		this.errorPage = errorPage;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (request.getHeader("X-Requested-With") != null
				&& request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
			response.setStatus(401);
			request.setAttribute("javax.servlet.error.status_code", Integer.valueOf(401));
		} else {
			response.sendRedirect(this.errorPage);
		}
	}
}