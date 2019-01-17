package org.springframework.social.kakao.api;

import org.springframework.social.kakao.api.KakaoObject;

public class AbstractStoryPosting extends KakaoObject {
	private String permission = "A";
	private String enableShare = "true";
	private String androidExecParam = "";
	private String iosExecParam = "";
	private String androidMarketParam = "";
	private String iosMarketParam = "";

	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
		if (this.permission.equals("A")) {
			this.enableShare = "true";
		}

	}

	public String isEnableShare() {
		return this.enableShare;
	}

	public void setEnableShare(boolean enableShare) {
		if (this.permission.equals("A")) {
			enableShare = true;
		}

		if (enableShare) {
			this.enableShare = "true";
		} else {
			this.enableShare = "false";
		}

	}

	public String getAndroidExecParam() {
		return this.androidExecParam;
	}

	public void setAndroidExecParam(String androidExecParam) {
		this.androidExecParam = androidExecParam;
	}

	public String getIosExecParam() {
		return this.iosExecParam;
	}

	public void setIosExecParam(String iosExecParam) {
		this.iosExecParam = iosExecParam;
	}

	public String getAndroidMarketParam() {
		return this.androidMarketParam;
	}

	public void setAndroidMarketParam(String androidMarketParam) {
		this.androidMarketParam = androidMarketParam;
	}

	public String getIosMarketParam() {
		return this.iosMarketParam;
	}

	public void setIosMarketParam(String iosMarketParam) {
		this.iosMarketParam = iosMarketParam;
	}
}