package vk.framework.spring.web.tags.form;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;

import vk.framework.spring.web.tags.AbstractTag;
import vk.framework.spring.web.tags.form.SelectTag;

public class OptionsTag extends AbstractTag {
	private Object items;
	private String itemLabel;
	private String itemValue;

	public final void doTag() throws JspException, IOException {
		JspTag parent = super.getParent();
		if (parent != null && parent instanceof SelectTag) {
			SelectTag selectTag = (SelectTag) AbstractTag.findAncestorWithClass(this, SelectTag.class);
			super.getJspContext().getOut()
					.print(selectTag.renderItemsOption(this.getItems(), this.getItemLabel(), this.getItemValue()));
		} else {
			throw new IllegalStateException("Parent tag is incorrect.");
		}
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
}