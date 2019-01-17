package vk.framework.spring.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.analytics.Analytics;
import com.google.api.services.analytics.Analytics.Builder;
import javax.annotation.Resource;

public class GoogleAnalyticsService {
	@Resource(name = "googleCredentialService")
	Credential googleCredentialService;
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static HttpTransport HTTP_TRANSPORT;

	public Analytics initialize(String applicationName) throws Exception {
		return (new Builder(HTTP_TRANSPORT, JSON_FACTORY, this.googleCredentialService))
				.setApplicationName(applicationName).build();
	}
}