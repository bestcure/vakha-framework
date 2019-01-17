package org.springframework.social.kakao.api;

import java.io.Serializable;
import org.springframework.social.kakao.api.KakaoObject;

public class KakaoPages extends KakaoObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String beforeURL;
	private String afterURL;

	public String getBeforeURL() {
		return this.beforeURL;
	}

	public String getAfterURL() {
		return this.afterURL;
	}
}