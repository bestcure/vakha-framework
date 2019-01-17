package org.springframework.social.kakao.api;

import java.io.Serializable;
import java.util.List;
import org.springframework.social.kakao.api.KakaoObject;

public class StoryLinkInfo extends KakaoObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String url;
	private String requested_url;
	private String host;
	private String title;
	private List<String> image;
	private String description;
	private String section;
	private String site_name;

	public String getUrl() {
		return this.url;
	}

	public String getRequested_url() {
		return this.requested_url;
	}

	public String getHost() {
		return this.host;
	}

	public String getTitle() {
		return this.title;
	}

	public List<String> getImage() {
		return this.image;
	}

	public String getDescription() {
		return this.description;
	}

	public String getSection() {
		return this.section;
	}

	public String getSite_name() {
		return this.site_name;
	}
}