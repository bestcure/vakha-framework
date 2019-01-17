package vk.framework.spring.aws;

import com.amazonaws.services.cognitosync.model.Platform;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.CreatePlatformApplicationRequest;
import com.amazonaws.services.sns.model.CreatePlatformApplicationResult;
import com.amazonaws.services.sns.model.CreatePlatformEndpointRequest;
import com.amazonaws.services.sns.model.CreatePlatformEndpointResult;
import com.amazonaws.services.sns.model.DeletePlatformApplicationRequest;
import com.amazonaws.services.sns.model.DeletePlatformApplicationResult;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import vk.framework.spring.aws.SnsMessageGenerator;
import vk.framework.spring.dataaccess.util.DataMap;

import java.io.PrintStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnsClientWrapper {
	@Autowired(required = false)
	private AmazonSNS snsClient;

	public void notification(Platform platform, String principal, String credential, List<DataMap> deviceToken,
			String applicationName, String message, String url) {
		applicationName = applicationName + "_" + Calendar.getInstance().getTimeInMillis();
		CreatePlatformApplicationResult platformApplicationResult = this.createPlatformApplication(applicationName,
				platform, principal, credential);
		String platformApplicationArn = platformApplicationResult.getPlatformApplicationArn();
		for (DataMap token : deviceToken) {
			CreatePlatformEndpointResult platformEndpointResult = this.createPlatformEndpoint(platform,
					String.valueOf(token.get((Object) "userId")), String.valueOf(token.get((Object) "token")),
					platformApplicationArn);
			PublishResult publishResult = this.publish(platformEndpointResult.getEndpointArn(), platform, message, url);
			System.out.println("Published! \n{MessageId=" + publishResult.getMessageId() + "}");
		}
		this.deletePlatformApplication(platformApplicationArn);
	}

	private CreatePlatformApplicationResult createPlatformApplication(String applicationName, Platform platform,
			String principal, String credential) {
		CreatePlatformApplicationRequest platformApplicationRequest = new CreatePlatformApplicationRequest();
		HashMap<String, String> attributes = new HashMap<String, String>();
		attributes.put("PlatformPrincipal", principal);
		attributes.put("PlatformCredential", credential);
		platformApplicationRequest.setAttributes(attributes);
		platformApplicationRequest.setName(applicationName);
		platformApplicationRequest.setPlatform(platform.name());
		return this.snsClient.createPlatformApplication(platformApplicationRequest);
	}

	private CreatePlatformEndpointResult createPlatformEndpoint(Platform platform, String customData,
			String platformToken, String applicationArn) {
		CreatePlatformEndpointRequest platformEndpointRequest = new CreatePlatformEndpointRequest();
		platformEndpointRequest.setCustomUserData(customData);
		String token = platformToken;
		platformEndpointRequest.setToken(token);
		platformEndpointRequest.setPlatformApplicationArn(applicationArn);
		return this.snsClient.createPlatformEndpoint(platformEndpointRequest);
	}

	private void deletePlatformApplication(String applicationArn) {
		DeletePlatformApplicationRequest request = new DeletePlatformApplicationRequest();
		request.setPlatformApplicationArn(applicationArn);
		this.snsClient.deletePlatformApplication(request);
	}

	private PublishResult publish(String endpointArn, Platform platform, String message, String url) {
		PublishRequest publishRequest = new PublishRequest();
		publishRequest.setMessageStructure("json");
		String generateMessage = this.getPlatformMessage(platform, message, url);
		HashMap<String, String> messageMap = new HashMap<String, String>();
		messageMap.put(platform.name(), generateMessage);
		generateMessage = SnsMessageGenerator.jsonify(messageMap);
		publishRequest.setTargetArn(endpointArn);
		System.out.println("{Message Body: " + generateMessage + "}");
		publishRequest.setMessage(generateMessage);
		return this.snsClient.publish(publishRequest);
	}

	private String getPlatformMessage(Platform platform, String message, String url) {
		switch (platform) {
			case APNS : {
				return SnsMessageGenerator.getAppleMessage((String) message, (String) url);
			}
			case APNS_SANDBOX : {
				return SnsMessageGenerator.getAppleMessage((String) message, (String) url);
			}
			case GCM : {
				return SnsMessageGenerator.getAndroidMessage((String) message, (String) url);
			}
		}
		throw new IllegalArgumentException("Platform not supported : " + platform.name());
	}

}