package vk.framework.spring.web.tags.datagrid.columns;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import vk.framework.spring.dataaccess.util.DataMap;
import vk.framework.spring.web.tags.AbstractTag;
import vk.framework.spring.web.tags.datagrid.DataGridTag;
import vk.framework.spring.web.tags.ui.PaginationInfo;

public class ColumnsTag extends AbstractTag {
	private boolean isDrawingHeader = false;
	private boolean isDrawingFooter = false;
	private boolean isDrawingColGroup = false;

	public void doTag() throws JspException, IOException {
		DataGridTag dataGridTag = (DataGridTag) AbstractTag.findAncestorWithClass(this, DataGridTag.class);
		JspFragment body = super.getJspBody();
		if (body == null) {
			throw new IllegalStateException("You need to place columns tags inside the table tag.");
		} else {
			JspTag parent = super.getParent();
			if (parent != null && parent instanceof DataGridTag) {
				StringWriter writer = new StringWriter();
				writer.append("<table");
				writer.append(dataGridTag.getDynamicAttribute());
				super.writeAttribute(writer, "class", dataGridTag.getCssClass());
				super.writeAttribute(writer, "style", dataGridTag.getCssStyle());
				super.writeAttribute(writer, "id", dataGridTag.getId());
				writer.append(">").append("\n");
				if (!StringUtils.isEmpty(dataGridTag.getCaption())) {
					writer.append("<caption>");
					writer.append(dataGridTag.getCaption());
					writer.append("</caption>").append("\n");
				}

				writer.append(this.getTableColGroup()).append("\n");
				writer.append(this.getTableHeader()).append("\n");
				writer.append(this.getTableBody()).append("\n");
				if (dataGridTag.isFooter()) {
					writer.append(this.getTableFooter()).append("\n");
				}

				writer.append("</table>").append("\n");
				super.getJspContext().getOut().print(writer);
			} else {
				throw new IllegalStateException("Parent tag is incorrect.");
			}
		}
	}

	private String getTableColGroup() throws JspException, IOException {
		JspFragment body = super.getJspBody();
		StringWriter writer = new StringWriter();
		this.setDrawingColGroup(true);
		writer.append("<colgroup>").append("\n");
		body.invoke(writer);
		writer.append("</colgroup>").append("\n");
		this.setDrawingColGroup(false);
		return writer.toString();
	}

	private String getTableHeader() throws JspException, IOException {
		JspFragment body = super.getJspBody();
		StringWriter writer = new StringWriter();
		this.setDrawingHeader(true);
		writer.append("<thead>").append("\n");
		body.invoke(writer);
		writer.append("</thead>").append("\n");
		this.setDrawingHeader(false);
		return writer.toString();
	}

	private String getTableBody() throws JspException, IOException {
		DataGridTag dataGridTag = (DataGridTag) AbstractTag.findAncestorWithClass(this, DataGridTag.class);
		JspFragment body = super.getJspBody();
		StringWriter writer = new StringWriter();
		Object item = null;
		writer.append("<tbody>").append("\n");
		DataMap status;
		if (dataGridTag.getItems() instanceof Map) {
			status = new DataMap();
			status.put("count", Integer.valueOf(1));
			status.put("index", Integer.valueOf(0));
			if (dataGridTag.getVarStatus() != null && !dataGridTag.getVarStatus().equals("")) {
				super.getJspContext().setAttribute(dataGridTag.getVarStatus(), status);
			}

			super.getJspContext().setAttribute(dataGridTag.getVar(), dataGridTag.getItems());
			writer.append("<tr>").append("\n");
			body.invoke(writer);
			writer.append("</tr>").append("\n");
		} else if (dataGridTag.getItems() instanceof List) {
			List items = (List) dataGridTag.getItems();
			if (items != null && items.size() > 0) {
				int arg10 = items.size();
				boolean ex = false;
				int arg11;
				if (dataGridTag.getPaginationInfo() != null) {
					PaginationInfo i = dataGridTag.getPaginationInfo();
					arg11 = i.getTotalRecordCount() - (i.getCurrentPageNo() - 1) * i.getRecordCountPerPage();
				} else {
					arg11 = arg10;
				}

				for (int arg12 = 0; arg12 < arg10; ++arg12) {
					item = items.get(arg12);
					status = new DataMap();
					status.put("count", Integer.valueOf(arg10));
					status.put("index", Integer.valueOf(arg12));
					super.getJspContext().setAttribute(dataGridTag.getVar(), item);
					if (dataGridTag.getVarStatus() != null && !dataGridTag.getVarStatus().equals("")) {
						super.getJspContext().setAttribute(dataGridTag.getVarStatus(), status);
					}

					super.getJspContext().setAttribute("__ROW_NUMBER__", Integer.valueOf(arg11 - arg12));
					writer.append("<tr>").append("\n");
					body.invoke(writer);
					writer.append("</tr>").append("\n");
				}
			} else {
				String emptyMessage = "";

				try {
					emptyMessage = this.getMessageSource().getMessage(dataGridTag.getEmptyMessage(), (Object[]) null,
							LocaleContextHolder.getLocale());
				} catch (Exception arg9) {
					emptyMessage = dataGridTag.getEmptyMessage();
				}

				writer.append("<tr>").append("\n");
				writer.append("<td align=\"center\" colspan=\"").append(String.valueOf(dataGridTag.getColumnCount()))
						.append("\">");
				writer.append(emptyMessage);
				writer.append("</td>");
				writer.append("</tr>").append("\n");
			}
		}

		writer.append("</tbody>").append("\n");
		return writer.toString();
	}

	private String getTableFooter() throws JspException, IOException {
		JspFragment body = super.getJspBody();
		StringWriter writer = new StringWriter();
		this.setDrawingFooter(true);
		writer.append("<tfoot>").append("\n");
		body.invoke(writer);
		writer.append("</tfoot>").append("\n");
		this.setDrawingFooter(false);
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

	public boolean isDrawingFooter() {
		return this.isDrawingFooter;
	}

	public void setDrawingFooter(boolean isDrawingFooter) {
		this.isDrawingFooter = isDrawingFooter;
	}
}