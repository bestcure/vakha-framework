package org.springframework.social.kakao.api;

import java.io.Serializable;
import org.springframework.social.kakao.api.KakaoObject;

public class StoryPostingResult extends KakaoObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id = "";

	public String getId() {
		return this.id;
	}
}