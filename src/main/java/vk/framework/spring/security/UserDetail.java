package vk.framework.spring.security;

import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetail implements UserDetails {
	private static final long serialVersionUID = 1L;
	private String loginId;
	private String loginPwd;
	private String userId;
	private String userNm;
	private Date birth;
	private String typeCd;
	private String authCd;
	private Integer authLevel;
	private String token;
	private String deviceKey;
	private String sgrCd;
	private String sgrYy;
	private String sgrSessionCd;
	private String mobileno;
	private String compCd;
	private String compNm;
	private String deptCd;
	private String positionCd;
	private String deptNm;
	private String positionNm;
	private String subscribeYn;
	private String firebaseToken;
	private String ipinKey;
	private String idMobileno;
	private String authDivision;
	private String personKey;
	private String maskingYn;
	private String sessionId;
	private String ip;
	private Date loginDt;
	private String statusCode;
	private String statusName;
	private Collection<? extends GrantedAuthority> authorities;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private boolean enabled = true;

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public String getPassword() {
		return this.loginPwd;
	}

	public String getUsername() {
		return this.loginId;
	}

	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public String getLoginId() {
		return this.loginId;
	}

	public String getLoginPwd() {
		return this.loginPwd;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getUserNm() {
		return this.userNm;
	}

	public Date getBirth() {
		return this.birth;
	}

	public String getTypeCd() {
		return this.typeCd;
	}

	public String getAuthCd() {
		return this.authCd;
	}

	public Integer getAuthLevel() {
		return this.authLevel;
	}

	public String getToken() {
		return this.token;
	}

	public String getDeviceKey() {
		return this.deviceKey;
	}

	public String getSgrCd() {
		return this.sgrCd;
	}

	public String getSgrYy() {
		return this.sgrYy;
	}

	public String getSgrSessionCd() {
		return this.sgrSessionCd;
	}

	public String getMobileno() {
		return this.mobileno;
	}

	public String getCompCd() {
		return this.compCd;
	}

	public String getCompNm() {
		return this.compNm;
	}

	public String getDeptCd() {
		return this.deptCd;
	}

	public String getPositionCd() {
		return this.positionCd;
	}

	public String getDeptNm() {
		return this.deptNm;
	}

	public String getPositionNm() {
		return this.positionNm;
	}

	public String getSubscribeYn() {
		return this.subscribeYn;
	}

	public String getFirebaseToken() {
		return this.firebaseToken;
	}

	public String getIpinKey() {
		return this.ipinKey;
	}

	public String getIdMobileno() {
		return this.idMobileno;
	}

	public String getAuthDivision() {
		return this.authDivision;
	}

	public String getPersonKey() {
		return this.personKey;
	}

	public String getMaskingYn() {
		return this.maskingYn;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public String getIp() {
		return this.ip;
	}

	public Date getLoginDt() {
		return this.loginDt;
	}

	public String getStatusCode() {
		return this.statusCode;
	}

	public String getStatusName() {
		return this.statusName;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

	public void setAuthCd(String authCd) {
		this.authCd = authCd;
	}

	public void setAuthLevel(Integer authLevel) {
		this.authLevel = authLevel;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}

	public void setSgrCd(String sgrCd) {
		this.sgrCd = sgrCd;
	}

	public void setSgrYy(String sgrYy) {
		this.sgrYy = sgrYy;
	}

	public void setSgrSessionCd(String sgrSessionCd) {
		this.sgrSessionCd = sgrSessionCd;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public void setCompCd(String compCd) {
		this.compCd = compCd;
	}

	public void setCompNm(String compNm) {
		this.compNm = compNm;
	}

	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	public void setPositionCd(String positionCd) {
		this.positionCd = positionCd;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public void setPositionNm(String positionNm) {
		this.positionNm = positionNm;
	}

	public void setSubscribeYn(String subscribeYn) {
		this.subscribeYn = subscribeYn;
	}

	public void setFirebaseToken(String firebaseToken) {
		this.firebaseToken = firebaseToken;
	}

	public void setIpinKey(String ipinKey) {
		this.ipinKey = ipinKey;
	}

	public void setIdMobileno(String idMobileno) {
		this.idMobileno = idMobileno;
	}

	public void setAuthDivision(String authDivision) {
		this.authDivision = authDivision;
	}

	public void setPersonKey(String personKey) {
		this.personKey = personKey;
	}

	public void setMaskingYn(String maskingYn) {
		this.maskingYn = maskingYn;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setLoginDt(Date loginDt) {
		this.loginDt = loginDt;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String toString() {
		return "UserDetail(loginId=" + this.getLoginId() + ", loginPwd=" + this.getLoginPwd() + ", userId="
				+ this.getUserId() + ", userNm=" + this.getUserNm() + ", birth=" + this.getBirth() + ", typeCd="
				+ this.getTypeCd() + ", authCd=" + this.getAuthCd() + ", authLevel=" + this.getAuthLevel() + ", token="
				+ this.getToken() + ", deviceKey=" + this.getDeviceKey() + ", sgrCd=" + this.getSgrCd() + ", sgrYy="
				+ this.getSgrYy() + ", sgrSessionCd=" + this.getSgrSessionCd() + ", mobileno=" + this.getMobileno()
				+ ", compCd=" + this.getCompCd() + ", compNm=" + this.getCompNm() + ", deptCd=" + this.getDeptCd()
				+ ", positionCd=" + this.getPositionCd() + ", deptNm=" + this.getDeptNm() + ", positionNm="
				+ this.getPositionNm() + ", subscribeYn=" + this.getSubscribeYn() + ", firebaseToken="
				+ this.getFirebaseToken() + ", ipinKey=" + this.getIpinKey() + ", idMobileno=" + this.getIdMobileno()
				+ ", authDivision=" + this.getAuthDivision() + ", personKey=" + this.getPersonKey() + ", maskingYn="
				+ this.getMaskingYn() + ", sessionId=" + this.getSessionId() + ", ip=" + this.getIp() + ", loginDt="
				+ this.getLoginDt() + ", statusCode=" + this.getStatusCode() + ", statusName=" + this.getStatusName()
				+ ", authorities=" + this.getAuthorities() + ", accountNonExpired=" + this.isAccountNonExpired()
				+ ", accountNonLocked=" + this.isAccountNonLocked() + ", credentialsNonExpired="
				+ this.isCredentialsNonExpired() + ", enabled=" + this.isEnabled() + ")";
	}
}