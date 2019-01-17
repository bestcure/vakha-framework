package org.springframework.social.kakao.api;

import org.springframework.social.kakao.api.AccessTokenInfo;
import org.springframework.social.kakao.api.KakaoIds;
import org.springframework.social.kakao.api.KakaoProfile;

public interface UserOperation {
	long getProfileId();

	String getNickname();

	KakaoProfile getUserProfile();

	KakaoProfile unlink();

	AccessTokenInfo accessTokenInfo();

	KakaoProfile updateProfile(String arg0);

	KakaoProfile logout();

	KakaoProfile signup(String arg0);

	KakaoIds ids();

	KakaoIds ids(String arg0, String arg1, String arg2);

	KakaoProfile getUserProfile(String arg0);
}