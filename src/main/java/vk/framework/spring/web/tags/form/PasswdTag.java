package vk.framework.spring.web.tags.form;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.web.tags.form.AbstractFormTag;

public class PasswdTag extends AbstractFormTag {
	private String value = "";
	private String defaultValue = "";
	private String size;
	private String maxlength;
	private String pattern;
	private String required;
	private boolean readonly;
	private String placeholder;

	public final void doTag() throws JspException, IOException {
		String readOnlyStr = "";
		if (!StringUtils.isEmpty(super.getPrefixText())) {
			super.writer.append("<label>" + super.getPrefixText() + "</label>");
		}

		super.writer.append("<input");
		super.writeBaseAttribute();
		super.writeAttribute(super.writer, "type", "password");
		if (this.getValue() != null && !this.getValue().equals("")) {
			super.writeAttribute(super.writer, "value", this.getValue().replace("\"", "&quot;"));
		} else {
			super.writeAttribute(super.writer, "value", this.getDefaultValue().replace("\"", "&quot;"));
		}

		super.writeAttribute(super.writer, "size", this.getSize());
		super.writeAttribute(super.writer, "maxlength", this.getMaxlength());
		super.writeAttribute(super.writer, "pattern", this.getPattern());
		super.writeAttribute(super.writer, "required", this.getRequired());
		if (this.isReadonly()) {
			readOnlyStr = "readonly";
		} else {
			readOnlyStr = "";
		}

		super.writeAttribute(super.writer, "readonly", readOnlyStr);
		if (!StringUtils.isEmpty(this.getPlaceholder())) {
			super.writeAttribute(super.writer, "placeholder", this.getPlaceholder());
		}

		super.writer.append(" />");
		if (!StringUtils.isEmpty(super.getSuffixText())) {
			super.writer.append("<label>" + super.getSuffixText() + "</label>");
		}

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

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMaxlength() {
		return this.maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getRequired() {
		return this.required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public boolean isReadonly() {
		return this.readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public String getPlaceholder() {
		return this.placeholder;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
}