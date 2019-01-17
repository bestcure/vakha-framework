package org.springframework.social.kakao.api;

import java.util.ArrayList;
import java.util.List;

public class StoryPhotoUpload {
	private List<String> filePathList;

	public List<String> getFilePathList() {
		return this.filePathList != null && this.filePathList.size() != 0 ? this.filePathList : null;
	}

	public void setFilePathList(List<String> filePathList) {
		this.filePathList = filePathList;
	}

	public void setFilePathList(String... filePaths) {
		if (this.filePathList == null) {
			this.filePathList = new ArrayList();
		}

		String[] arg1 = filePaths;
		int arg2 = filePaths.length;

		for (int arg3 = 0; arg3 < arg2; ++arg3) {
			String filePath = arg1[arg3];
			this.filePathList.add(filePath);
		}

	}

	public void clearFilePathList() {
		this.filePathList = null;
	}
}