package org.springframework.social.kakao.api.impl;

import java.net.URI;
import java.util.Arrays;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class AbstractKakaoOperations {
	private final boolean isAuthorized;
	private static final String API_HOST = "https://kapi.kakao.com";
	private static final MultiValueMap<String, String> EMPTY_PARAMETER = new LinkedMultiValueMap();

	public AbstractKakaoOperations(boolean isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	protected void requireAuthorization() {
		if (!this.isAuthorized) {
			throw new MissingAuthorizationException("kakao");
		}
	}

	protected URI buildApiUri(String path) {
		return this.buildApiUri(path, EMPTY_PARAMETER);
	}

	protected URI buildApiUri(String path, MultiValueMap<String, String> parameters) {
		return URIBuilder.fromUri("https://kapi.kakao.com" + path).queryParams(parameters).build();
	}

	protected HttpHeaders getAdminKeyHeader(String adminKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(new MediaType[]{MediaType.ALL}));
		headers.set("Authorization", "KakaoAK " + adminKey);
		return headers;
	}
}