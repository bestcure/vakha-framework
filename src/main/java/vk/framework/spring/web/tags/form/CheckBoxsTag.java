package vk.framework.spring.web.tags.form;

import java.io.IOException;
import javax.servlet.jsp.JspException;

import vk.framework.spring.web.tags.form.AbstractFormTag;

public class CheckBoxsTag extends AbstractFormTag {
	private Object items;
	private String itemLabel;
	private String itemValue;
	private String value;

	public final void doTag() throws JspException, IOException {
		super.getJspContext().getOut().print(super.writer);
	}

	public Object getItems() {
		return this.items;
	}

	public void setItems(Object items) {
		this.items = items;
	}

	public String getItemLabel() {
		return this.itemLabel;
	}

	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}

	public String getItemValue() {
		return this.itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}