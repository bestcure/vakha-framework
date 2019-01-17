package org.springframework.social.kakao.api;

import org.springframework.social.kakao.api.AbstractStoryPosting;

public class StoryNotePosting extends AbstractStoryPosting {
	private String content = "";

	public String getContent() {
		return this.content;
	}

	public boolean setContent(String content) {
		boolean returnValue = true;
		if (content.length() > 2048) {
			this.content = content.substring(0, 2048);
			returnValue = false;
		} else {
			this.content = content;
		}

		return returnValue;
	}
}