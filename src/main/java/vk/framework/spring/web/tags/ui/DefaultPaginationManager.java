package vk.framework.spring.web.tags.ui;

import java.util.Map;

import vk.framework.spring.web.tags.ui.DefaultPaginationRenderer;
import vk.framework.spring.web.tags.ui.PaginationManager;
import vk.framework.spring.web.tags.ui.PaginationRenderer;

public class DefaultPaginationManager implements PaginationManager {
	private Map<String, PaginationRenderer> rendererType;

	public void setRendererType(Map<String, PaginationRenderer> rendererType) {
		this.rendererType = rendererType;
	}

	public PaginationRenderer getRendererType(String type) {
		return (PaginationRenderer) (this.rendererType != null && this.rendererType.containsKey(type)
				? (PaginationRenderer) this.rendererType.get(type)
				: new DefaultPaginationRenderer());
	}
}