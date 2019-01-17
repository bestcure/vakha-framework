package vk.framework.spring.web.tags.datagrid;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;

import vk.framework.spring.web.tags.AbstractTag;
import vk.framework.spring.web.tags.datagrid.DataGridTag;

public class PageInfoTag extends AbstractTag {
	private String cssStyle;
	private String cssClass;

	public void doTag() throws JspException, IOException {
		DataGridTag dataGridTag = (DataGridTag) AbstractTag.findAncestorWithClass(this, DataGridTag.class);
		if (dataGridTag == null) {
			throw new IllegalStateException("DataGrid tag does not exists.");
		} else {
			JspTag parent = super.getParent();
			if (parent != null && parent instanceof DataGridTag) {
				if (dataGridTag.getPaginationInfo() != null) {
					this.drawPageInfo(dataGridTag);
				}

			} else {
				throw new IllegalStateException("Parent tag is incorrect.");
			}
		}
	}

	private void drawPageInfo(DataGridTag dataGridTag) throws JspException, IOException {
		StringWriter writer = new StringWriter();
		writer.append("<li");
		super.writeAttribute(writer, "class", this.getCssClass());
		super.writeAttribute(writer, "style", this.getCssStyle());
		writer.append(">");
		writer.append("Total ");
		writer.append("<strong>");
		writer.append(String.valueOf(dataGridTag.getPaginationInfo().getTotalRecordCount()));
		writer.append("</strong>");
		writer.append("건");
		writer.append(" Page ");
		writer.append("<li class=\"now\">");
		writer.append(String.valueOf(dataGridTag.getPaginationInfo().getCurrentPageNo()));
		writer.append("</li>");
		writer.append("&#47;");
		writer.append("<em>");
		writer.append(String.valueOf(dataGridTag.getPaginationInfo().getTotalPageCount()));
		writer.append("</em>");
		writer.append("</li>");
		super.getJspContext().getOut().print(writer);
	}

	public String getCssStyle() {
		return this.cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getCssClass() {
		return this.cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
}