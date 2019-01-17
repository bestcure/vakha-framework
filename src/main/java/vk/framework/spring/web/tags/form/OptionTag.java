package vk.framework.spring.web.tags.form;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;

import vk.framework.spring.web.tags.AbstractTag;
import vk.framework.spring.web.tags.form.SelectTag;

public class OptionTag extends AbstractTag {
	private String label;
	private String value;
	private boolean hidden = false;

	public final void doTag() throws JspException, IOException {
		JspTag parent = super.getParent();
		if (parent != null && parent instanceof SelectTag) {
			SelectTag selectTag = (SelectTag) AbstractTag.findAncestorWithClass(this, SelectTag.class);
			StringWriter writer = new StringWriter();
			if (!this.getHidden()) {
				writer.append(selectTag.renderOption(this.getLabel(), this.getValue()));
			}

			super.getJspContext().getOut().print(writer);
		} else {
			throw new IllegalStateException("Parent tag is incorrect.");
		}
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean getHidden() {
		return this.hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}