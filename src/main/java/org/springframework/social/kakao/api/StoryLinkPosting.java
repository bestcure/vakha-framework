package org.springframework.social.kakao.api;

import org.springframework.social.kakao.api.StoryLinkInfo;
import org.springframework.social.kakao.api.StoryNotePosting;

public class StoryLinkPosting extends StoryNotePosting {
	private StoryLinkInfo storyLinkInfo;
	private String url;

	public StoryLinkInfo getStoryLinkInfo() {
		return this.storyLinkInfo;
	}

	public void setStoryLinkInfo(StoryLinkInfo storyLinkInfo) {
		this.storyLinkInfo = storyLinkInfo;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}