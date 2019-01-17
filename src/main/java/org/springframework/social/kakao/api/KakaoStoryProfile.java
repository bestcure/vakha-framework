package org.springframework.social.kakao.api;

import java.io.Serializable;
import org.springframework.social.kakao.api.KakaoObject;

public class KakaoStoryProfile extends KakaoObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean isStoryUser;
	private String nickName;
	private String profileImageURL;
	private String thumbnailURL;
	private String bgImageURL;
	private String permalink;
	private String birthday;
	private String birthdayType;

	public boolean getIsStoryUser() {
		return this.isStoryUser;
	}

	public String getNickName() {
		return this.nickName;
	}

	public String getProfileImageURL() {
		return this.profileImageURL;
	}

	public String getThumbnailURL() {
		return this.thumbnailURL;
	}

	public String getBgImageURL() {
		return this.bgImageURL;
	}

	public String getPermalink() {
		return this.permalink;
	}

	public String getBirthday() {
		return this.birthday;
	}

	public String getBirthdayType() {
		return this.birthdayType;
	}
}