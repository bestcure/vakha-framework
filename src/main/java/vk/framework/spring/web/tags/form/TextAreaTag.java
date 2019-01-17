package vk.framework.spring.web.tags.form;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.web.tags.form.AbstractFormTag;

public class TextAreaTag extends AbstractFormTag {
	private String value = "";
	private String rows;
	private String cols;
	private boolean readonly;
	private String placeholder;
	private String defaultValue;

	public final void doTag() throws JspException, IOException {
		String readOnlyStr = "";
		if (!StringUtils.isEmpty(super.getPrefixText())) {
			super.writer.append("<label>" + super.getPrefixText() + "</label>");
		}

		super.writer.append("<textarea");
		super.writeBaseAttribute();
		super.writer.append(this.getDynamicAttribute());
		super.writeAttribute(super.writer, "rows", this.getRows());
		super.writeAttribute(super.writer, "cols", this.getCols());
		if (this.isReadonly()) {
			readOnlyStr = "readonly";
		} else {
			readOnlyStr = "";
		}

		super.writeAttribute(super.writer, "readonly", readOnlyStr);
		if (!StringUtils.isEmpty(this.getPlaceholder())) {
			super.writeAttribute(super.writer, "placeholder", this.getPlaceholder());
		}

		super.writer.append(">");
		super.writer.append(this.getValue());
		if (StringUtils.isEmpty(this.getValue()) && !StringUtils.isEmpty(this.getDefaultValue())) {
			super.writer.append(this.getDefaultValue());
		}

		super.writer.append("</textarea>");
		if (!StringUtils.isEmpty(super.getSuffixText())) {
			super.writer.append("<label>" + super.getSuffixText() + "</label>");
		}

		super.getJspContext().getOut().print(super.writer);
	}

	public String getValue() {
		return this.value;
	}

	public String getRows() {
		return this.rows;
	}

	public String getCols() {
		return this.cols;
	}

	public boolean isReadonly() {
		return this.readonly;
	}

	public String getPlaceholder() {
		return this.placeholder;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public void setCols(String cols) {
		this.cols = cols;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}