package org.sessioncontainer.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionInfo {
	private final String sessionContainerId;
	private final Map<String,byte[]> attributeMap;
	SessionInfo(String sessionContainerId){
		this.sessionContainerId = sessionContainerId;
		this.attributeMap = new HashMap<String,byte[]>();
	}
	public List<String> getAttributeNames(){
		List<String> names = new ArrayList<String>();
		for(Map.Entry<String,byte[]> entry : attributeMap.entrySet()){
			names.add(entry.getKey());
		}
		return names;
	}
	public void rmvAttribute(String attributeKey){
		attributeMap.remove(attributeKey);
	}
	public void setAttribute(String attributeKey,byte[] attributeBytes){
		attributeMap.put(attributeKey, attributeBytes);
	}
	public byte[] getAttribute(String attributeKey){
		return attributeMap.get(attributeKey);
	}
}
