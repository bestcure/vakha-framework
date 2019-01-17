package vk.framework.spring.web.tags.datagrid.columns;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.util.StringUtil;
import vk.framework.spring.web.tags.datagrid.columns.AbstractColumnTag;

public class CheckBoxColumnTag extends AbstractColumnTag {
	private String allCheckboxId;
	private String checkboxName;
	private String checkedValue;

	protected void doTagHeader() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		writer.append("<th").append(this.getDynamicAttribute()).append(super.getHeaderAttribute()).append(">");
		if (!StringUtils.isEmpty(this.getTitle())) {
			writer.append(this.getTitle());
		} else {
			writer.append("<input");
			if (StringUtils.isEmpty(this.getAllCheckboxId())) {
				this.setAllCheckboxId("checkAll");
			}

			super.writeAttribute(writer, "id", this.getAllCheckboxId());
			super.writeAttribute(writer, "type", "checkbox");
			writer.append("/>");
		}

		writer.append("</th>");
		super.getJspContext().getOut().print(writer.toString());
	}

	protected void doTagBody() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		writer.append("<td").append(this.getDynamicAttribute()).append(super.getBodyAttribute()).append(">");
		if (StringUtils.isEmpty(this.getCheckboxName())) {
			this.setCheckboxName(this.getFieldName());
		}

		writer.append("<input");
		super.writeAttribute(writer, "type", "checkbox");
		super.writeAttribute(writer, "name", this.getCheckboxName());
		super.writeAttribute(writer, "id", this.getCheckboxName());
		if (super.getColumnField(this.getFieldName()) == null) {
			super.writeAttribute(writer, "value", (String) super.getColumnField(this.getFieldName()));
		} else {
			super.writeAttribute(writer, "value", String.valueOf(super.getColumnField(this.getFieldName())));
		}

		if (!StringUtil.isEmpty(this.getCheckedValue())) {
			if (this.getCheckedValue().indexOf(",") > -1) {
				String[] checkVal = this.getCheckedValue().split("[,]");
				String[] arg2 = checkVal;
				int arg3 = checkVal.length;

				for (int arg4 = 0; arg4 < arg3; ++arg4) {
					String check = arg2[arg4];
					if (super.getColumnField(this.getFieldName()).equals(check)) {
						writer.append(" checked=\"checked\"");
					}
				}
			} else if (super.getColumnField(this.getFieldName()).equals(this.getCheckedValue())) {
				writer.append(" checked=\"checked\"");
			}
		}

		writer.append("/>");
		writer.append("</td>");
		super.getJspContext().getOut().print(writer.toString());
	}

	protected void doTagFooter(Object items) throws JspException, IOException {
	}

	public String getAllCheckboxId() {
		return this.allCheckboxId;
	}

	public void setAllCheckboxId(String allCheckboxId) {
		this.allCheckboxId = allCheckboxId;
	}

	public String getCheckboxName() {
		return this.checkboxName;
	}

	public void setCheckboxName(String checkboxName) {
		this.checkboxName = checkboxName;
	}

	public String getCheckedValue() {
		return this.checkedValue;
	}

	public void setCheckedValue(String checkedValue) {
		this.checkedValue = checkedValue;
	}
}