package vk.framework.spring.web.tags.form;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.web.tags.form.AbstractFormTag;

public class RadioButtonsTag extends AbstractFormTag {
	private Object items;
	private String itemLabel;
	private String itemValue;
	private String label;
	private String value;
	private String checkedValue;
	private String defaultCheckedValue;

	public final void doTag() throws JspException, IOException {
		if (!StringUtils.isEmpty(this.getId()) && StringUtils.isEmpty(this.getName())) {
			this.setName(this.getId());
		}

		if (this.getCheckedValue() == null || this.getCheckedValue().equals("")) {
			this.setCheckedValue(this.getDefaultCheckedValue());
		}

		if (this.getItems() != null) {
			this.renderItemsRadioButton(this.getItems(), this.getItemLabel(), this.getItemValue());
		} else {
			this.renderRadio(this.getLabel(), this.getValue());
		}

		super.getJspContext().getOut().print(super.writer);
	}

	public void renderItemsRadioButton(Object items, String itemLabel, String itemValue) {
		Iterator arg4;
		if (items instanceof Map) {
			Map collection = (Map) items;
			arg4 = collection.entrySet().iterator();

			while (arg4.hasNext()) {
				Entry object = (Entry) arg4.next();
				this.renderRadio(object.getValue(), object.getKey());
			}
		} else if (items instanceof Collection) {
			Collection collection1 = (Collection) items;
			arg4 = collection1.iterator();

			while (true) {
				while (true) {
					Object object1;
					do {
						if (!arg4.hasNext()) {
							return;
						}

						object1 = arg4.next();
					} while (!(object1 instanceof Map));

					Map dataMap = (Map) object1;
					if (!StringUtils.isEmpty(itemLabel) && !StringUtils.isEmpty(itemValue)) {
						this.renderRadio(dataMap.get(itemLabel), dataMap.get(itemValue));
					} else {
						this.renderRadio(dataMap, dataMap);
					}
				}
			}
		}

	}

	public void renderRadio(Object label, Object value) {
		if (!StringUtils.isEmpty(super.getPrefixText())) {
			super.writer.append(super.getPrefixText());
		}

		super.writer.append("<label><input");
		this.writeBaseAttribute();
		super.writer.append(this.getDynamicAttribute());
		super.writeAttribute(this.writer, "type", "radio");
		super.writeAttribute(this.writer, "value", value.toString());
		if (this.getCheckedValue() != null && this.getCheckedValue().equals(value)) {
			this.writer.append(" checked=\"checked\"");
		}

		super.writer.append(" />");
		super.writer.append("&nbsp;");
		super.writer.append(label.toString());
		super.writer.append("</label>");
		if (!StringUtils.isEmpty(super.getSuffixText())) {
			super.writer.append(super.getSuffixText());
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

	public String getDefaultCheckedValue() {
		return this.defaultCheckedValue;
	}

	public void setDefaultCheckedValue(String defaultCheckedValue) {
		this.defaultCheckedValue = defaultCheckedValue;
	}

	public String getCheckedValue() {
		return this.checkedValue;
	}

	public void setCheckedValue(String checkedValue) {
		this.checkedValue = checkedValue;
	}
}