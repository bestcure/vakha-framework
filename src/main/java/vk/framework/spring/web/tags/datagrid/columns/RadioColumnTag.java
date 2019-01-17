package vk.framework.spring.web.tags.datagrid.columns;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.web.tags.datagrid.columns.AbstractColumnTag;

public class RadioColumnTag extends AbstractColumnTag {
	private String radioId;
	private String radioName;
	private String checkedValue;

	protected void doTagHeader() throws JspException, IOException {
		StringBuilder writer = new StringBuilder();
		writer.append("<th").append(this.getDynamicAttribute()).append(super.getHeaderAttribute()).append(">");
		if (StringUtils.isEmpty(this.getTitle())) {
			writer.append("선택");
		} else {
			writer.append(this.getTitle());
		}

		writer.append("</th>");
		super.getJspContext().getOut().print(writer.toString());
	}

	protected void doTagBody() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		writer.append("<td").append(this.getDynamicAttribute()).append(super.getBodyAttribute()).append(">");
		if (StringUtils.isEmpty(this.getRadioId())) {
			this.setRadioId(this.getRadioName());
		}

		writer.append("<input");
		super.writeAttribute(writer, "type", "radio");
		super.writeAttribute(writer, "id", this.getRadioId());
		super.writeAttribute(writer, "name", this.getRadioName());
		super.writeAttribute(writer, "value", (String) super.getColumnField(this.getFieldName()));
		if (super.getColumnField(this.getFieldName()).equals(this.getCheckedValue())) {
			writer.append(" checked=\"checked\"");
		}

		writer.append("/>");
		writer.append("</td>");
		super.getJspContext().getOut().print(writer.toString());
	}

	protected void doTagFooter(Object items) throws JspException, IOException {
	}

	public String getRadioId() {
		return this.radioId;
	}

	public void setRadioId(String radioId) {
		this.radioId = radioId;
	}

	public String getRadioName() {
		return this.radioName;
	}

	public void setRadioName(String radioName) {
		this.radioName = radioName;
	}

	public String getCheckedValue() {
		return this.checkedValue;
	}

	public void setCheckedValue(String checkedValue) {
		this.checkedValue = checkedValue;
	}
}