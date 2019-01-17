package org.springframework.social.kakao.api;

import java.io.Serializable;
import org.springframework.social.kakao.api.KakaoObject;

public class KakaoProfileProperties extends KakaoObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nickname;
	private String thumbnail_image;
	private String profile_image;

	public String getNickname() {
		return this.nickname;
	}

	public String getThumbnail_image() {
		return this.thumbnail_image;
	}

	public String getProfile_image() {
		return this.profile_image;
	}
}