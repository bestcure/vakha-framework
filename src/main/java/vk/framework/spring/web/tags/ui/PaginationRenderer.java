package vk.framework.spring.web.tags.ui;

import vk.framework.spring.web.tags.ui.PaginationInfo;

public interface PaginationRenderer {
	String renderPagination(PaginationInfo arg0, String arg1);
}