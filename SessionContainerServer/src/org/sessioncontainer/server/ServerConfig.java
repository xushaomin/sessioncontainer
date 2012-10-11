package org.sessioncontainer.server;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ServerConfig {
	private long connectionTimeout = 1000L;
	private int port = 9090;
	private int commandQueueCapcity = 100;
	
	private boolean loadSuccess = false;
	public ServerConfig(){}
	
	public void load() throws DocumentException{
		if(loadSuccess) return;
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("SessionContainerConfig.xml");
		Document doc = new SAXReader().read(is);
		
		
		Element port = (Element)doc.selectSingleNode("/Server/port");
		if(port!=null&&!Utils.StringIsEmpty(port.getTextTrim())) this.port = Integer.parseInt(port.getTextTrim());
		
		Element connectionTimeout = (Element)doc.selectSingleNode("/Server/connectionTimeout");
		if(connectionTimeout!=null&&!Utils.StringIsEmpty(connectionTimeout.getTextTrim())) this.connectionTimeout = Long.parseLong(connectionTimeout.getTextTrim());
		
		Element commandQueueCapcity = (Element)doc.selectSingleNode("/Server/commandQueueCapcity");
		if(commandQueueCapcity!=null&&!Utils.StringIsEmpty(commandQueueCapcity.getTextTrim())) this.commandQueueCapcity = Integer.parseInt(commandQueueCapcity.getTextTrim());
		
	}

	public long getConnectionTimeout() {
		return connectionTimeout;
	}

	public int getPort() {
		return port;
	}

	public boolean isLoadSuccess() {
		return loadSuccess;
	}

	public int getCommandQueueCapcity() {
		return commandQueueCapcity;
	}

	
}
