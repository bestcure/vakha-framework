package vk.framework.spring.web.tags.datagrid.columns;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import vk.framework.spring.web.tags.datagrid.NgDataGridTag;
import vk.framework.spring.web.tags.datagrid.columns.AbstractColumnTag;

public class NgColumnTag extends AbstractColumnTag {
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
	}

	private String getColumnHeader() throws JspException, IOException {
		StringBuilder writer = new StringBuilder();
		writer.append("");
		return writer.toString();
	}

	private String getColumn(Object objValue) throws JspException, IOException {
		StringWriter writer = new StringWriter();
		NgDataGridTag ngDataGridTag = (NgDataGridTag) AbstractColumnTag.findAncestorWithClass(this,
				NgDataGridTag.class);
		String var = "";
		if (ngDataGridTag != null) {
			var = ngDataGridTag.getVar();
		}

		writer.append("<column").append(this.getDynamicAttribute());
		if (!StringUtils.isEmpty(super.getFieldName())) {
			writer.append(" prop=\"" + this.getFieldName() + "\"");
		}

		if (!StringUtils.isEmpty(super.getWidth())) {
			writer.append(" width=\"" + this.getWidth().replace("%", "0").replace("*", "150") + "\"");
		}

		if (!StringUtils.isEmpty(super.getTitle())) {
			writer.append(" name=\"" + this.getTitle() + "\"");
		}

		if (!StringUtils.isEmpty(super.getbCssClass())) {
			writer.append(" class=\"" + this.getbCssClass() + "\"");
		}

		if (!StringUtils.isEmpty(super.getbCssStyle())) {
			writer.append(" style=\"" + this.getbCssStyle() + "\"");
		}

		writer.append(">");
		String value = String.valueOf(objValue).replace(var + ".", "$row.");
		if (StringUtils.isEmpty(value)) {
			value = "";
		}

		if (!StringUtils.isEmpty(super.getNgClick())) {
			writer.append("<a href=\"#none\" ng-click=\"");
			writer.append(this.getNgClick().replace(var + ".", "$row."));
			writer.append("\">");
			writer.append(value);
			writer.append("</a>");
		} else if (!StringUtils.isEmpty(super.getOnClick())) {
			writer.append("<a href=\"#none\" onclick=\"");
			writer.append(this.getOnClick());
			writer.append("\">");
			writer.append(value);
			writer.append("</a>");
		}

		if (!StringUtils.isEmpty(super.getFormat())) {
			writer.append(value.replace("}}", " | " + this.getConvertFormat() + " }}"));
		}

		if (StringUtils.isEmpty(super.getFieldName())) {
			writer.append(value);
		}

		writer.append("</column>");
		return writer.toString();
	}

	private String getColumnBody() throws JspException, IOException {
		JspFragment body = super.getJspBody();
		StringWriter writer = new StringWriter();
		if (body != null) {
			body.invoke(writer);
		}

		return writer.toString();
	}

	private String getConvertFormat() throws JspException {
		String value = "";
		WebApplicationContext wactx = WebApplicationContextUtils
				.getWebApplicationContext(super.getPageContext().getServletContext());
		SimpleDateFormat dateFormat = (SimpleDateFormat) wactx.getBean("dateFormat");
		SimpleDateFormat dateTimeFormat = (SimpleDateFormat) wactx.getBean("dateTimeFormat");
		if (!StringUtils.isEmpty(this.getFormat())) {
			if (this.getFormat().equals("number")) {
				value = "number";
			} else if (this.getFormat().equals("currency")) {
				value = "currency";
			} else if (this.getFormat().equals("percent")) {
				value = "number";
			} else if (this.getFormat().equals("phone")) {
				value = "phone";
			} else if (this.getFormat().equals("date")) {
				value = "date: \'" + dateFormat.toPattern() + "\'";
			} else if (this.getFormat().equals("datetime")) {
				value = "date: \'" + dateTimeFormat.toPattern() + "\'";
			} else if (this.getFormat().equals("dateShort")) {
				value = "date: \'" + dateFormat.toPattern().replace("yyyy", "yy") + "\'";
			} else if (this.getFormat().equals("datetimeShort")) {
				value = "date: \'" + dateTimeFormat.toPattern().replace("yyyy", "yy") + "\'";
			}
		}

		return value;
	}
}