package vk.framework.spring.web.tags.datagrid.columns;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import vk.framework.spring.web.tags.AbstractTag;
import vk.framework.spring.web.tags.datagrid.DataGridTag;
import vk.framework.spring.web.tags.datagrid.NgDataGridTag;
import vk.framework.spring.web.tags.datagrid.columns.ColumnTag;
import vk.framework.spring.web.tags.datagrid.columns.ColumnsTag;
import vk.framework.spring.web.tags.datagrid.columns.NgColumnsTag;

public abstract class AbstractColumnTag extends AbstractTag {
	private String title;
	private String fieldName;
	private boolean hidden;
	private boolean summary;
	private String onClick;
	private String ngClick;
	private String width;
	private String suffixText;
	private String prefixText;
	private String hCssStyle;
	private String bCssStyle;
	private String hCssClass;
	private String bCssClass;
	private boolean multiLangData;
	private boolean sort;
	private String sortName;
	private String format;

	public final void doTag() throws JspException, IOException {
		DataGridTag dataGridTag = (DataGridTag) AbstractTag.findAncestorWithClass(this, DataGridTag.class);
		NgDataGridTag ngDataGridTag = (NgDataGridTag) AbstractTag.findAncestorWithClass(this, NgDataGridTag.class);
		if (dataGridTag == null && ngDataGridTag == null) {
			throw new IllegalStateException("table tag does not exists.");
		} else {
			ColumnsTag columnsTag = (ColumnsTag) AbstractTag.findAncestorWithClass(this, ColumnsTag.class);
			NgColumnsTag ngColumnsTag = (NgColumnsTag) AbstractTag.findAncestorWithClass(this, NgColumnsTag.class);
			if (columnsTag == null && ngColumnsTag == null) {
				throw new IllegalStateException("columns tag does not exists.");
			} else {
				JspTag parent = super.getParent();
				if (parent == null || !(parent instanceof ColumnsTag) && !(parent instanceof ColumnTag)
						&& !(parent instanceof NgColumnsTag) && !(parent instanceof NgColumnsTag)) {
					throw new IllegalStateException("Parent tag is incorrect.");
				} else {
					if (ngColumnsTag != null) {
						if (ngColumnsTag.isDrawingColGroup()) {
							this.getColumnGroup();
						} else if (ngColumnsTag.isDrawingHeader()) {
							if (!this.isHidden()) {
								ngDataGridTag.setColumnCount(ngDataGridTag.getColumnCount() + 1);
							}

							this.doTagHeader();
						} else {
							this.doTagBody();
						}
					} else if (columnsTag.isDrawingColGroup()) {
						this.getColumnGroup();
					} else if (columnsTag.isDrawingHeader()) {
						if (!this.isHidden()) {
							dataGridTag.setColumnCount(dataGridTag.getColumnCount() + 1);
						}

						this.doTagHeader();
					} else if (columnsTag.isDrawingFooter()) {
						this.doTagFooter(dataGridTag.getItems());
					} else {
						this.doTagBody();
					}

				}
			}
		}
	}

	private void getColumnGroup() throws JspException, IOException {
		StringWriter writer = new StringWriter();
		if (!this.isHidden()) {
			writer.append("<col");
			if (this.getWidth() != null) {
				super.writeAttribute(writer, "width", this.getWidth());
			}

			writer.append(" />");
		}

		super.getJspContext().getOut().print(writer.toString());
	}

	public Object getColumnField(String fieldName) throws JspException, IOException {
		DataGridTag dataGridTag = (DataGridTag) AbstractTag.findAncestorWithClass(this, DataGridTag.class);
		NgDataGridTag ngDataGridTag = (NgDataGridTag) AbstractTag.findAncestorWithClass(this, NgDataGridTag.class);
		String var = "";
		String expressFieldName = "";
		if (ngDataGridTag != null) {
			var = ngDataGridTag.getVar();
			if (fieldName.contains("$")) {
				expressFieldName = "{{" + fieldName + "}}";
			} else {
				var = var.replace(var + ".", "$row.");
				expressFieldName = "{{" + var + "." + fieldName + "}}";
			}
		} else {
			var = dataGridTag.getVar();
			expressFieldName = "${" + var + "." + fieldName + "}";
		}

		return ExpressionEvaluatorManager.evaluate(fieldName, expressFieldName, Object.class, super.getPageContext());
	}

	public String getHeaderAttribute() {
		StringWriter writer = new StringWriter();
		super.writeAttribute(writer, "class", this.gethCssClass());
		super.writeAttribute(writer, "style", this.gethCssStyle());
		return writer.toString();
	}

	public String getBodyAttribute() {
		StringWriter writer = new StringWriter();
		super.writeAttribute(writer, "class", this.getbCssClass());
		super.writeAttribute(writer, "style", this.getbCssStyle());
		return writer.toString();
	}

	protected abstract void doTagHeader() throws JspException, IOException;

	protected abstract void doTagBody() throws JspException, IOException;

	protected abstract void doTagFooter(Object arg0) throws JspException, IOException;

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isHidden() {
		return this.hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;

		try {
			if (hidden) {
				super.setDynamicAttribute((String) null, "style", "display:none");
			}
		} catch (JspException arg2) {
			arg2.printStackTrace();
		}

	}

	public String getOnClick() {
		return this.onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getSuffixText() {
		return this.suffixText;
	}

	public void setSuffixText(String suffixText) {
		this.suffixText = suffixText;
	}

	public String getPrefixText() {
		return this.prefixText;
	}

	public void setPrefixText(String prefixText) {
		this.prefixText = prefixText;
	}

	public String gethCssStyle() {
		return this.hCssStyle;
	}

	public void sethCssStyle(String hCssStyle) {
		this.hCssStyle = hCssStyle;
	}

	public String getbCssStyle() {
		return this.bCssStyle;
	}

	public void setbCssStyle(String bCssStyle) {
		this.bCssStyle = bCssStyle;
	}

	public String gethCssClass() {
		return this.hCssClass;
	}

	public void sethCssClass(String hCssClass) {
		this.hCssClass = hCssClass;
	}

	public String getbCssClass() {
		return this.bCssClass;
	}

	public void setbCssClass(String bCssClass) {
		this.bCssClass = bCssClass;
	}

	public boolean isMultiLangData() {
		return this.multiLangData;
	}

	public void setMultiLangData(boolean multiLangData) {
		this.multiLangData = multiLangData;
	}

	public boolean isSort() {
		return this.sort;
	}

	public void setSort(boolean sort) {
		this.sort = sort;
	}

	public String getSortName() {
		return this.sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getNgClick() {
		return this.ngClick;
	}

	public void setNgClick(String ngClick) {
		this.ngClick = ngClick;
	}

	public boolean isSummary() {
		return this.summary;
	}

	public void setSummary(boolean summary) {
		this.summary = summary;
	}
}