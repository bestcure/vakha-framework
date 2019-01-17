package vk.framework.spring.web.session;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionTracker {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static SessionTracker ourInstance = new SessionTracker();
	private final WeakHashMap<String, HttpSession> sessions = new WeakHashMap();
	private int sessionCount;

	public static SessionTracker instance() {
		return ourInstance;
	}

	public List<HttpSession> getSessions() {
		return new ArrayList(this.sessions.values());
	}

	public HttpSession getSession(String id) {
		return (HttpSession) this.sessions.get(id);
	}

	public int getSessionCount() {
		return this.sessionCount;
	}

	public void addSession(HttpSession session) {
		++this.sessionCount;
		this.sessions.put(session.getId(), session);
		if (this.log.isDebugEnabled()) {
			this.log.debug(session.toString());
		}

	}

	public void removeSession(HttpSession session) {
		--this.sessionCount;
		this.sessions.remove(session.getId());
	}
}