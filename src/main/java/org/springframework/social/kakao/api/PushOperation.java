package org.springframework.social.kakao.api;

import java.util.List;
import org.springframework.social.kakao.api.ForApns;
import org.springframework.social.kakao.api.ForGcm;
import org.springframework.social.kakao.api.PushToken;

public interface PushOperation {
	String register(String arg0, String arg1, String arg2, String arg3);

	List<PushToken> tokens(String arg0);

	void deregister(String arg0, String arg1, String arg2);

	void send(List<String> arg0, ForApns arg1, ForGcm arg2);
}