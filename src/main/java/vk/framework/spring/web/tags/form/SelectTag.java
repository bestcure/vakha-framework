package vk.framework.spring.web.tags.form;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.web.tags.form.AbstractFormTag;

public class SelectTag extends AbstractFormTag {
	private Object items;
	private String itemLabel;
	private String itemValue;
	private String value;
	private String defaultValue;
	private String prompt;
	private boolean allOption;
	private boolean multiple;

	public void doTag() throws JspException, IOException {
		JspFragment body = super.getJspBody();
		if (!StringUtils.isEmpty(super.getPrefixText())) {
			super.writer.append("<label>" + super.getPrefixText() + "</label>\n");
		}

		super.writer.append("<select ng-init=\"" + this.getId() + " = null \"");
		super.writeBaseAttribute();
		super.writer.append(this.getDynamicAttribute());
		if (this.isMultiple()) {
			super.writer.append(" multiple=\"multiple\"");
		}

		super.writer.append(">");
		super.writer.append("\n");
		if (this.getPrompt() != null && this.getPrompt() != "") {
			super.writer.append(this.renderOption(this.getPrompt(), ""));
		}

		if (this.isAllOption()) {
			super.writer.append(this.renderOption("전체", "ALL"));
		}

		if (this.getItems() != null) {
			super.writer.append(this.renderItemsOption(this.getItems(), this.getItemLabel(), this.getItemValue()));
		} else if (body != null) {
			body.invoke(this.writer);
		}

		super.writer.append("</select>");
		if (!StringUtils.isEmpty(super.getSuffixText())) {
			super.writer.append("<label>" + super.getSuffixText() + "</label>");
		}

		super.getJspContext().getOut().print(super.writer);
	}

	public String renderItemsOption(Object items, String itemLabel, String itemValue) {
		StringWriter writer = new StringWriter();
		Iterator arg5;
		if (items instanceof Map) {
			Map collection = (Map) items;
			arg5 = collection.entrySet().iterator();

			while (arg5.hasNext()) {
				Entry object = (Entry) arg5.next();
				writer.append(this.renderOption(object.getValue(), object.getKey()));
			}
		} else if (items instanceof Collection) {
			Collection collection1 = (Collection) items;
			arg5 = collection1.iterator();

			while (true) {
				while (true) {
					Object object1;
					do {
						if (!arg5.hasNext()) {
							return writer.toString();
						}

						object1 = arg5.next();
					} while (!(object1 instanceof Map));

					Map dataMap = (Map) object1;
					if (!StringUtils.isEmpty(itemLabel) && !StringUtils.isEmpty(itemValue)) {
						writer.append(this.renderOption(dataMap.get(itemLabel), dataMap.get(itemValue)));
					} else {
						writer.append(this.renderOption(dataMap, dataMap));
					}
				}
			}
		}

		return writer.toString();
	}

	public String renderOption(Object label, Object value) {
		StringWriter writer = new StringWriter();
		writer.append("<option ");
		writer.append("value=\"").append(value.toString()).append("\"");
		if (!StringUtils.isEmpty(this.getValue()) && this.getValue().toString().equals(value.toString())) {
			writer.append(" selected=\"selected\"");
		} else if (StringUtils.isEmpty(this.getValue()) && this.getDefaultValue() != null
				&& this.getDefaultValue().toString().equals(value.toString())) {
			writer.append(" selected=\"selected\"");
		}

		writer.append(">");
		writer.append(label.toString());
		writer.append("</option>");
		writer.append("\n");
		return writer.toString();
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

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getPrompt() {
		return this.prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public boolean isAllOption() {
		return this.allOption;
	}

	public void setAllOption(boolean allOption) {
		this.allOption = allOption;
	}

	public boolean isMultiple() {
		return this.multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
}