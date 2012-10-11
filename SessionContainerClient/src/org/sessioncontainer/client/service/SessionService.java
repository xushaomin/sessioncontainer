package org.sessioncontainer.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.sessioncontainer.client.SessionContainerException;
import org.sessioncontainer.client.SessionContainerRuntimeException;
import org.sessioncontainer.client.command.Command;
import org.sessioncontainer.client.command.CommandHolder;
public class SessionService {
	private final static Logger logger=Logger.getLogger(SessionService.class);
	
	private static SessionService instance = new SessionService();
	public static SessionService getInstance(){return instance;}
	
	private final Map<String,String> sessionIdMap;
	private ServiceContext context;
	private SessionServiceNode[] nodes = null;
	private int nodeSize;
	private SessionService(){
		context = new ServiceContext();
		NodeAddress[] nodeAddArray = context.getNodeAddressArray();
		sessionIdMap = new ConcurrentHashMap<String, String>();
		this.nodeSize = nodeAddArray.length;
		this.nodes = new SessionServiceNode[nodeSize];
		
		int index=0;
		try {
			for(;index<this.nodeSize;index++){
				nodes[index] = new SessionServiceNode(context,nodeAddArray[index]);
			}
		} catch (SessionContainerException e) {
			//如果再创建某个结点连接时，发生错误！，则报错，并关闭原有连接
			for(int i=0;i<index;i++){
				nodes[index].shutdown();
			}
			e.printStackTrace();
		}
	}
	public void regeditSessionId(String sessionId,String sessionContainerId){
		sessionIdMap.put(sessionId, sessionContainerId);
	}
	public void rmvSessionId(String sessionId){
		sessionIdMap.remove(sessionId);
	}
	public String getSessionContainerId(String sessionId){
		return sessionIdMap.get(sessionId);
	}
	
	
	
	public Object getAttribute(String sessionContainerId, String attributeKey){
		SessionServiceNode serviceNode = getNodeConnector(sessionContainerId);
		if(serviceNode==null) throw new SessionContainerRuntimeException("未知错误：SessionServiceNode为空!");
		try {
			return serviceNode.getAttribute(sessionContainerId, attributeKey);
		} catch (SessionContainerException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final List<String> emptyStrList = new ArrayList<String>();
	public List<String> getAttributeNames(String sessionContainerId){
		SessionServiceNode serviceNode = getNodeConnector(sessionContainerId);
		if(serviceNode==null) throw new SessionContainerRuntimeException("未知错误：SessionServiceNode为空!");
		try {
			List<String> nameList = serviceNode.getAttributeNames(sessionContainerId);
			return nameList==null?emptyStrList:nameList;
		} catch (SessionContainerException | InterruptedException e) {
			e.printStackTrace();
			return emptyStrList;
		}
	}
	
	public void setAttribute(String sessionContainerId,String attributeKey,Object obj){
		SessionServiceNode serviceNode = getNodeConnector(sessionContainerId);
		if(serviceNode==null) throw new SessionContainerRuntimeException("未知错误：SessionServiceNode为空!");
		try {
			serviceNode.setAttribute(sessionContainerId, attributeKey,obj);
		} catch (SessionContainerException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void rmvAttribute(String sessionContainerId,String attributeKey) {
		SessionServiceNode serviceNode = getNodeConnector(sessionContainerId);
		if(serviceNode==null) throw new SessionContainerRuntimeException("未知错误：SessionServiceNode为空!");
		try {
			serviceNode.rmvAttribute(sessionContainerId, attributeKey);
		} catch (SessionContainerException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void delSession(String sessionContainerId){
		SessionServiceNode serviceNode = getNodeConnector(sessionContainerId);
		if(serviceNode==null) throw new SessionContainerRuntimeException("未知错误：SessionServiceNode为空!");
		try {
			serviceNode.delSession(sessionContainerId);
		} catch (SessionContainerException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	private SessionServiceNode getNodeConnector(String sessionContainerId) {
		if (sessionContainerId == null) throw new SessionContainerRuntimeException("sessionContainerId为空！");
		int index = Math.abs(sessionContainerId.hashCode()%nodeSize);
		return nodes[index];
	}

}
