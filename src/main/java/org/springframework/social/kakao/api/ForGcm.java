package org.springframework.social.kakao.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.LinkedHashMap;
import java.util.Map;

public class ForGcm {
	private boolean isEmpty = true;
	private String collapse;
	private boolean delay_while_idle;
	private Map<String, Object> custom_field = new LinkedHashMap();
	private String return_url;

	@JsonIgnore
	public boolean isEmpty() {
		return this.isEmpty;
	}

	public String getCollapse() {
		return this.collapse;
	}

	public void setCollapse(String collapse) {
		this.isEmpty = false;
		this.collapse = collapse;
	}

	public boolean isDelay_while_idle() {
		return this.delay_while_idle;
	}

	public void setDelay_while_idle(boolean delay_while_idle) {
		this.isEmpty = false;
		this.delay_while_idle = delay_while_idle;
	}

	public String getReturn_url() {
		return this.return_url;
	}

	public void setReturn_url(String return_url) {
		this.isEmpty = false;
		this.return_url = return_url;
	}

	public void addCustom_field(String key, Object value) {
		this.custom_field.put(key, value);
	}

	public Map<String, Object> getCustom_field() {
		return this.custom_field;
	}

	public ForGcm() {
	}

	public ForGcm(String collapse, boolean delayWhileIdle, String return_url) {
		this.isEmpty = false;
		this.collapse = collapse;
		this.delay_while_idle = delayWhileIdle;
		this.return_url = return_url;
	}
}