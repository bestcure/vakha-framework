package vk.framework.spring.web.tags.datagrid.columns;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.web.tags.AbstractTag;
import vk.framework.spring.web.tags.datagrid.NgDataGridTag;

public class NgColumnsTag extends AbstractTag {
	private boolean isDrawingHeader = false;
	private boolean isDrawingColGroup = false;

	public void doTag() throws JspException, IOException {
		NgDataGridTag ngDataGridTag = (NgDataGridTag) AbstractTag.findAncestorWithClass(this, NgDataGridTag.class);
		JspFragment body = super.getJspBody();
		if (body == null) {
			throw new IllegalStateException("You need to place columns tags inside the table tag.");
		} else {
			JspTag parent = super.getParent();
			if (parent != null && parent instanceof NgDataGridTag) {
				StringWriter writer = new StringWriter();
				writer.append("<dtable options=\"options." + ngDataGridTag.getItems() + "\"");
				writer.append(ngDataGridTag.getDynamicAttribute());
				super.writeAttribute(writer, "rows", ngDataGridTag.getItems() + ".data");
				super.writeAttribute(writer, "class", ngDataGridTag.getCssClass());
				super.writeAttribute(writer, "style", ngDataGridTag.getCssStyle());
				super.writeAttribute(writer, "id",
						StringUtils.defaultString(ngDataGridTag.getId(), ngDataGridTag.getItems()));
				super.writeAttribute(writer, "data-id", ngDataGridTag.getItems() + "");
				super.writeAttribute(writer, "data-url", ngDataGridTag.getUrl());
				super.writeAttribute(writer, "data-form", ngDataGridTag.getDataForm());
				super.writeAttribute(writer, "data-pagnation", ngDataGridTag.isPaging() + "");
				super.writeAttribute(writer, "data-auto-load", ngDataGridTag.isAutoLoad() + "");
				super.writeAttribute(writer, "data-row-height", ngDataGridTag.getRowHeight() + "");
				super.writeAttribute(writer, "on-row-click", ngDataGridTag.getItems() + ".onRowClick(row)");
				super.writeAttribute(writer, "on-select", ngDataGridTag.getItems() + ".onSelect(rows)");
				super.writeAttribute(writer, "selected", ngDataGridTag.getItems() + ".selected");
				if (StringUtils.isEmpty(ngDataGridTag.getCssClass())) {
					super.writeAttribute(writer, "class", "material");
				}

				writer.append(">").append("\n");
				writer.append(this.getTableBody()).append("\n");
				writer.append("</dtable>").append("\n");
				super.getJspContext().getOut().print(writer);
			} else {
				throw new IllegalStateException("Parent tag is incorrect.");
			}
		}
	}

	private String getTableBody() throws JspException, IOException {
		JspFragment body = super.getJspBody();
		StringWriter writer = new StringWriter();
		body.invoke(writer);
		writer.append("").append("\n");
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
}