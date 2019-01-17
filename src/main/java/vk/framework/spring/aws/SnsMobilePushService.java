package vk.framework.spring.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.cognitosync.model.Platform;
import com.amazonaws.services.sns.model.MessageAttributeValue;

import vk.framework.spring.aws.SnsClientWrapper;
import vk.framework.spring.dataaccess.util.DataMap;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnsMobilePushService {
	@Autowired
	ServletContext servletContext;
	@Resource(name = "awsProp")
	private Properties awsProp;
	@Autowired
	private SnsClientWrapper snsClientWrapper;
	public static final Map<Platform, Map<String, MessageAttributeValue>> attributesMap = new HashMap<Platform, Map<String, MessageAttributeValue>>();

	public boolean send(List<DataMap> deviceTokenList, String message, String url) throws Exception {
		try {
			ArrayList<DataMap> deviceTokenIOS = new ArrayList<DataMap>();
			ArrayList<DataMap> deviceTokenAndroid = new ArrayList<DataMap>();
			for (DataMap deviceTokenMap : deviceTokenList) {
				if (deviceTokenMap.get((Object) "token") == null || deviceTokenMap.get((Object) "token").equals(""))
					continue;
				String platform = String.valueOf(deviceTokenMap.get((Object) "platform"));
				if (platform.equalsIgnoreCase("IOS")) {
					deviceTokenIOS.add(deviceTokenMap);
					continue;
				}
				if (!platform.equalsIgnoreCase("ANDROID"))
					continue;
				deviceTokenAndroid.add(deviceTokenMap);
			}
			this.appleSandboxAppNotification(deviceTokenIOS, message, url);
			this.androidAppNotification(deviceTokenAndroid, message, url);
		} catch (AmazonServiceException ase) {
			System.out.println(
					"Caught an AmazonServiceException, which means your request made it to Amazon SNS, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + (Object) ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println(
					"Caught an AmazonClientException, which means the client encountered a serious internal problem while trying to communicate with SNS, such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
		return false;
	}

	public boolean send(String platform, String deviceToken, String userId, String message, String url)
			throws Exception {
		try {
			ArrayList<DataMap> deviceTokenIOS = new ArrayList<DataMap>();
			ArrayList<DataMap> deviceTokenAndroid = new ArrayList<DataMap>();
			DataMap dataMap = new DataMap();
			dataMap.put((Object) "platform", (Object) platform);
			dataMap.put((Object) "userId", (Object) userId);
			dataMap.put((Object) "token", (Object) deviceToken);
			if (platform.equalsIgnoreCase("IOS")) {
				deviceTokenIOS.add(dataMap);
				this.appleSandboxAppNotification(deviceTokenIOS, message, url);
			} else if (platform.equalsIgnoreCase("ANDROID")) {
				deviceTokenAndroid.add(dataMap);
				this.androidAppNotification(deviceTokenAndroid, message, url);
			}
		} catch (AmazonServiceException ase) {
			System.out.println(
					"Caught an AmazonServiceException, which means your request made it to Amazon SNS, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + (Object) ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println(
					"Caught an AmazonClientException, which means the client encountered a serious internal problem while trying to communicate with SNS, such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
		return false;
	}

	public void appleSandboxAppNotification(List<DataMap> deviceToken, String message, String url) throws Exception {
		String keyPath = this.servletContext.getRealPath("/WEB-INF/classes");
		File pkFile = new File(keyPath + this.awsProp.getProperty("aws.sns.ios.cert.path"));
		File pkPrivateFile = new File(keyPath + this.awsProp.getProperty("aws.sns.ios.private.path"));
		String certificate = FileUtils.readFileToString((File) pkFile, (String) "UTF-8");
		String privateKey = FileUtils.readFileToString((File) pkPrivateFile, (String) "UTF-8");
		String applicationName = this.awsProp.getProperty("aws.sns.application.name");
		DataMap dataMap = new DataMap();
		dataMap.put((Object) "userId", (Object) "10003");
		dataMap.put((Object) "token", (Object) "df8128ad64e7f6c7f8f19a8825c15fdcd5c7a0584a01f9006d73122e769819a5");
		deviceToken.add(dataMap);
		this.snsClientWrapper.notification(Platform.APNS_SANDBOX, certificate, privateKey, deviceToken, applicationName,
				message, url);
	}

	public void androidAppNotification(List<DataMap> deviceToken, String message, String url) {
		String serverAPIKey = this.awsProp.getProperty("aws.sns.google.api.key");
		String applicationName = this.awsProp.getProperty("aws.sns.application.name");
		DataMap dataMap = new DataMap();
		dataMap.put((Object) "userId", (Object) "10003");
		dataMap.put((Object) "token", (Object) "df8128ad64e7f6c7f8f19a8825c15fdcd5c7a0584a01f9006d73122e769819a5");
		deviceToken.add(dataMap);
		this.snsClientWrapper.notification(Platform.GCM, "", serverAPIKey, deviceToken, applicationName, message, url);
	}

	static {
		attributesMap.put(Platform.ADM, null);
		attributesMap.put(Platform.GCM, null);
		attributesMap.put(Platform.APNS, null);
		attributesMap.put(Platform.APNS_SANDBOX, null);
	}
}