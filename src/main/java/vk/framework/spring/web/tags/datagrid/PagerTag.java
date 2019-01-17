package vk.framework.spring.web.tags.datagrid;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import vk.framework.spring.web.tags.AbstractTag;
import vk.framework.spring.web.tags.datagrid.DataGridTag;
import vk.framework.spring.web.tags.ui.DefaultPaginationManager;
import vk.framework.spring.web.tags.ui.PaginationManager;
import vk.framework.spring.web.tags.ui.PaginationRenderer;

public class PagerTag extends AbstractTag {
	private String type;
	private String jsFunction;

	public void doTag() throws JspException, IOException {
		DataGridTag dataGridTag = (DataGridTag) AbstractTag.findAncestorWithClass(this, DataGridTag.class);
		if (dataGridTag == null) {
			throw new IllegalStateException("DataGrid tag does not exists.");
		} else {
			JspTag parent = super.getParent();
			if (parent != null && parent instanceof DataGridTag) {
				if (dataGridTag.getPaginationInfo() != null) {
					this.drawPager(dataGridTag);
				}

			} else {
				throw new IllegalStateException("Parent tag is incorrect.");
			}
		}
	}

	private void drawPager(DataGridTag dataGridTag) throws JspException, IOException {
		StringWriter writer = new StringWriter();
		String pageIndex = super.getRequest().getParameter("pageIndex");
		if (StringUtils.isEmpty(pageIndex)) {
			pageIndex = "1";
		}

		writer.append("<input id=\"pageIndex\" name=\"pageIndex\" type=\"hidden\" value=\"");
		writer.append(pageIndex);
		writer.append("\" />").append("\n");
		WebApplicationContext ctx = RequestContextUtils.findWebApplicationContext(super.getRequest(),
				super.getPageContext().getServletContext());
		Object paginationManager;
		if (ctx.containsBean("paginationManager")) {
			paginationManager = (PaginationManager) ctx.getBean("paginationManager");
		} else {
			paginationManager = new DefaultPaginationManager();
		}

		PaginationRenderer paginationRenderer = ((PaginationManager) paginationManager).getRendererType(this.type);
		String pagination = paginationRenderer.renderPagination(dataGridTag.getPaginationInfo(), this.jsFunction);
		String currentUrl = (String) super.getRequest().getAttribute("javax.servlet.forward.request_uri");
		if (currentUrl != null && currentUrl.startsWith("/manager")) {
			String pageUnit = String.valueOf(super.getRequest().getParameter("pageUnit"));
			writer.append("<select name=\"pageUnit\" class=\"paging-ea\">");
			writer.append("<option value=\"10\" " + (pageUnit.equals("10") ? "selected" : "") + ">10</option>");
			writer.append("<option value=\"20\" " + (pageUnit.equals("20") ? "selected" : "") + ">20</option>");
			writer.append("<option value=\"40\" " + (pageUnit.equals("40") ? "selected" : "") + ">40</option>");
			writer.append("<option value=\"80\" " + (pageUnit.equals("80") ? "selected" : "") + ">80</option>");
			writer.append("<option value=\"100\" " + (pageUnit.equals("100") ? "selected" : "") + ">100</option>");
			writer.append("<option value=\"500\" " + (pageUnit.equals("500") ? "selected" : "") + ">500</option>");
			writer.append("<option value=\"1000\" " + (pageUnit.equals("1000") ? "selected" : "") + ">1000</option>");
			writer.append("<option value=\"999999999\" " + (pageUnit.equals("999999999") ? "selected" : "")
					+ ">ALL</option>");
			writer.append("</select>");
			writer.append("<script> $(\'[name=pageUnit]\').selectmenu({ change: function(event, ui) { "
					+ this.jsFunction + "(1); } }); </script>");
		}

		writer.append("<ul class=\"paging\">");
		writer.append(pagination).append("\n");
		writer.append("</ul>").append("\n");
		super.getJspContext().getOut().print(writer);
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setJsFunction(String jsFunction) {
		this.jsFunction = jsFunction;
	}
}