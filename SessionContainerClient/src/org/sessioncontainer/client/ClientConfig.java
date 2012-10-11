package org.sessioncontainer.client;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ClientConfig {
	
	/*private String sessionKey = "SESSIONHOLDERKEY";
	private String authorizedKey = "SIMPLEAUTHORIZEDKEY";
	private int    authorizedLength = 16;
	private Set<String> logoutSet = new HashSet<String>();*/
	private long connectionTimeout = 1000L;
	private Set<String> serverNodes = new HashSet<String>();
	
	private boolean loadSuccess = false;
	public ClientConfig(){}
	
	public void load() throws DocumentException{
		if(loadSuccess) return;
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("SessionContainerConfig.xml");
		Document doc = new SAXReader().read(is);
		
		/*Element sessionKeyNode = (Element)doc.selectSingleNode("/Client/filter/sessionKey");
		if(sessionKeyNode!=null&&!Utils.StringIsEmpty(sessionKeyNode.getTextTrim())) 
			this.sessionKey = sessionKeyNode.getTextTrim();
		
		Element authorizedKeyNode = (Element)doc.selectSingleNode("/Client/filter/authorizedKey");
		if(authorizedKeyNode!=null&&!Utils.StringIsEmpty(authorizedKeyNode.getTextTrim())) 
			this.authorizedKey = authorizedKeyNode.getTextTrim();
		
		List<Element> logouts = (List<Element>)doc.selectNodes("/Client/filter/logouts/logout");
		for(Element logout : logouts){this.logoutSet.add(logout.getTextTrim());}*/
		
		Element ConnectionTimeoutNode = (Element)doc.selectSingleNode("/Client/Service/ConnectionTimeout");
		if(ConnectionTimeoutNode!=null&&!Utils.StringIsEmpty(ConnectionTimeoutNode.getTextTrim()))
			this.connectionTimeout = Long.parseLong(ConnectionTimeoutNode.getTextTrim());
		
		List<Element> ServerNodes = (List<Element>)doc.selectNodes("/Client/Service/ServerNodes/ServerNode");
		for(Element ServerNode : ServerNodes){this.serverNodes.add(ServerNode.getTextTrim());}
		
		//this.logoutSet = Collections.unmodifiableSet(this.logoutSet);
		this.serverNodes = Collections.unmodifiableSet(this.serverNodes);
	}

	/*public String getSessionKey() {
		return sessionKey;
	}

	public String getAuthorizedKey() {
		return authorizedKey;
	}

	public int getAuthorizedLength() {
		return authorizedLength;
	}

	public Set<String> getLogoutSet() {
		return logoutSet;
	}*/

	public long getConnectionTimeout() {
		return connectionTimeout;
	}

	public Set<String> getServerNodes() {
		return serverNodes;
	}
	
}
