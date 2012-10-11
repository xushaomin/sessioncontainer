package org.sessioncontainer.client.command;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.sessioncontainer.client.SessionContainerRuntimeException;
import org.sessioncontainer.client.Utils;


public class CommandHolder {
	private static final AtomicInteger commandIdSeq = new AtomicInteger(0);
	private static int getNextCommandIdInInt(){
		return commandIdSeq.incrementAndGet();
	}
	private static String getNextCommandIdInStr(){
		return String.valueOf(getNextCommandIdInInt());
	}
	
	
	private final Map<String, Command> commandMap;
	public CommandHolder() {
		commandMap = new ConcurrentHashMap<String,Command>();
	}
	private String getHolderKey(String sessionContainerId,String commandId){
		return sessionContainerId+"---"+commandId;
	}
	public void rmvCommand(String sessionContainerId,String commandId){
		commandMap.remove(getHolderKey(sessionContainerId,commandId));
	}
	
	public Command getCommand(String sessionContainerId,String commandId){
		return commandMap.get(getHolderKey(sessionContainerId,commandId));
	}
	public Command newCommandSetSessionAttribute(String sessionContainerId,String attributeKey,int waiterNum){
		if(Utils.StringIsEmpty(sessionContainerId)||Utils.StringIsEmpty(attributeKey)) throw new SessionContainerRuntimeException("参数为空！");
		Command tmp = new CommandSetAttribute(getNextCommandIdInStr(), sessionContainerId, attributeKey,waiterNum);
		commandMap.put(getHolderKey(tmp.getSessionContainerId(),tmp.getCommandId()), tmp);
		return tmp;
	}
	public Command newCommandGetSessionAttribute(String sessionContainerId,String attributeKey){
		if(Utils.StringIsEmpty(sessionContainerId)||Utils.StringIsEmpty(attributeKey)) throw new SessionContainerRuntimeException("参数为空！");
		Command tmp = new CommandGetAttribute(getNextCommandIdInStr(), sessionContainerId, attributeKey,1);
		commandMap.put(getHolderKey(tmp.getSessionContainerId(),tmp.getCommandId()), tmp);
		return tmp;
	}
	public Command newCommandGetSessionAttributeNames(String sessionContainerId){
		if(Utils.StringIsEmpty(sessionContainerId)) throw new SessionContainerRuntimeException("参数为空！");
		Command tmp = new CommandGetAttributeNames(getNextCommandIdInStr(), sessionContainerId, null,1);
		commandMap.put(getHolderKey(tmp.getSessionContainerId(),tmp.getCommandId()), tmp);
		return tmp;
	}
	
	public Command newCommandRmvSessionAttribute(String sessionContainerId,String attributeKey,int waiterNum){
		if(Utils.StringIsEmpty(sessionContainerId)||Utils.StringIsEmpty(attributeKey)) throw new SessionContainerRuntimeException("参数为空！");
		Command tmp = new CommandRmvAttribute(getNextCommandIdInStr(), sessionContainerId, attributeKey,waiterNum);
		commandMap.put(getHolderKey(tmp.getSessionContainerId(),tmp.getCommandId()), tmp);
		return tmp;
	}
	public Command newCommandDelSession(String sessionContainerId,int waiterNum){
		if(Utils.StringIsEmpty(sessionContainerId)) throw new SessionContainerRuntimeException("参数为空！");
		Command tmp = new CommandDelSession(getNextCommandIdInStr(), sessionContainerId, null,waiterNum);
		commandMap.put(getHolderKey(tmp.getSessionContainerId(),tmp.getCommandId()), tmp);
		return tmp;
	}

}
