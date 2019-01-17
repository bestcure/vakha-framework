package org.springframework.social.kakao.api.impl;

import org.springframework.social.kakao.api.AccessTokenInfo;
import org.springframework.social.kakao.api.KakaoIds;
import org.springframework.social.kakao.api.KakaoProfile;
import org.springframework.social.kakao.api.UserOperation;
import org.springframework.social.kakao.api.impl.AbstractKakaoOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public class UserTemplate extends AbstractKakaoOperations implements UserOperation {
	private final RestTemplate restTemplate;
	private final RestTemplate adminRestTemplate;

	public UserTemplate(RestTemplate restTemplate, RestTemplate adminRestTemplate, boolean isAuthorized) {
		super(isAuthorized);
		this.restTemplate = restTemplate;
		this.adminRestTemplate = adminRestTemplate;
	}

	public long getProfileId() {
		this.requireAuthorization();
		return this.getUserProfile().getId();
	}

	public String getNickname() {
		this.requireAuthorization();
		return this.getUserProfile().getProperties().getNickname();
	}

	public KakaoProfile getUserProfile() {
		this.requireAuthorization();
		return (KakaoProfile) this.restTemplate.getForObject(this.buildApiUri("/v1/user/me"), KakaoProfile.class);
	}

	public KakaoProfile unlink() {
		this.requireAuthorization();
		return (KakaoProfile) this.restTemplate.postForObject(this.buildApiUri("/v1/user/unlink"), (Object) null,
				KakaoProfile.class);
	}

	public AccessTokenInfo accessTokenInfo() {
		this.requireAuthorization();
		return (AccessTokenInfo) this.restTemplate.getForObject(this.buildApiUri("/v1/user/access_token_info"),
				AccessTokenInfo.class);
	}

	public KakaoProfile updateProfile(String profileJsonString) {
		this.requireAuthorization();
		LinkedMultiValueMap param = new LinkedMultiValueMap();
		param.set("properties", profileJsonString);
		return (KakaoProfile) this.restTemplate.postForObject(this.buildApiUri("/v1/user/update_profile"), param,
				KakaoProfile.class);
	}

	public KakaoProfile logout() {
		this.requireAuthorization();
		return (KakaoProfile) this.restTemplate.postForObject(this.buildApiUri("/v1/user/logout"), (Object) null,
				KakaoProfile.class);
	}

	public KakaoProfile signup(String profileJsonString) {
		LinkedMultiValueMap param = new LinkedMultiValueMap();
		if (!StringUtils.isEmpty(profileJsonString)) {
			param.set("properties", profileJsonString);
		}

		return (KakaoProfile) this.restTemplate.postForObject(this.buildApiUri("/v1/user/signup"), param,
				KakaoProfile.class);
	}

	public KakaoIds ids() {
		return this.ids("", "", "");
	}

	public KakaoIds ids(String limit, String fromId, String order) {
		LinkedMultiValueMap param = new LinkedMultiValueMap();
		param.set("limit", limit);
		param.set("fromId", fromId);
		param.set("order", order);
		return (KakaoIds) this.adminRestTemplate.getForObject(this.buildApiUri("/v1/user/ids", param), KakaoIds.class);
	}

	public KakaoProfile getUserProfile(String userId) {
		LinkedMultiValueMap param = new LinkedMultiValueMap();
		param.set("user_id", userId);
		return (KakaoProfile) this.adminRestTemplate.getForObject(this.buildApiUri("/v1/user/me", param),
				KakaoProfile.class);
	}
}