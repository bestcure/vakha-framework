package vk.framework.spring.web.tags.form;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.web.tags.form.AbstractFormTag;

public class CheckBoxTag extends AbstractFormTag {
	private Object items;
	private String itemLabel;
	private String itemValue;
	private Object label;
	private Object value;
	private Object checkedValue;
	private Object defaultCheckedValue;
	private Boolean allOption;

	public final void doTag() throws JspException, IOException {
		if (this.getCheckedValue() == null || this.getCheckedValue().equals("")) {
			this.setCheckedValue(this.getDefaultCheckedValue());
		}

		if (this.getAllOption() != null && this.getAllOption().booleanValue()) {
			this.renderCheckBox("전체", "ALL");
		}

		if (this.getItems() != null) {
			this.renderItemCheckBox(this.getItems(), this.getItemLabel(), this.getItemValue());
		} else {
			this.renderCheckBox(this.getLabel(), this.getValue());
		}

		super.getJspContext().getOut().print(super.writer);
	}

	public void renderItemCheckBox(Object items, String itemLabel, String itemValue) {
		Iterator arg4;
		if (items instanceof Map) {
			Map collection = (Map) items;
			arg4 = collection.entrySet().iterator();

			while (arg4.hasNext()) {
				Entry object = (Entry) arg4.next();
				this.renderCheckBox(object.getValue(), object.getKey());
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
						this.renderCheckBox(dataMap.get(itemLabel), dataMap.get(itemValue));
					} else {
						this.renderCheckBox(dataMap, dataMap);
					}
				}
			}
		}

	}

	public void renderCheckBox(Object label, Object value) {
		if (label == null) {
			label = "";
		}

		if (value == null) {
			value = "";
		}

		if (!StringUtils.isEmpty(super.getPrefixText())) {
			super.writer.append(super.getPrefixText());
		}

		if (label != "") {
			super.writer.append("<label>");
		}

		super.writer.append("<input");
		super.writeAttribute(this.writer, "type", "checkbox");
		super.writeAttribute(this.writer, "value", value.toString());
		super.writeBaseAttribute();
		super.writer.append(this.getDynamicAttribute());
		int arg4;
		int arg5;
		if (this.getCheckedValue() instanceof Object[]) {
			Object[] collection = (Object[]) ((Object[]) this.getCheckedValue());
			Object[] arg3 = collection;
			arg4 = collection.length;

			for (arg5 = 0; arg5 < arg4; ++arg5) {
				Object object = arg3[arg5];
				if (object.equals(value)) {
					super.writer.append(" checked=\"checked\"");
				}
			}
		} else {
			String[] arg7 = String.valueOf(this.getCheckedValue()).split("[,]");
			String[] arg8 = arg7;
			arg4 = arg7.length;

			for (arg5 = 0; arg5 < arg4; ++arg5) {
				String arg9 = arg8[arg5];
				if (arg9.equals(String.valueOf(value))) {
					super.writer.append(" checked=\"checked\"");
				}
			}
		}

		super.writer.append(" />");
		if (label != "") {
			super.writer.append("&nbsp;");
			super.writer.append(label.toString());
			super.writer.append("</label>");
		}

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

	public Object getLabel() {
		return this.label;
	}

	public void setLabel(Object label) {
		this.label = label;
	}

	public Object getValue() {
		return this.value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getCheckedValue() {
		return this.checkedValue;
	}

	public void setCheckedValue(Object checkedValue) {
		this.checkedValue = checkedValue;
	}

	public Object getDefaultCheckedValue() {
		return this.defaultCheckedValue;
	}

	public void setDefaultCheckedValue(Object defaultCheckedValue) {
		this.defaultCheckedValue = defaultCheckedValue;
	}

	public Boolean getAllOption() {
		return this.allOption;
	}

	public void setAllOption(Boolean allOption) {
		this.allOption = allOption;
	}
}