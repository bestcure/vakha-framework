package vk.framework.spring.web.tags.ui;

import vk.framework.spring.web.tags.ui.AbstractPaginationRenderer;
import vk.framework.spring.web.tags.ui.PaginationInfo;

public class DefaultPaginationRenderer extends AbstractPaginationRenderer {
	public DefaultPaginationRenderer() {
		this.firstPageLabel = "<li class=\"ppv\"><a href=\"#none\" onclick=\"{0}({1}); return false;\">처음</a></li>";
		this.previousPageLabel = "<li class=\"pv\"><a href=\"#none\" onclick=\"{0}({1}); return false;\">이전</a></li>";
		this.currentPageLabel = "<li class=\"now\"><a href=\"#none\">{0}</a></li>";
		this.otherPageLabel = "<li><a href=\"#\" onclick=\"{0}({1}); return false;\">{2}</a></li>";
		this.nextPageLabel = "<li class=\"fw\"><a href=\"#\" onclick=\"{0}({1}); return false;\">다음</a></li>";
		this.lastPageLabel = "<li class=\"ffw\"><a href=\"#\" onclick=\"{0}({1}); return false;\">마지막</a></li>";
	}

	public String renderPagination(PaginationInfo paginationInfo, String jsFunction) {
		return super.renderPagination(paginationInfo, jsFunction);
	}
}