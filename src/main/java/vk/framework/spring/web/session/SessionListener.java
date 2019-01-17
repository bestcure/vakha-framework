package vk.framework.spring.web.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionListener implements HttpSessionListener {
	private static final Logger log = LoggerFactory.getLogger(SessionListener.class);
	private Integer sessionTimeout = Integer.valueOf(60);

	public SessionListener(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public void sessionCreated(HttpSessionEvent event) {
		log.debug("SessionListener sessionCreated : " + event.getSession().getId());
		log.debug("SessionListener sessionCreated : " + event.getSession().getMaxInactiveInterval());
		event.getSession().setMaxInactiveInterval(60 * this.sessionTimeout.intValue());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		log.debug("SessionListener sessionDestroyed : " + event.getSession().getId());
	}
}