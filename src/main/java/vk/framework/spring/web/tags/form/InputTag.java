package vk.framework.spring.web.tags.form;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.web.tags.form.AbstractFormTag;

public class InputTag extends AbstractFormTag {
	private String type = "";
	private String value = "";
	private String defaultValue = "";
	private boolean ngModel;
	private String size;
	private String maxlength;
	private String pattern;
	private String required;
	private boolean readonly;
	private String placeholder;
	private String autocapitalize;
	private String autocorrect;
	private String autocomplete;

	public final void doTag() throws JspException, IOException {
		String readOnlyStr = "";
		if (!StringUtils.isEmpty(super.getPrefixText())) {
			super.writer.append("<label>" + super.getPrefixText() + "</label>");
		}

		super.writer.append("<input");
		super.writeBaseAttribute();
		if (this.getType() != null && !this.getType().equals("")) {
			super.writeAttribute(super.writer, "type", this.getType());
		} else {
			super.writeAttribute(super.writer, "type", "text");
		}

		if (this.isNgModel()) {
			super.writeAttribute(super.writer, "ng-model", this.getId());
		}

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

		if (!StringUtils.isEmpty(this.getPlaceholder())) {
			super.writeAttribute(super.writer, "placeholder", this.getPlaceholder());
		}

		if (!StringUtils.isEmpty(this.getAutocapitalize())) {
			super.writeAttribute(super.writer, "autocapitalize", this.getAutocapitalize());
		}

		if (!StringUtils.isEmpty(this.getAutocorrect())) {
			super.writeAttribute(super.writer, "autocorrect", this.getAutocorrect());
		}

		if (!StringUtils.isEmpty(this.getAutocomplete())) {
			super.writeAttribute(super.writer, "autocomplete", this.getAutocomplete());
		}

		super.writer.append(this.getDynamicAttribute());
		super.writeAttribute(super.writer, "readonly", readOnlyStr);
		super.writer.append(" />");
		if (!StringUtils.isEmpty(super.getSuffixText())) {
			super.writer.append("<label>" + super.getSuffixText() + "</label>");
		}

		super.getJspContext().getOut().print(super.writer);
	}

	public String getType() {
		return this.type;
	}

	public String getValue() {
		return this.value;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public boolean isNgModel() {
		return this.ngModel;
	}

	public String getSize() {
		return this.size;
	}

	public String getMaxlength() {
		return this.maxlength;
	}

	public String getPattern() {
		return this.pattern;
	}

	public String getRequired() {
		return this.required;
	}

	public boolean isReadonly() {
		return this.readonly;
	}

	public String getPlaceholder() {
		return this.placeholder;
	}

	public String getAutocapitalize() {
		return this.autocapitalize;
	}

	public String getAutocorrect() {
		return this.autocorrect;
	}

	public String getAutocomplete() {
		return this.autocomplete;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setNgModel(boolean ngModel) {
		this.ngModel = ngModel;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

	public void setAutocapitalize(String autocapitalize) {
		this.autocapitalize = autocapitalize;
	}

	public void setAutocorrect(String autocorrect) {
		this.autocorrect = autocorrect;
	}

	public void setAutocomplete(String autocomplete) {
		this.autocomplete = autocomplete;
	}
}