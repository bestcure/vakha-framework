package org.springframework.social.kakao.api.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.springframework.social.kakao.api.ForApns;
import org.springframework.social.kakao.api.ForGcm;
import org.springframework.social.kakao.api.PushOperation;
import org.springframework.social.kakao.api.PushToken;
import org.springframework.social.kakao.api.impl.AbstractKakaoOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

public class PushTemplate extends AbstractKakaoOperations implements PushOperation {
	private final RestTemplate adminRestTemplate;

	public PushTemplate(RestTemplate adminRestTemplate, boolean isAuthorized) {
		super(isAuthorized);
		this.adminRestTemplate = adminRestTemplate;
	}

	public String register(String uuid, String deviceId, String pushType, String pushToken) {
		LinkedMultiValueMap parameters = new LinkedMultiValueMap();
		parameters.set("uuid", uuid);
		parameters.set("device_id", deviceId);
		parameters.set("push_type", pushType);
		parameters.set("push_token", pushToken);
		return (String) this.adminRestTemplate.postForObject(this.buildApiUri("/v1/push/register"), parameters,
				String.class);
	}

	public List<PushToken> tokens(String uuid) {
		LinkedMultiValueMap parameters = new LinkedMultiValueMap();
		parameters.set("uuid", uuid);
		PushToken[] arrPushToken = (PushToken[]) this.adminRestTemplate
				.getForObject(this.buildApiUri("/v1/push/tokens", parameters), PushToken[].class);
		return Arrays.asList(arrPushToken);
	}

	public void deregister(String uuid, String deviceId, String pushType) {
		LinkedMultiValueMap parameters = new LinkedMultiValueMap();
		parameters.set("uuid", uuid);
		parameters.set("device_id", deviceId);
		parameters.set("push_type", pushType);
		this.adminRestTemplate.postForLocation(this.buildApiUri("/v1/push/deregister"), parameters);
	}

	public void send(List<String> uuids, ForApns forApns, ForGcm forGcm) {
		LinkedMultiValueMap parameters = new LinkedMultiValueMap();
		parameters.set("uuids", uuids);
		if (forApns != null) {
			if (forApns.isEmpty()) {
				parameters.set("for_apns", new HashMap());
			} else {
				parameters.set("for_apns", forApns);
			}
		}

		if (forGcm != null) {
			if (forGcm.isEmpty()) {
				parameters.set("for_gcm", new HashMap());
			} else {
				parameters.set("for_gcm", forGcm);
			}
		}

		this.adminRestTemplate.postForLocation(this.buildApiUri("/v1/push/send"), parameters);
	}
}