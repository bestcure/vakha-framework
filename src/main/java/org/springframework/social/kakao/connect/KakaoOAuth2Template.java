package org.springframework.social.kakao.connect;

import java.util.Arrays;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;

public class KakaoOAuth2Template extends OAuth2Template {
	private Logger logger = LoggerFactory.getLogger(KakaoOAuth2Template.class);

	public KakaoOAuth2Template(String clientId) {
		super(clientId, "", "https://kauth.kakao.com/oauth/authorize", "https://kauth.kakao.com/oauth/token");
		this.setUseParametersForClientAuthentication(true);
	}

	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(new MediaType[]{MediaType.ALL}));
		HttpEntity requestEntity = new HttpEntity(parameters, headers);
		LinkedHashMap response = (LinkedHashMap) this.getRestTemplate().postForObject(accessTokenUrl, requestEntity,
				LinkedHashMap.class, new Object[0]);
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("response : " + response);
		}

		String expires = response.get("expires_in").toString();
		return new AccessGrant(response.get("access_token").toString(), response.get("scope").toString(),
				response.get("refresh_token").toString(), expires != null ? Long.valueOf(expires) : null);
	}
}