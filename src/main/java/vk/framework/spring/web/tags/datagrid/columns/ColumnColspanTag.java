package vk.framework.spring.web.tags.datagrid.columns;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;

import vk.framework.spring.web.tags.AbstractTag;
import vk.framework.spring.web.tags.datagrid.columns.ColumnsTag;

public class ColumnColspanTag extends AbstractTag {
	private boolean isDrawingHeader = false;
	private boolean isDrawingColGroup = false;
	private String title = "";

	public void doTag() throws JspException, IOException {
		JspTag parent = super.getParent();
		if (parent != null && parent instanceof ColumnsTag) {
			StringWriter writer = new StringWriter();
			writer.append(this.getTableHeader()).append("\n");
			super.getJspContext().getOut().print(writer);
		} else {
			throw new IllegalStateException("Parent tag is incorrect.");
		}
	}

	private String getTableHeader() throws JspException, IOException {
		JspFragment body = super.getJspBody();
		StringWriter writer = new StringWriter();
		this.setDrawingHeader(true);
		writer.append("<tr>").append("\n");
		body.invoke(writer);
		writer.append("</tr>").append("\n");
		this.setDrawingHeader(false);
		return writer.toString();
	}

	public boolean isDrawingHeader() {
		return this.isDrawingHeader;
	}

	public void setDrawingHeader(boolean isDrawingHeader) {
		this.isDrawingHeader = isDrawingHeader;
	}

	public boolean isDrawingColGroup() {
		return this.isDrawingColGroup;
	}

	public void setDrawingColGroup(boolean isDrawingColGroup) {
		this.isDrawingColGroup = isDrawingColGroup;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}