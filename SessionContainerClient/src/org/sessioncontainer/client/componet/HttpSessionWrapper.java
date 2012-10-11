package org.sessioncontainer.client.componet;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.sessioncontainer.client.service.SessionService;

public class HttpSessionWrapper implements HttpSession{
	private final String sessionContainerId;
	private final HttpSession session;
	private final SessionService sessionService = SessionService.getInstance();
	
	public HttpSessionWrapper(String sessionContainerId,HttpSession session){
		this.sessionContainerId = sessionContainerId;
		this.session = session;
	}
	@Override
	public Object getAttribute(String arg0) {
		return sessionService.getAttribute(sessionContainerId, arg0);
	}
	@Override
	public Enumeration getAttributeNames() {
		List<String> attributeNameList = sessionService.getAttributeNames(sessionContainerId);
		Vector<String> v = new Vector<String>(attributeNameList);
		return v.elements();
	}
	@Override
	public long getCreationTime() {
		return session.getCreationTime();
	}
	@Override
	public String getId() {
		return this.session.getId();
	}
	@Override
	public long getLastAccessedTime() {
		return session.getLastAccessedTime();
	}
	@Override
	public int getMaxInactiveInterval() {
		return session.getMaxInactiveInterval();
	}
	@Override
	public ServletContext getServletContext() {
		return session.getServletContext();
	}
	@Override
	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return session.getSessionContext();
	}
	@Override
	public Object getValue(String arg0) {
		return sessionService.getAttribute(sessionContainerId, arg0);
	}
	@Override
	public String[] getValueNames() {
		List<String> attributeNameList = sessionService.getAttributeNames(sessionContainerId);
		String[] valueNames = new String[attributeNameList.size()];
		return attributeNameList.toArray(valueNames);
	}
	@Override
	public void invalidate() {
		session.invalidate();
	}
	@Override
	public boolean isNew() {
		return session.isNew();
	}
	@Override
	public void putValue(String attributeKey, Object obj) {
		sessionService.setAttribute(sessionContainerId, attributeKey, obj);
	}
	@Override
	public void removeAttribute(String attributeKey) {
		sessionService.rmvAttribute(sessionContainerId, attributeKey);
	}
	@Override
	public void removeValue(String attributeKey) {
		sessionService.rmvAttribute(sessionContainerId, attributeKey);
	}
	@Override
	public void setAttribute(String attributeKey, Object obj) {
		sessionService.setAttribute(sessionContainerId, attributeKey, obj);
	}
	@Override
	public void setMaxInactiveInterval(int arg0) {
		session.setMaxInactiveInterval(arg0);
	}
}
