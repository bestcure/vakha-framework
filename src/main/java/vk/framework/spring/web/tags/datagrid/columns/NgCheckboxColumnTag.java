package vk.framework.spring.web.tags.datagrid.columns;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.web.tags.datagrid.columns.AbstractColumnTag;

public class NgCheckboxColumnTag extends AbstractColumnTag {
	protected void doTagHeader() throws JspException, IOException {
	}

	protected void doTagBody() throws JspException, IOException {
		super.getJspContext().getOut().print(this.getColumnBody());
	}

	protected void doTagFooter(Object items) throws JspException, IOException {
	}

	private String getColumnBody() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		writer.append("<column").append(this.getDynamicAttribute());
		if (!StringUtils.isEmpty(super.getFieldName())) {
			writer.append(" prop=\"" + this.getFieldName() + "\"");
		}

		if (!StringUtils.isEmpty(super.getWidth())) {
			writer.append(" width=\"" + this.getWidth().replace("%", "0").replace("*", "150") + "\"");
		}

		if (!StringUtils.isEmpty(super.getTitle())) {
			writer.append(" name=\"" + this.getTitle() + "\"");
		}

		if (!StringUtils.isEmpty(super.getbCssClass())) {
			writer.append(" class=\"" + this.getbCssClass() + "\"");
		}

		if (!StringUtils.isEmpty(super.getbCssStyle())) {
			writer.append(" style=\"" + this.getbCssStyle() + "\"");
		}

		writer.append(" isCheckboxColumn=\"true\"");
		writer.append(" headerCheckbox=\"true\"");
		writer.append("></column>");
		return writer.toString();
	}
}