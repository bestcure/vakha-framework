package vk.framework.spring.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MessageVo {
	private String sessionId;
	private String userId;
	private String userNm;
	private String message;

	public final String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getUserNm() {
		return this.userNm;
	}

	public String getMessage() {
		return this.message;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}