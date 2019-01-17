package vk.framework.spring.web.tags.form;

import java.io.IOException;
import javax.servlet.jsp.JspException;

import vk.framework.spring.web.tags.form.AbstractFormTag;

public class HiddenTag extends AbstractFormTag {
	private String value = "";
	private String defaultValue = "";

	public final void doTag() throws JspException, IOException {
		super.writer.append("<input");
		super.writeBaseAttribute();
		super.writeAttribute(super.writer, "type", "hidden");
		if (this.getValue() != null && !this.getValue().equals("")) {
			super.writeAttribute(super.writer, "value", this.getValue().replace("\"", "&quot;"));
		} else {
			super.writeAttribute(super.writer, "value", this.getDefaultValue().replace("\"", "&quot;"));
		}

		super.writer.append(this.getDynamicAttribute());
		super.writer.append(" />");
		super.getJspContext().getOut().print(super.writer);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}