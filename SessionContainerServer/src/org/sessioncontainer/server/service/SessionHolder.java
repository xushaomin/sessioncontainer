package org.sessioncontainer.server.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionHolder {
	private final Map<String,SessionInfo> sessionMap;
	public SessionHolder() {
		sessionMap = new ConcurrentHashMap<String, SessionInfo>();
	}
	/*public SessionInfo newSessionInfo(String sessionContainerId){
		SessionInfo sessionInfo = new SessionInfo(sessionContainerId);
		sessionMap.put(sessionContainerId, sessionInfo);
		return sessionInfo;
	}*/
	public SessionInfo getSessionInfo(String sessionContainerId){
		checkSession(sessionContainerId);
		return sessionMap.get(sessionContainerId);
	}
	private void checkSession(String sessionContainerId){
		if(sessionMap.get(sessionContainerId)==null){
			synchronized(this){
				if(sessionMap.get(sessionContainerId)==null){
					sessionMap.put(sessionContainerId, new SessionInfo(sessionContainerId));
				}
			}
		}
	}
	
	public void rmvSessionInfo(String sessionContainerKey){
		sessionMap.remove(sessionContainerKey);
	}
}
