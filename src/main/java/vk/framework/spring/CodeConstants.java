package vk.framework.spring;

import java.util.LinkedHashMap;
import java.util.Map;

public class CodeConstants {
	public static final Map<String, String> USE_AT = new LinkedHashMap();
	public static final Map<String, String> WORD_DICARY_REGIST_SE;
	public static final Map<String, String> ESSNTL_RSPNS_AT;
	public static final String SESSION_KEY = "SESSION_KEY";
	public static final String TOKEN_KEY = "TOKEN";
	public static final String DEVICE_KEY = "DEVICE_KEY";
	public static final String PAYMENT_KEY = "PAYMENT_KEY";
	public static final String JSON_INPUT_DATA = "inputData";

	static {
		USE_AT.put("Y", "사용");
		USE_AT.put("N", "미사용");
		WORD_DICARY_REGIST_SE = new LinkedHashMap();
		WORD_DICARY_REGIST_SE.put("S", "행정표준용어");
		WORD_DICARY_REGIST_SE.put("T", "서브시스템구분");
		WORD_DICARY_REGIST_SE.put("U", "사용자정의");
		ESSNTL_RSPNS_AT = new LinkedHashMap();
		ESSNTL_RSPNS_AT.put("Y", "필수");
		ESSNTL_RSPNS_AT.put("N", "필수아님");
	}
}