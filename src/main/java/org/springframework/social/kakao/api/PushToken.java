package org.springframework.social.kakao.api;

import java.io.Serializable;
import org.springframework.social.kakao.api.KakaoObject;

public class PushToken extends KakaoObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String user_id;
	private String device_id;
	private String push_type;
	private String push_token;
	private String created_at;
	private String updated_at;

	public String getUser_id() {
		return this.user_id;
	}

	public String getDevice_id() {
		return this.device_id;
	}

	public String getPush_type() {
		return this.push_type;
	}

	public String getPush_token() {
		return this.push_token;
	}

	public String getCreated_at() {
		return this.created_at;
	}

	public String getUpdated_at() {
		return this.updated_at;
	}
}