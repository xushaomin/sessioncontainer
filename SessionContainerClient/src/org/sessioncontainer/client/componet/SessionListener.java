package org.sessioncontainer.client.componet;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.sessioncontainer.client.ClientConfig;
import org.sessioncontainer.client.Utils;
import org.sessioncontainer.client.service.SessionService;

/**
 * 在本地session创建时注册到服务，在session销毁时从服务中注销
 *
 */
public class SessionListener implements HttpSessionListener {
	
	private final SessionService sessionService = SessionService.getInstance();
    /**
     * Default constructor. 
     */
    public SessionListener() {
    }
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		String sessionContainerId = Utils.getUUIDInUpperCase();
		String sessionId = event.getSession().getId();
		sessionService.regeditSessionId(sessionId, sessionContainerId);
	}
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		String sessionId = event.getSession().getId();
		String sessionContainerId = sessionService.getSessionContainerId(sessionId);
		sessionService.delSession(sessionContainerId);//从存储服务器上移除数据
		sessionService.rmvSessionId(sessionId);
	}

	
}
