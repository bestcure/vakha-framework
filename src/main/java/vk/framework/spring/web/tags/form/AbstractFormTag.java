package vk.framework.spring.web.tags.form;

import java.io.StringWriter;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.web.tags.AbstractTag;

public abstract class AbstractFormTag extends AbstractTag {
	private String id;
	private String name;
	private String title;
	private String cssClass;
	private String cssStyle;
	private boolean disabled;
	private String suffixText;
	private String prefixText;
	protected final StringWriter writer = new StringWriter();

	protected final void writeBaseAttribute() {
		String disabledStr = "";
		if (!StringUtils.isEmpty(this.getId()) && StringUtils.isEmpty(this.getName())) {
			this.setName(this.getId());
		}

		super.writeAttribute(this.writer, "id", this.getId());
		super.writeAttribute(this.writer, "name", this.getName());
		super.writeAttribute(this.writer, "title", this.getTitle());
		super.writeAttribute(this.writer, "class", this.getCssClass());
		super.writeAttribute(this.writer, "style", this.getCssStyle());
		if (this.isDisabled()) {
			disabledStr = "disabled";
		} else {
			disabledStr = "";
		}

		super.writeAttribute(this.writer, "disabled", disabledStr);
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCssClass() {
		return this.cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getCssStyle() {
		return this.cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public boolean isDisabled() {
		return this.disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getSuffixText() {
		return this.suffixText;
	}

	public void setSuffixText(String suffixText) {
		this.suffixText = suffixText;
	}

	public String getPrefixText() {
		return this.prefixText;
	}

	public void setPrefixText(String prefixText) {
		this.prefixText = prefixText;
	}
}