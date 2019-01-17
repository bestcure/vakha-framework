package vk.framework.spring.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BaseVo {
	private String cmd;
	private String sessionId;
	private String sessionUserId;
	private String sessionLoginId;
	private String sessionUserNm;
	private String sessionTypeCd;
	private String sessionAuthCd;
	private String sessionSgrCd;
	private String sessionSgrSessionCd;
	private String sessionCompCd;
	private String sessionAuthDivision;
	private String sessionPersonKey;
	private String sessionMaskingYn;
	private String regId;
	private String regIp;
	private String updId;
	private String updIp;
	private String dateTimeFunction = "NOW()";
	private String dateFunction = "NOW()";

	public final String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getCmd() {
		return this.cmd;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public String getSessionUserId() {
		return this.sessionUserId;
	}

	public String getSessionLoginId() {
		return this.sessionLoginId;
	}

	public String getSessionUserNm() {
		return this.sessionUserNm;
	}

	public String getSessionTypeCd() {
		return this.sessionTypeCd;
	}

	public String getSessionAuthCd() {
		return this.sessionAuthCd;
	}

	public String getSessionSgrCd() {
		return this.sessionSgrCd;
	}

	public String getSessionSgrSessionCd() {
		return this.sessionSgrSessionCd;
	}

	public String getSessionCompCd() {
		return this.sessionCompCd;
	}

	public String getSessionAuthDivision() {
		return this.sessionAuthDivision;
	}

	public String getSessionPersonKey() {
		return this.sessionPersonKey;
	}

	public String getSessionMaskingYn() {
		return this.sessionMaskingYn;
	}

	public String getRegId() {
		return this.regId;
	}

	public String getRegIp() {
		return this.regIp;
	}

	public String getUpdId() {
		return this.updId;
	}

	public String getUpdIp() {
		return this.updIp;
	}

	public String getDateTimeFunction() {
		return this.dateTimeFunction;
	}

	public String getDateFunction() {
		return this.dateFunction;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setSessionUserId(String sessionUserId) {
		this.sessionUserId = sessionUserId;
	}

	public void setSessionLoginId(String sessionLoginId) {
		this.sessionLoginId = sessionLoginId;
	}

	public void setSessionUserNm(String sessionUserNm) {
		this.sessionUserNm = sessionUserNm;
	}

	public void setSessionTypeCd(String sessionTypeCd) {
		this.sessionTypeCd = sessionTypeCd;
	}

	public void setSessionAuthCd(String sessionAuthCd) {
		this.sessionAuthCd = sessionAuthCd;
	}

	public void setSessionSgrCd(String sessionSgrCd) {
		this.sessionSgrCd = sessionSgrCd;
	}

	public void setSessionSgrSessionCd(String sessionSgrSessionCd) {
		this.sessionSgrSessionCd = sessionSgrSessionCd;
	}

	public void setSessionCompCd(String sessionCompCd) {
		this.sessionCompCd = sessionCompCd;
	}

	public void setSessionAuthDivision(String sessionAuthDivision) {
		this.sessionAuthDivision = sessionAuthDivision;
	}

	public void setSessionPersonKey(String sessionPersonKey) {
		this.sessionPersonKey = sessionPersonKey;
	}

	public void setSessionMaskingYn(String sessionMaskingYn) {
		this.sessionMaskingYn = sessionMaskingYn;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public void setUpdId(String updId) {
		this.updId = updId;
	}

	public void setUpdIp(String updIp) {
		this.updIp = updIp;
	}

	public void setDateTimeFunction(String dateTimeFunction) {
		this.dateTimeFunction = dateTimeFunction;
	}

	public void setDateFunction(String dateFunction) {
		this.dateFunction = dateFunction;
	}
}