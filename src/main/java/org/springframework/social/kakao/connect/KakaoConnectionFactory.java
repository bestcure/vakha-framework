package org.springframework.social.kakao.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.kakao.api.Kakao;
import org.springframework.social.kakao.connect.KakaoAdapter;
import org.springframework.social.kakao.connect.KakaoServiceProvider;

public class KakaoConnectionFactory extends OAuth2ConnectionFactory<Kakao> {
	public KakaoConnectionFactory(String clientId) {
		super("kakao", new KakaoServiceProvider(clientId), new KakaoAdapter());
	}
}