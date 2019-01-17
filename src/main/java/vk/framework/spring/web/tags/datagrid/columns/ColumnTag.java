package vk.framework.spring.web.tags.datagrid.columns;

import java.io.IOException;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import org.apache.commons.lang3.StringUtils;

import vk.framework.spring.collections.CamelUtil;
import vk.framework.spring.dataaccess.util.DataMap;
import vk.framework.spring.util.StringUtil;
import vk.framework.spring.web.functions.DateTimeFunctions;
import vk.framework.spring.web.tags.datagrid.DataGridTag;
import vk.framework.spring.web.tags.datagrid.columns.AbstractColumnTag;

public class ColumnTag extends AbstractColumnTag {
	protected void doTagHeader() throws JspException, IOException {
		super.getJspContext().getOut().print(this.getColumnHeader());
	}

	protected void doTagBody() throws JspException, IOException {
		if (!StringUtils.isEmpty(this.getFieldName())) {
			super.getJspContext().getOut().print(this.getColumn(super.getColumnField(this.getFieldName())));
		} else {
			super.getJspContext().getOut().print(this.getColumn(this.getColumnBody()));
		}

	}

	protected void doTagFooter(Object items) throws JspException, IOException {
		if (!StringUtils.isEmpty(this.getFieldName())) {
			super.getJspContext().getOut().print(this.getColumnFooter(items, this.getFieldName()));
		} else {
			super.getJspContext().getOut().print(this.getColumnFooter(items));
		}

	}

	private String getColumnHeader() throws JspException, IOException {
		HttpServletRequest request = super.getRequest();
		StringBuilder writer = new StringBuilder();
		if (this.isHidden()) {
			return writer.toString();
		} else {
			if (this.isSummary()) {
				DataGridTag sortOrder = (DataGridTag) AbstractColumnTag.findAncestorWithClass(this, DataGridTag.class);
				sortOrder.setFooter(true);
			}

			writer.append("<th").append(this.getDynamicAttribute()).append(super.getHeaderAttribute()).append(">");
			String sortOrder1 = StringUtils.defaultString(request.getParameter("searchSortOrder"), "ASC");
			String sortField = StringUtils.defaultString(request.getParameter("searchSortField"), "");
			if (sortField != null && sortField.contains("_")) {
				sortField = CamelUtil
						.convert2CamelCase(StringUtils.defaultString(request.getParameter("searchSortField"), ""));
			}

			String fieldName = StringUtils.isNotEmpty(this.getSortName()) ? this.getSortName() : this.getFieldName();
			if (this.isSort()) {
				if (fieldName.equals(sortField)) {
					writer.append("<a href=\"#none\" onclick=\"fnCmdSort(\'" + fieldName + "\', \'"
							+ (sortOrder1.equals("ASC") ? "DESC" : "ASC") + "\')\"\">");
				} else {
					writer.append("<a href=\"#none\" onclick=\"fnCmdSort(\'" + fieldName + "\', \'ASC\')\"\">");
				}
			}

			if (!StringUtils.isEmpty(this.getTitle())) {
				writer.append(this.convertMultiLangText(this.getTitle()));
			} else if (this.getFieldName() != null && !this.getFieldName().isEmpty()) {
				writer.append(this.getFieldName());
			}

			if (super.isSort()) {
				if (fieldName.equals(sortField)) {
					writer.append("&nbsp;" + (sortOrder1.equals("DESC") ? "▼" : "▲"));
				} else {
					writer.append("&nbsp;▲");
				}

				writer.append("</a>");
			}

			writer.append("</th>");
			return writer.toString();
		}
	}

