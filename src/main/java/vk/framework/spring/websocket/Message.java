package vk.framework.spring.websocket;

public class Message {
	private String sessionId;
	private String userId;
	private String userNm;
	private String message;
	private String messageDivision;
	private String linkCd;
	private String parameters;
	private String time;

	public String toString() {
		return "ChatMessage [sessionId=" + this.sessionId + ", userNm=" + this.userNm + ", message=" + this.message
				+ "]";
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

	public String getMessageDivision() {
		return this.messageDivision;
	}

	public String getLinkCd() {
		return this.linkCd;
	}

	public String getParameters() {
		return this.parameters;
	}

	public String getTime() {
		return this.time;
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

	public void setMessageDivision(String messageDivision) {
		this.messageDivision = messageDivision;
	}

	public void setLinkCd(String linkCd) {
		this.linkCd = linkCd;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public static enum MessageReceiver {
		ALL, MANAGER, USER, PRIVATE;

		private MessageReceiver() {
		}
	}

	public static enum MessageDivision {
		NOTIFICATION, PUSH, CHAT, MAIL;

		private MessageDivision() {
		}
	}

}