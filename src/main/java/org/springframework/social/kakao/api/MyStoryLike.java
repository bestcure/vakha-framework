package org.springframework.social.kakao.api;

import org.springframework.social.kakao.api.MyStoryWriter;

public class MyStoryLike {
	private String emotion;
	private MyStoryWriter actor;

	public String getEmotion() {
		return this.emotion;
	}

	public MyStoryWriter getActor() {
		return this.actor;
	}
}