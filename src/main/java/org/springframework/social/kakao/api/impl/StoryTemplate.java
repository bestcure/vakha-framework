package org.springframework.social.kakao.api.impl;

import java.util.Iterator;
import java.util.List;
import org.springframework.core.io.FileSystemResource;
import org.springframework.social.kakao.api.AbstractStoryPosting;
import org.springframework.social.kakao.api.KakaoStoryProfile;
import org.springframework.social.kakao.api.MyStory;
import org.springframework.social.kakao.api.StoryLinkInfo;
import org.springframework.social.kakao.api.StoryLinkPosting;
import org.springframework.social.kakao.api.StoryNotePosting;
import org.springframework.social.kakao.api.StoryOperation;
import org.springframework.social.kakao.api.StoryPhotoPosting;
import org.springframework.social.kakao.api.StoryPhotoUpload;
import org.springframework.social.kakao.api.StoryPostingResult;
import org.springframework.social.kakao.api.impl.AbstractKakaoOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public class StoryTemplate extends AbstractKakaoOperations implements StoryOperation {
	private final RestTemplate restTemplate;

	public StoryTemplate(RestTemplate restTemplate, boolean isAuthorized) {
		super(isAuthorized);
		this.restTemplate = restTemplate;
	}

	public KakaoStoryProfile isStoryUser() {
		this.requireAuthorization();
		return (KakaoStoryProfile) this.restTemplate.getForObject(this.buildApiUri("/v1/api/story/isstoryuser"),
				KakaoStoryProfile.class);
	}

	public KakaoStoryProfile getUserProfile() {
		this.requireAuthorization();
		return (KakaoStoryProfile) this.restTemplate.getForObject(this.buildApiUri("/v1/api/story/profile"),
				KakaoStoryProfile.class);
	}

	public StoryPostingResult postNote(StoryNotePosting storyNotePosting) {
		this.requireAuthorization();
		MultiValueMap param = this.postingCommonParamSetting(storyNotePosting);
		param.set("content", storyNotePosting.getContent());
		return (StoryPostingResult) this.restTemplate.postForObject(this.buildApiUri("/v1/api/story/post/note"), param,
				StoryPostingResult.class);
	}

	public List<String> uploadPhoto(StoryPhotoUpload storyPhotoUpload) {
		List filePathList = storyPhotoUpload.getFilePathList();
		LinkedMultiValueMap param = new LinkedMultiValueMap();
		Iterator arg3 = filePathList.iterator();

		while (arg3.hasNext()) {
			String filePath = (String) arg3.next();
			param.add("file", new FileSystemResource(filePath));
		}

		return (List) this.restTemplate.postForObject(this.buildApiUri("/v1/api/story/upload/multi"), param,
				List.class);
	}

	public StoryPostingResult postPhoto(StoryPhotoPosting storyPhotoPosting) {
		this.requireAuthorization();
		if (storyPhotoPosting.getStoryPhotoUpload() != null) {
			List param = this.uploadPhoto(storyPhotoPosting.getStoryPhotoUpload());
			storyPhotoPosting.setImageUrlList(param);
		}

		MultiValueMap param1 = this.postingCommonParamSetting(storyPhotoPosting);
		param1.set("image_url_list", storyPhotoPosting.imageUrlListToJson(false));
		param1.set("content", storyPhotoPosting.getContent());
		return (StoryPostingResult) this.restTemplate.postForObject(this.buildApiUri("/v1/api/story/post/photo"),
				param1, StoryPostingResult.class);
	}

	public StoryLinkInfo linkInfo(String url) {
		this.requireAuthorization();
		LinkedMultiValueMap param = new LinkedMultiValueMap();
		param.set("url", url);
		return (StoryLinkInfo) this.restTemplate.getForObject(this.buildApiUri("/v1/api/story/linkinfo", param),
				StoryLinkInfo.class);
	}

	public StoryPostingResult postLink(StoryLinkPosting storyLinkPosting) {
		this.requireAuthorization();
		if (!StringUtils.isEmpty(storyLinkPosting.getUrl())) {
			storyLinkPosting.setStoryLinkInfo(this.linkInfo(storyLinkPosting.getUrl()));
		}

		MultiValueMap param = this.postingCommonParamSetting(storyLinkPosting);
		param.set("link_info", storyLinkPosting.getStoryLinkInfo().toJsonString(false));
		param.set("content", storyLinkPosting.getContent());
		return (StoryPostingResult) this.restTemplate.postForObject(this.buildApiUri("/v1/api/story/post/link"), param,
				StoryPostingResult.class);
	}

	private MultiValueMap<String, Object> postingCommonParamSetting(AbstractStoryPosting abstractStoryPosting) {
		LinkedMultiValueMap param = new LinkedMultiValueMap();
		param.set("permission", abstractStoryPosting.getPermission());
		param.set("enable_share", abstractStoryPosting.isEnableShare());
		if (!StringUtils.isEmpty(abstractStoryPosting.getAndroidExecParam())) {
			param.set("android_exec_param", abstractStoryPosting.getAndroidExecParam());
		}

		if (!StringUtils.isEmpty(abstractStoryPosting.getIosExecParam())) {
			param.set("ios_exec_param", abstractStoryPosting.getIosExecParam());
		}

		if (!StringUtils.isEmpty(abstractStoryPosting.getAndroidMarketParam())) {
			param.set("android_market_param", abstractStoryPosting.getAndroidMarketParam());
		}

		if (!StringUtils.isEmpty(abstractStoryPosting.getIosMarketParam())) {
			param.set("ios_market_param", abstractStoryPosting.getIosMarketParam());
		}

		return param;
	}

	public MyStory myStory(String id) {
		LinkedMultiValueMap param = new LinkedMultiValueMap();
		param.set("id", id);
		return (MyStory) this.restTemplate.getForObject(this.buildApiUri("/v1/api/story/mystory", param),
				MyStory.class);
	}

	public List<MyStory> myStories(String lastId) {
		LinkedMultiValueMap param = new LinkedMultiValueMap();
		if (lastId != null) {
			param.set("last_id", lastId);
		}

		return (List) this.restTemplate.getForObject(this.buildApiUri("/v1/api/story/mystories", param), List.class);
	}

	public void deleteMyStory(String id) {
		LinkedMultiValueMap param = new LinkedMultiValueMap();
		param.set("id", id);
		this.restTemplate.delete(this.buildApiUri("/v1/api/story/delete/mystory", param));
	}
}