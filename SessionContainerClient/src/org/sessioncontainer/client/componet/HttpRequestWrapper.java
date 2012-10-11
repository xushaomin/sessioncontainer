package org.sessioncontainer.client.componet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;


public class HttpRequestWrapper extends HttpServletRequestWrapper {
	private final String sessionContainerId;
	public HttpRequestWrapper(String sessionContainerId,HttpServletRequest request) {
		super(request);
		this.sessionContainerId = sessionContainerId;
	}
	public HttpSession getSession(boolean create) {
		return new HttpSessionWrapper(this.sessionContainerId, super.getSession(create));
	}
	public HttpSession getSession() {
		return new HttpSessionWrapper(this.sessionContainerId, super.getSession());
	}
}
