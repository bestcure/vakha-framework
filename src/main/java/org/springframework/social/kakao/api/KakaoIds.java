package org.springframework.social.kakao.api;

import java.io.Serializable;
import java.util.List;
import org.springframework.social.kakao.api.KakaoObject;
import org.springframework.social.kakao.api.KakaoPages;

public class KakaoIds extends KakaoObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Long> result;
	private int totalCount;
	KakaoPages pages;

	public List<Long> getResult() {
		return this.result;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public KakaoPages getPages() {
		return this.pages;
	}
}