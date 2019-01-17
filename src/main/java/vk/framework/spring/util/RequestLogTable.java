package vk.framework.spring.util;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestLogTable {
	protected Log log = LogFactory.getLog((String) "request.log");
	private static final String HEADER_KEY = "key";
	private static final String HEADER_VALUE = "value";
	private static final String SPACE = " ";
	private HttpServletRequest httpServletRequest;
	private String currentUrl;
	private int keyMaxLen;
	private int valueMaxLen;
	private int maxLen;

	public RequestLogTable(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
		this.currentUrl = this.httpServletRequest.getRequestURL().toString();
		this.init();
	}

	private void init() {
		this.initParameter();
		this.initHeader();
		if (this.keyMaxLen < "key".length()) {
			this.keyMaxLen = "key".length();
		}
		if (this.valueMaxLen < "value".length()) {
			this.valueMaxLen = "value".length();
		}
		this.maxLen = this.keyMaxLen + this.valueMaxLen + 1;
		if (this.maxLen < this.currentUrl.length()) {
			this.valueMaxLen = this.currentUrl.length() - this.keyMaxLen;
		}
	}

	private void initParameter() {
		String key = "";
		Enumeration parameterNames = this.httpServletRequest.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			key = (String) parameterNames.nextElement();
			if (key.length() > this.keyMaxLen) {
				this.keyMaxLen = key.length();
			}
			for (String parameter : this.httpServletRequest.getParameterValues(key)) {
				if (parameter.length() <= this.valueMaxLen)
					continue;
				this.valueMaxLen = parameter.length();
			}
		}
	}

	private void initHeader() {
		String key = "";
		String value = "";
		Enumeration headerNames = this.httpServletRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			key = (String) headerNames.nextElement();
			value = this.httpServletRequest.getHeader(key);
			if (key.length() > this.keyMaxLen) {
				this.keyMaxLen = key.length();
			}
			if (value.length() <= this.valueMaxLen)
				continue;
			this.valueMaxLen = value.length();
		}
	}

	public void printLog() {
		String httpHeader = ">> HTTP Header ";
		String httpParameter = ">> HTTP Request Parameter  ";
		StringBuilder sb = new StringBuilder();
		this.printBorderTop();
		this.printUrlHeader();
		sb = new StringBuilder();
		sb.append((Object) Border.VERTICAL_LINE);
		sb.append(httpHeader);
		sb.append(StringUtils.repeat((String) " ", (int) (this.maxLen - httpHeader.length())));
		sb.append((Object) Border.VERTICAL_LINE);
		this.logWrite(sb.toString());
		this.printKeyValueHeader();
		String key = "";
		Enumeration headerNames = this.httpServletRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			key = (String) headerNames.nextElement();
			this.printKeyValueData(key, this.httpServletRequest.getHeader(key));
		}
		sb = new StringBuilder();
		sb.append((Object) Border.MIDDLE_LEFT);
		sb.append(StringUtils.repeat((String) Border.HORIZONTAL_LINE.toString(), (int) this.keyMaxLen));
		sb.append((Object) Border.BOTTOM_SEPARATION);
		sb.append(StringUtils.repeat((String) Border.HORIZONTAL_LINE.toString(), (int) this.valueMaxLen));
		sb.append((Object) Border.MIDDLE_RIGHT);
		this.logWrite(sb.toString());
		sb = new StringBuilder();
		sb.append((Object) Border.VERTICAL_LINE);
		sb.append(httpParameter);
		sb.append(StringUtils.repeat((String) " ", (int) (this.maxLen - httpParameter.length())));
		sb.append((Object) Border.VERTICAL_LINE);
		this.logWrite(sb.toString());
		this.printKeyValueHeader();
		Enumeration parameterNames = this.httpServletRequest.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			key = (String) parameterNames.nextElement();
			for (String parameter : this.httpServletRequest.getParameterValues(key)) {
				this.printKeyValueData(key, parameter);
			}
		}
		this.printBorderBottom();
	}

	private void printBorderTop() {
		StringBuilder sb = new StringBuilder();
		sb.append((Object) Border.TOP_LEFT);
		sb.append(StringUtils.repeat((String) Border.HORIZONTAL_LINE.toString(), (int) this.maxLen));
		sb.append((Object) Border.TOP_RIGHT);
		this.logWrite(sb.toString());
	}

	private void printBorderBottom() {
		StringBuilder sb = new StringBuilder();
		sb.append((Object) Border.BOTTOM_LEFT);
		sb.append(StringUtils.repeat((String) Border.HORIZONTAL_LINE.toString(), (int) this.keyMaxLen));
		sb.append((Object) Border.BOTTOM_SEPARATION);
		sb.append(StringUtils.repeat((String) Border.HORIZONTAL_LINE.toString(), (int) this.valueMaxLen));
		sb.append((Object) Border.BOTTOM_RIGHT);
		this.logWrite(sb.toString());
	}

	private void printUrlHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append((Object) Border.VERTICAL_LINE);
		sb.append(this.currentUrl);
		sb.append(StringUtils.repeat((String) " ", (int) (this.maxLen - this.currentUrl.length())));
		sb.append((Object) Border.VERTICAL_LINE);
		this.logWrite(sb.toString());
		sb = new StringBuilder();
		sb.append((Object) Border.MIDDLE_LEFT);
		sb.append(StringUtils.repeat((String) Border.HORIZONTAL_LINE.toString(), (int) this.maxLen));
		sb.append((Object) Border.MIDDLE_RIGHT);
		this.logWrite(sb.toString());
	}

	private void printKeyValueHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append((Object) Border.MIDDLE_LEFT);
		sb.append(StringUtils.repeat((String) Border.HORIZONTAL_LINE.toString(), (int) this.keyMaxLen));
		sb.append((Object) Border.TOP_SEPARATION);
		sb.append(StringUtils.repeat((String) Border.HORIZONTAL_LINE.toString(), (int) this.valueMaxLen));
		sb.append((Object) Border.MIDDLE_RIGHT);
		this.logWrite(sb.toString());
		sb = new StringBuilder();
		sb.append((Object) Border.VERTICAL_LINE);
		sb.append("key");
		sb.append(StringUtils.repeat((String) " ", (int) (this.keyMaxLen - "key".length())));
		sb.append((Object) Border.VERTICAL_LINE);
		sb.append("value");
		sb.append(StringUtils.repeat((String) " ", (int) (this.valueMaxLen - "value".length())));
		sb.append((Object) Border.VERTICAL_LINE);
		this.logWrite(sb.toString());
		sb = new StringBuilder();
		sb.append((Object) Border.MIDDLE_LEFT);
		sb.append(StringUtils.repeat((String) Border.HORIZONTAL_LINE.toString(), (int) this.keyMaxLen));
		sb.append((Object) Border.MIDDLE_SEPARATION);
		sb.append(StringUtils.repeat((String) Border.HORIZONTAL_LINE.toString(), (int) this.valueMaxLen));
		sb.append((Object) Border.MIDDLE_RIGHT);
		this.logWrite(sb.toString());
	}

	private void printKeyValueData(String key, String value) {
		StringBuilder sb = new StringBuilder();
		sb.append((Object) Border.VERTICAL_LINE);
		sb.append(key);
		sb.append(StringUtils.repeat((String) " ", (int) (this.keyMaxLen - key.length())));
		sb.append((Object) Border.VERTICAL_LINE);
		sb.append(value);
		sb.append(StringUtils.repeat((String) " ", (int) (this.valueMaxLen - value.length())));
		sb.append((Object) Border.VERTICAL_LINE);
		this.logWrite(sb.toString());
	}

	private void logWrite(String value) {
		this.log.debug((Object) value);
	}

	private static enum Border {
		TOP_LEFT("┌"), TOP_SEPARATION("┬"), TOP_RIGHT("┐"), MIDDLE_LEFT("├"), MIDDLE_SEPARATION("┼"), MIDDLE_RIGHT(
				"┤"), BOTTOM_LEFT(
						"└"), BOTTOM_SEPARATION("┴"), BOTTOM_RIGHT("┘"), HORIZONTAL_LINE("─"), VERTICAL_LINE("│");

		private String value = "";

		private Border(String value) {
			this.value = value;
		}

		public String toString() {
			return this.value;
		}
	}

}