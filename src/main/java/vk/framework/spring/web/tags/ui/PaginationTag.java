package vk.framework.spring.web.tags.ui;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import vk.framework.spring.web.tags.ui.DefaultPaginationManager;
import vk.framework.spring.web.tags.ui.PaginationInfo;
import vk.framework.spring.web.tags.ui.PaginationManager;
import vk.framework.spring.web.tags.ui.PaginationRenderer;

public class PaginationTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private PaginationInfo paginationInfo;
	private String type;
	private String jsFunction;

	public int doEndTag() throws JspException {
		try {
			JspWriter e = this.pageContext.getOut();
			WebApplicationContext ctx = RequestContextUtils.findWebApplicationContext(
					(HttpServletRequest) this.pageContext.getRequest(), this.pageContext.getServletContext());
			Object paginationManager;
			if (ctx.containsBean("paginationManager")) {
				paginationManager = (PaginationManager) ctx.getBean("paginationManager");
			} else {
				paginationManager = new DefaultPaginationManager();
			}

			PaginationRenderer paginationRenderer = ((PaginationManager) paginationManager).getRendererType(this.type);
			String contents = paginationRenderer.renderPagination(this.paginationInfo, this.jsFunction);
			String pageIndex = this.pageContext.getRequest().getParameter("pageIndex");
			if (StringUtils.isEmpty(pageIndex)) {
				pageIndex = "1";
			}

			e.println("<input id=\"pageIndex\" name=\"pageIndex\" type=\"hidden\" value=\"" + pageIndex + "\" />");
			e.println("<ul class=\"paging\">");
			e.println(contents);
			e.append("</ul>");
			return 6;
		} catch (IOException arg6) {
			throw new JspException();
		}
	}

	public void setJsFunction(String jsFunction) {
		this.jsFunction = jsFunction;
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}

	public void setType(String type) {
		this.type = type;
	}
}