package org.springframework.social.kakao.api;

import java.util.List;
import org.springframework.social.kakao.api.KakaoStoryProfile;
import org.springframework.social.kakao.api.MyStory;
import org.springframework.social.kakao.api.StoryLinkInfo;
import org.springframework.social.kakao.api.StoryLinkPosting;
import org.springframework.social.kakao.api.StoryNotePosting;
import org.springframework.social.kakao.api.StoryPhotoPosting;
import org.springframework.social.kakao.api.StoryPhotoUpload;
import org.springframework.social.kakao.api.StoryPostingResult;

public interface StoryOperation {
	KakaoStoryProfile isStoryUser();

	KakaoStoryProfile getUserProfile();

	StoryPostingResult postNote(StoryNotePosting arg0);

	List<String> uploadPhoto(StoryPhotoUpload arg0);

	StoryPostingResult postPhoto(StoryPhotoPosting arg0);

	StoryLinkInfo linkInfo(String arg0);

	StoryPostingResult postLink(StoryLinkPosting arg0);

	MyStory myStory(String arg0);

	List<MyStory> myStories(String arg0);

	void deleteMyStory(String arg0);
}