	private String getColumn(Object objValue) throws JspException, IOException {
		StringWriter writer = new StringWriter();
		if (this.isHidden()) {
			return writer.toString();
		} else {
			writer.append("<td").append(this.getDynamicAttribute()).append(super.getBodyAttribute()).append(">");
			if (!StringUtils.isEmpty(super.getPrefixText())) {
				if (this.isMultiLangData()) {
					writer.append(this.convertMultiLangText(this.getPrefixText()));
				} else {
					writer.append(this.getPrefixText());
				}
			}

			String value = String.valueOf(objValue);
			if (objValue == null) {
				value = "";
			}

			if (this.isMultiLangData()) {
				value = this.convertMultiLangText((String) objValue);
			}

			if (!StringUtils.isEmpty(this.getFormat()) && !value.equals("")) {
				if (this.getFormat().equals("number")) {
					try {
						value = NumberFormat.getInstance().format((long) Integer.parseInt(objValue.toString()));
					} catch (NumberFormatException arg9) {
						;
					}
				} else if (this.getFormat().equals("currency")) {
					try {
						value = NumberFormat.getCurrencyInstance().format((long) Integer.parseInt(objValue.toString()));
					} catch (NumberFormatException arg8) {
						;
					}
				} else if (this.getFormat().equals("percent")) {
					try {
						value = NumberFormat.getPercentInstance().format((long) Integer.parseInt(objValue.toString()));
					} catch (NumberFormatException arg7) {
						;
					}
				} else if (this.getFormat().equals("date")) {
					try {
						value = DateTimeFunctions.defaultDateFormat((Date) objValue);
					} catch (NumberFormatException arg6) {
						;
					}
				} else if (this.getFormat().equals("phone")) {
					try {
						value = StringUtil.phoneFormat((String) objValue);
					} catch (Exception arg5) {
						;
					}
				} else if (this.getFormat().equals("datetime")) {
					try {
						value = DateTimeFunctions.defaultDateTimeFormat((Date) objValue);
					} catch (Exception arg4) {
						;
					}
				}
			} else if (objValue instanceof Date) {
				value = DateTimeFunctions.defaultDateFormat((Date) objValue);
			}

			if (!StringUtils.isEmpty(super.getOnClick())) {
				writer.append("<a href=\"#none\" ");
				writer.append("onclick=\"");
				writer.append(this.getOnClick());
				writer.append("\">");
				writer.append(value);
				writer.append("</a>");
			} else {
				writer.append(value);
			}

			if (!StringUtils.isEmpty(super.getSuffixText())) {
				if (this.isMultiLangData()) {
					writer.append(this.convertMultiLangText(this.getSuffixText()));
				} else {
					writer.append(this.getSuffixText());
				}
			}

			writer.append("</td>");
			return writer.toString();
		}
	}

	private String getColumnFooter(Object items, Object objValue) throws JspException, IOException {
		StringWriter writer = new StringWriter();
		if (this.isHidden()) {
			return writer.toString();
		} else if (!this.isSummary()) {
			writer.append("<td></td>");
			return writer.toString();
		} else {
			Integer value = Integer.valueOf(0);
			Iterator arg4 = ((List) items).iterator();

			while (arg4.hasNext()) {
				Object obj = arg4.next();
				DataMap item = (DataMap) obj;
				if (item.get(objValue) != null) {
					if (item.get(objValue) instanceof Number) {
						try {
							value = Integer.valueOf(value.intValue() + Integer.parseInt(item.get(objValue).toString()));
						} catch (NumberFormatException arg8) {
							value = Integer.valueOf(value.intValue() + 0);
						}
					} else {
						value = Integer.valueOf(value.intValue() + 0);
					}
				}
			}

			writer.append("<td").append(this.getDynamicAttribute()).append(super.getBodyAttribute()).append(">");
			writer.append(NumberFormat.getInstance().format(value));
			writer.append("</td>");
			return writer.toString();
		}
	}

	private String getColumnBody() throws JspException, IOException {
		JspFragment body = super.getJspBody();
		StringWriter writer = new StringWriter();
		if (body != null) {
			body.invoke(writer);
		}

		return writer.toString();
	}

	private String getColumnFooter(Object items) throws JspException, IOException {
		StringWriter writer = new StringWriter();
		writer.append("<td></td>");
		return writer.toString();
	}
}