package org.springframework.social.kakao.api;

import java.io.Serializable;
import java.util.List;
import org.springframework.social.kakao.api.KakaoObject;
import org.springframework.social.kakao.api.MyStoryComment;
import org.springframework.social.kakao.api.MyStoryLike;
import org.springframework.social.kakao.api.MyStoryMedia;

public class MyStory extends KakaoObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String url;
	private String media_type;
	private String created_at;
	private int comment_count;
	private int like_count;
	private String content;
	private List<MyStoryMedia> media;
	private List<MyStoryComment> comments;
	private List<MyStoryLike> likes;

	public String getId() {
		return this.id;
	}

	public String getUrl() {
		return this.url;
	}

	public String getMedia_type() {
		return this.media_type;
	}

	public String getCreated_at() {
		return this.created_at;
	}

	public int getComment_count() {
		return this.comment_count;
	}

	public int getLike_count() {
		return this.like_count;
	}

	public String getContent() {
		return this.content;
	}

	public List<MyStoryMedia> getMedia() {
		return this.media;
	}

	public List<MyStoryComment> getComments() {
		return this.comments;
	}

	public List<MyStoryLike> getLikes() {
		return this.likes;
	}
}