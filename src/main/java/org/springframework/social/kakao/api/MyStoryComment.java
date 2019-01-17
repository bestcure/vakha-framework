package org.springframework.social.kakao.api;

import org.springframework.social.kakao.api.MyStoryWriter;

public class MyStoryComment {
	private String text;
	private MyStoryWriter writer;

	public String getText() {
		return this.text;
	}

	public MyStoryWriter getWriter() {
		return this.writer;
	}
}