package org.springframework.social.kakao.api.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.kakao.api.PushOperation;
import org.springframework.social.kakao.api.StoryOperation;
import org.springframework.social.kakao.api.TalkOperation;
import org.springframework.social.kakao.api.UserOperation;
import org.springframework.social.kakao.api.impl.PushTemplate;
import org.springframework.social.kakao.api.impl.StoryTemplate;
import org.springframework.social.kakao.api.impl.TalkTemplate;
import org.springframework.social.kakao.api.impl.UserTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.social.support.HttpRequestDecorator;
import org.springframework.web.client.RestTemplate;

public class KakaoTemplate extends AbstractOAuth2ApiBinding implements Kakao {
	private UserOperation userOperation;
	private StoryOperation storyOperation;
	private TalkOperation talkOperation;
	private PushOperation pushOperation;
	private String adminKey;
	private RestTemplate adminRestTemplate;

	public KakaoTemplate() {
		this.initialize();
	}

	@Deprecated
	public KakaoTemplate(String accessToken) {
		super(accessToken);
		this.initialize();
	}

	public KakaoTemplate(String accessToken, String adminKey) {
		super(accessToken);
		this.adminKey = adminKey;
		this.initialize();
	}

	public void setRequestFactory(ClientHttpRequestFactory requestFactory) {
		super.setRequestFactory(
				ClientHttpRequestFactorySelector.bufferRequests((ClientHttpRequestFactory) requestFactory));
	}

	public UserOperation userOperation() {
		return this.userOperation;
	}

	public StoryOperation storyOperation() {
		return this.storyOperation;
	}

	public TalkOperation talkOperation() {
		return this.talkOperation;
	}

	public PushOperation pushOperation() {
		return this.pushOperation;
	}

	private void initialize() {
		super.setRequestFactory(ClientHttpRequestFactorySelector
				.bufferRequests((ClientHttpRequestFactory) this.getRestTemplate().getRequestFactory()));
		this.adminRestTemplate = new RestTemplate(ClientHttpRequestFactorySelector.getRequestFactory());
		this.adminRestTemplate
				.setInterceptors(Arrays.asList(new AdminKeyHeaderOAuth2RequestInterceptor(this.adminKey)));
		this.initSubApis();
	}

	private void initSubApis() {
		this.userOperation = new UserTemplate(this.getRestTemplate(), this.adminRestTemplate, this.isAuthorized());
		this.storyOperation = new StoryTemplate(this.getRestTemplate(), this.isAuthorized());
		this.talkOperation = new TalkTemplate(this.getRestTemplate(), this.isAuthorized());
		this.pushOperation = new PushTemplate(this.adminRestTemplate, this.isAuthorized());
	}

	class AdminKeyHeaderOAuth2RequestInterceptor implements ClientHttpRequestInterceptor {
		private final String adminKey;

		public AdminKeyHeaderOAuth2RequestInterceptor(String adminKey) {
			this.adminKey = adminKey;
		}

		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
				throws IOException {
			HttpRequestDecorator protectedResourceRequest = new HttpRequestDecorator(request);
			protectedResourceRequest.getHeaders().set("Authorization", "KakaoAK " + this.adminKey);
			return execution.execute((HttpRequest) protectedResourceRequest, body);
		}
	}

}