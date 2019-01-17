package vk.framework.spring.web.tags.datagrid;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

import vk.framework.spring.web.tags.AbstractTag;
import vk.framework.spring.web.tags.ui.PaginationInfo;

public class DataGridTag extends AbstractTag {
	private int columnCount;
	private String id;
	private String var;
	private String varStatus;
	private Object items;
	private PaginationInfo paginationInfo;
	private String caption;
	private String emptyMessage = "검색된 결과가 없습니다.";
	private String cssStyle;
	private String cssClass;
	private String ngController;
	private boolean footer;

	public void doTag() throws JspException, IOException {
		JspFragment body = super.getJspBody();
		if (body == null) {
			throw new IllegalStateException("You need to place column tags inside the DataGrid tag.");
		} else {
			StringWriter writer = new StringWriter();
			body.invoke(writer);
			super.getJspContext().getOut().print(writer);
		}
	}

	public int getColumnCount() {
		return this.columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVar() {
		return this.var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getVarStatus() {
		return this.varStatus;
	}

	public void setVarStatus(String varStatus) {
		this.varStatus = varStatus;
	}

	public Object getItems() {
		return this.items;
	}

	public void setItems(Object items) {
		this.items = items;
	}

	public PaginationInfo getPaginationInfo() {
		return this.paginationInfo;
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}

	public String getCaption() {
		return this.caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getEmptyMessage() {
		return this.emptyMessage;
	}

	public void setEmptyMessage(String emptyMessage) {
		this.emptyMessage = emptyMessage;
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

	public String getNgController() {
		return this.ngController;
	}

	public void setNgController(String ngController) {
		this.ngController = ngController;
	}

	public boolean isFooter() {
		return this.footer;
	}

	public void setFooter(boolean footer) {
		this.footer = footer;
	}
}