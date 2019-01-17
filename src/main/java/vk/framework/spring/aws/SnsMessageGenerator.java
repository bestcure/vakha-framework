package vk.framework.spring.aws;

import java.util.HashMap;
import org.codehaus.jackson.map.ObjectMapper;

public class SnsMessageGenerator {
	public static final String defaultMessage = "This is the default message";
	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static String jsonify(Object message) {
		try {
			return objectMapper.writeValueAsString(message);
		} catch (Exception arg1) {
			arg1.printStackTrace();
			throw (RuntimeException) arg1;
		}
	}

	public static String getAppleMessage(String message, String url) {
		HashMap appleMessageMap = new HashMap();
		HashMap appMessageMap = new HashMap();
		appMessageMap.put("alert", message);
		appMessageMap.put("sam_url", url);
		appMessageMap.put("badge", Integer.valueOf(9));
		appMessageMap.put("sound", "default");
		appleMessageMap.put("aps", appMessageMap);
		return jsonify(appleMessageMap);
	}

	public static String getAndroidMessage(String message, String url) {
		HashMap payload = new HashMap();
		payload.put("message", message);
		payload.put("sam_url", url);
		HashMap androidMessageMap = new HashMap();
		androidMessageMap.put("data", payload);
		return jsonify(androidMessageMap);
	}

	public static String getBaiduMessage() {
		HashMap baiduMessageMap = new HashMap();
		baiduMessageMap.put("title", "New Notification Received from SNS");
		baiduMessageMap.put("description", "Hello World!");
		return jsonify(baiduMessageMap);
	}

	public static String getWNSMessage() {
		HashMap wnsMessageMap = new HashMap();
		wnsMessageMap.put("version", "1");
		wnsMessageMap.put("value", "23");
		return "<badge version=\"" + wnsMessageMap.get("version") + "\" value=\"" + wnsMessageMap.get("value") + "\"/>";
	}

	public static String getMPNSMessage() {
		HashMap mpnsMessageMap = new HashMap();
		mpnsMessageMap.put("count", "23");
		mpnsMessageMap.put("payload", "This is a tile notification");
		return "<?xml version=\"1.0\" encoding=\"utf-8\"?><wp:Notification xmlns:wp=\"WPNotification\"><wp:Tile><wp:Count>"
				+ (String) mpnsMessageMap.get("count") + "</wp:Count><wp:Title>"
				+ (String) mpnsMessageMap.get("payload") + "</wp:Title></wp:Tile></wp:Notification>";
	}
}