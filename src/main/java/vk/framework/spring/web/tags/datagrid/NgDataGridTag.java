package vk.framework.spring.web.tags.datagrid;

import java.io.IOException;
import java.io.StringWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;

import vk.framework.spring.web.tags.AbstractTag;
import vk.framework.spring.web.tags.ui.PaginationInfo;

public class NgDataGridTag extends AbstractTag {
	private int columnCount;
	private String id;
	private String var;
	private String varStatus;
	private String items;
	private boolean paging;
	private boolean autoLoad = true;
	private String url;
	private String dataForm;
	private PaginationInfo paginationInfo;
	private String caption;
	private String emptyMessage = "검색된 결과가 없습니다.";
	private String cssStyle;
	private String cssClass;
	private String ngController;
	private String rowHeight;

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

	public String getId() {
		return this.id;
	}

	public String getVar() {
		return this.var;
	}

	public String getVarStatus() {
		return this.varStatus;
	}

	public String getItems() {
		return this.items;
	}

	public boolean isPaging() {
		return this.paging;
	}

	public boolean isAutoLoad() {
		return this.autoLoad;
	}

	public String getUrl() {
		return this.url;
	}

	public String getDataForm() {
		return this.dataForm;
	}

	public PaginationInfo getPaginationInfo() {
		return this.paginationInfo;
	}

	public String getCaption() {
		return this.caption;
	}

	public String getEmptyMessage() {
		return this.emptyMessage;
	}

	public String getCssStyle() {
		return this.cssStyle;
	}

	public String getCssClass() {
		return this.cssClass;
	}

	public String getNgController() {
		return this.ngController;
	}

	public String getRowHeight() {
		return this.rowHeight;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setVarStatus(String varStatus) {
		this.varStatus = varStatus;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public void setPaging(boolean paging) {
		this.paging = paging;
	}

	public void setAutoLoad(boolean autoLoad) {
		this.autoLoad = autoLoad;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setDataForm(String dataForm) {
		this.dataForm = dataForm;
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setEmptyMessage(String emptyMessage) {
		this.emptyMessage = emptyMessage;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setNgController(String ngController) {
		this.ngController = ngController;
	}

	public void setRowHeight(String rowHeight) {
		this.rowHeight = rowHeight;
	}
}