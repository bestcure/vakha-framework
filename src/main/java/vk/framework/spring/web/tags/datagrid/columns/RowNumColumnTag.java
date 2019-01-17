package vk.framework.spring.web.tags.datagrid.columns;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.web.tags.datagrid.columns.AbstractColumnTag;

public class RowNumColumnTag extends AbstractColumnTag {
	protected void doTagHeader() throws JspException, IOException {
		super.getJspContext().getOut().print(this.getColumnHeader());
	}

	protected void doTagBody() throws JspException, IOException {
		super.getJspContext().getOut().print(this.getColumnBody());
	}

	protected void doTagFooter(Object items) throws JspException, IOException {
	}

	private String getColumnHeader() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		writer.append("<th").append(this.getDynamicAttribute()).append(super.getHeaderAttribute()).append(">");
		if (!StringUtils.isEmpty(this.getTitle())) {
			writer.append(this.getTitle());
		}

		writer.append("</th>");
		return writer.toString();
	}

	private String getColumnBody() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		writer.append("<td").append(this.getDynamicAttribute()).append(super.getBodyAttribute()).append(">");
		if (!StringUtils.isEmpty(super.getPrefixText())) {
			writer.append(this.getPrefixText());
		}

		writer.append(super.getJspContext().getAttribute("__ROW_NUMBER__").toString());
		if (!StringUtils.isEmpty(super.getSuffixText())) {
			writer.append(this.getSuffixText());
		}

		writer.append("</td>");
		return writer.toString();
	}
}