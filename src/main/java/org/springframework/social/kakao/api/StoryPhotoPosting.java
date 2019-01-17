package org.springframework.social.kakao.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.social.kakao.api.StoryNotePosting;
import org.springframework.social.kakao.api.StoryPhotoUpload;

public class StoryPhotoPosting extends StoryNotePosting {
	private StoryPhotoUpload storyPhotoUpload;
	private List<String> imageUrlList;

	public StoryPhotoUpload getStoryPhotoUpload() {
		return this.storyPhotoUpload != null && this.storyPhotoUpload.getFilePathList() == null
				? null
				: this.storyPhotoUpload;
	}

	public void setStoryPhotoUpload(StoryPhotoUpload storyPhotoUpload) {
		this.storyPhotoUpload = storyPhotoUpload;
	}

	public List<String> getImageUrlList() {
		return this.imageUrlList;
	}

	public void setImageUrlList(List<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}

	public void setImageUrlList(String... imageUrls) {
		if (this.imageUrlList == null) {
			this.imageUrlList = new ArrayList();
		}

		String[] arg1 = imageUrls;
		int arg2 = imageUrls.length;

		for (int arg3 = 0; arg3 < arg2; ++arg3) {
			String imageUrl = arg1[arg3];
			this.imageUrlList.add(imageUrl);
		}

	}

	public void clearImageUrlList() {
		this.imageUrlList = null;
	}

	public String imageUrlListToJson(boolean prettyPrint) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			return prettyPrint
					? objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.imageUrlList)
					: objectMapper.writeValueAsString(this.imageUrlList);
		} catch (JsonProcessingException arg3) {
			arg3.printStackTrace();
			return "";
		}
	}
}