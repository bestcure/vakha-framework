package org.springframework.social.kakao.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ForApns {
	private boolean isEmpty = true;
	private int badge;
	private String sound;
	private boolean push_alert;
	private Object message;
	private Map<String, Object> custom_field = new LinkedHashMap();

	@JsonIgnore
	public boolean isEmpty() {
		return this.isEmpty;
	}

	public int getBadge() {
		return this.badge;
	}

	public void setBadge(int badge) {
		this.isEmpty = false;
		this.badge = badge;
	}

	public String getSound() {
		return this.sound;
	}

	public void setSound(String sound) {
		this.isEmpty = false;
		this.sound = sound;
	}

	public boolean isPush_alert() {
		return this.push_alert;
	}

	public void setPush_alert(boolean push_alert) {
		this.isEmpty = false;
		this.push_alert = push_alert;
	}

	public Object getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.isEmpty = false;
		this.message = message;
	}

	public void setMessage(String locKey, List<String> locArgs) {
		this.isEmpty = false;
		LinkedHashMap tmp = new LinkedHashMap();
		tmp.put("loc-key", locKey);
		tmp.put("loc-args", locArgs);
		this.message = tmp;
	}

	public void addCustom_field(String key, Object value) {
		this.isEmpty = false;
		this.custom_field.put(key, value);
	}

	public Map<String, Object> getCustom_field() {
		return this.custom_field;
	}

	public ForApns() {
	}

	public ForApns(int badge, String sound, boolean push_alert, String message) {
		this.isEmpty = false;
		this.badge = badge;
		this.sound = sound;
		this.push_alert = push_alert;
		this.message = message;
	}

	public ForApns(int badge, String sound, boolean push_alert, String locKey, List<String> locArgs) {
		this.isEmpty = false;
		this.badge = badge;
		this.sound = sound;
		this.push_alert = push_alert;
		this.setMessage(locKey, locArgs);
	}
}