package vk.framework.spring.web.tags.form;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import vk.framework.spring.web.functions.DateTimeFunctions;
import vk.framework.spring.web.tags.form.AbstractFormTag;

public class DateTag extends AbstractFormTag {
	private Date value;
	private Date defaultValue;
	private String required;
	private boolean readonly;
	private String format = "date";
	private String placeholder = "";

	public final void doTag() throws JspException, IOException {
		WebApplicationContext wactx = WebApplicationContextUtils
				.getWebApplicationContext(this.getPageContext().getServletContext());
		SimpleDateFormat dateFormat = (SimpleDateFormat) wactx.getBean("dateFormat");
		SimpleDateFormat dateTimeFormat = (SimpleDateFormat) wactx.getBean("dateTimeFormat");
		Device device = DeviceUtils.getCurrentDevice(super.getRequest());
		if (!StringUtils.isEmpty(super.getPrefixText())) {
			super.writer.append("<label>" + super.getPrefixText() + "</label>");
		}

		super.writer.append("<input ");
		if (!StringUtils.isEmpty(this.getId()) && StringUtils.isEmpty(this.getName())) {
			this.setName(this.getId());
		}

		super.writeAttribute(super.writer, "id", this.getId());
		super.writeAttribute(super.writer, "name", this.getName());
		super.writeAttribute(super.writer, "title", this.getTitle());
		Date value = this.getValue() == null ? this.getDefaultValue() : this.getValue();
		if (!device.isNormal()) {
			super.writeAttribute(super.writer, "type", "date");
			if (this.format.equals("datetime")) {
				super.writeAttribute(super.writer, "value", DateTimeFunctions.dateFormat(value, "yyyy-MM-dd HH:mm"));
				super.writeAttribute(super.writer, "style", this.getCssStyle() + "; width: 170px !important");
			} else {
				super.writeAttribute(super.writer, "value", DateTimeFunctions.dateFormat(value, "yyyy-MM-dd"));
				super.writeAttribute(super.writer, "style", this.getCssStyle() + "; width: 120px !important");
			}
		} else {
			super.writeAttribute(super.writer, "type", "text");
			if (this.format.equals("datetime")) {
				super.writeAttribute(super.writer, "value",
						DateTimeFunctions.dateFormat(value, dateTimeFormat.toPattern()));
			} else {
				super.writeAttribute(super.writer, "value",
						DateTimeFunctions.dateFormat(value, dateFormat.toPattern()));
			}

			if (super.getCssClass() != null && !super.getCssClass().equals("")) {
				super.writeAttribute(super.writer, "class", "datepicker cal " + super.getCssClass());
			} else {
				super.writeAttribute(super.writer, "class", "datepicker cal");
			}
		}

		super.writeAttribute(super.writer, "maxlength", "10");
		super.writeAttribute(super.writer, "autocomplete", "off");
		super.writeAttribute(super.writer, "required", this.getRequired());
		if (this.isReadonly()) {
			super.writeAttribute(super.writer, "readonly", "readonly");
		}

		super.writer.append(this.getDynamicAttribute());
		super.writer.append(" />");
		if (!StringUtils.isEmpty(super.getSuffixText())) {
			super.writer.append("<label>" + super.getSuffixText() + "</label>");
		}

		super.getJspContext().getOut().print(super.writer);
	}

	public Date getValue() {
		return this.value;
	}

	public Date getDefaultValue() {
		return this.defaultValue;
	}

	public String getRequired() {
		return this.required;
	}

	public boolean isReadonly() {
		return this.readonly;
	}

	public String getFormat() {
		return this.format;
	}

	public String getPlaceholder() {
		return this.placeholder;
	}

	public void setValue(Date value) {
		this.value = value;
	}

	public void setDefaultValue(Date defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
}