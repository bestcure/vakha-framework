package org.springframework.social.kakao.api;

import org.springframework.social.ApiBinding;
import org.springframework.social.kakao.api.PushOperation;
import org.springframework.social.kakao.api.StoryOperation;
import org.springframework.social.kakao.api.TalkOperation;
import org.springframework.social.kakao.api.UserOperation;

public interface Kakao extends ApiBinding {
	UserOperation userOperation();

	StoryOperation storyOperation();

	TalkOperation talkOperation();

	PushOperation pushOperation();
}