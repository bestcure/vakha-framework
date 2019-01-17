package vk.framework.spring.web.tags.datagrid.columns;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;

import vk.framework.spring.web.tags.datagrid.columns.AbstractColumnTag;

public class NgRowNumColumnTag extends AbstractColumnTag {
	protected void doTagHeader() throws JspException, IOException {
	}

	protected void doTagBody() throws JspException, IOException {
		super.getJspContext().getOut().print(this.getColumnBody());
	}

	protected void doTagFooter(Object items) throws JspException, IOException {
	}

	private String getColumnBody() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		writer.append("<column prop=\"count\" name=\"").append(this.getTitle()).append("\"")
				.append(this.getDynamicAttribute()).append(super.getBodyAttribute());
		writer.append(" width=\"" + super.getWidth() + "\"");
		writer.append("></column>");
		return writer.toString();
	}
}