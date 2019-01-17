package org.springframework.social.kakao.api.impl;

import org.springframework.social.kakao.api.KakaoTalkProfile;
import org.springframework.social.kakao.api.TalkOperation;
import org.springframework.social.kakao.api.impl.AbstractKakaoOperations;
import org.springframework.web.client.RestTemplate;

public class TalkTemplate extends AbstractKakaoOperations implements TalkOperation {
	private final RestTemplate restTemplate;

	public TalkTemplate(RestTemplate restTemplate, boolean isAuthorized) {
		super(isAuthorized);
		this.restTemplate = restTemplate;
	}

	public KakaoTalkProfile getUserProfile() {
		this.requireAuthorization();
		return (KakaoTalkProfile) this.restTemplate.getForObject(this.buildApiUri("/v1/api/talk/profile"),
				KakaoTalkProfile.class);
	}
}