package org.springframework.social.kakao.api;

import java.io.Serializable;
import org.springframework.social.kakao.api.KakaoObject;

public class KakaoTalkProfile extends KakaoObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String nickName;
	private String profileImageURL;
	private String thumbnailURL;
	private String countryISO;

	public String getNickName() {
		return this.nickName;
	}

	public String getProfileImageURL() {
		return this.profileImageURL;
	}

	public String getThumbnailURL() {
		return this.thumbnailURL;
	}

	public String getCountryISO() {
		return this.countryISO;
	}
}