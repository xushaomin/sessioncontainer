package org.sessioncontainer.client.service;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.sessioncontainer.client.SessionContainerException;
import org.sessioncontainer.client.command.Command;
import org.sessioncontainer.client.command.CommandHolder;
import org.sessioncontainer.codec.Message;

public class SessionServiceNode{
	private final static Logger logger=Logger.getLogger(SessionServiceNode.class);
	
	private final SessionConnector[] connectors;
	private final int ConnectorLength;
	private final CommandHolder commandHolder;
	public SessionServiceNode(ServiceContext serviceContext,NodeAddress nodeAddress) throws SessionContainerException {
		 InetSocketAddress[] address =nodeAddress.getAddressArray();
		 connectors = new SessionConnector[address.length];
		 ConnectorLength = address.length;
		 int index=0;
		 try {
			 for(;index<address.length;index++){
				 connectors[index] = new SessionConnector(serviceContext,this,address[index]);;
			 }
			 commandHolder = serviceContext.getCommandHolder();
		 } catch (SessionContainerException e) {
			 //如果再创建某个连接时，发生错误！，则报错，并关闭原有连接
			 for(int i=0;i<index;i++){
				 connectors[index].shutdown();
			 }
			 throw e;
		 }
	}
	
	//---读取属性
	private AtomicInteger connIndex4GetAttribute = new AtomicInteger(0);
	private SessionConnector nextConnectorByRead(){
		int indexCount = connIndex4GetAttribute.incrementAndGet();
		int indexSelected = indexCount%ConnectorLength;
		if(indexSelected==0){
			connIndex4GetAttribute.compareAndSet(indexCount, 0);//重置计数，总有一个线程能正确重置
		}
		return connectors[indexSelected];
	}
	public Object getAttribute(String sessionContainerId,String attributeKey) throws SessionContainerException, InterruptedException{
		Command command = commandHolder.newCommandGetSessionAttribute(sessionContainerId, attributeKey);
		SessionConnector connector = nextConnectorByRead();
		doCommandWithSingleConn(command,connector);
		return command.getAttachment();
	}
	public List<String> getAttributeNames(String sessionContainerId) throws SessionContainerException, InterruptedException{
		Command command = commandHolder.newCommandGetSessionAttributeNames(sessionContainerId);
		SessionConnector connector = nextConnectorByRead();
		doCommandWithSingleConn(command,connector);
		return (List<String>) command.getAttachment();
	}
	private void doCommandWithSingleConn(Command command,SessionConnector connector) throws SessionContainerException, InterruptedException{
		connector.writeMessage(command.createMessage());
		command.await();//等待结束，请求完成，则移除command
		commandHolder.rmvCommand(command.getSessionContainerId(),command.getCommandId());
	}
	
	//---修改属性+删除session
	public void setAttribute(String sessionContainerId,String attributeKey,Object obj) throws SessionContainerException, InterruptedException{
		Command command = commandHolder.newCommandSetSessionAttribute(sessionContainerId, attributeKey, ConnectorLength);
		command.setAttachment(obj);
		doCommandWithAllConn(command);
	}
	public void rmvAttribute(String sessionContainerId,String attributeKey) throws SessionContainerException, InterruptedException{
		Command command = commandHolder.newCommandRmvSessionAttribute(sessionContainerId, attributeKey, ConnectorLength);
		doCommandWithAllConn(command);
	}
	public void delSession(String sessionContainerId) throws SessionContainerException, InterruptedException{
		Command command = commandHolder.newCommandDelSession(sessionContainerId, ConnectorLength);
		doCommandWithAllConn(command);
	}
	private void doCommandWithAllConn(Command command) throws SessionContainerException, InterruptedException{
		Message msg = command.createMessage();
		for(SessionConnector conn:connectors){
			conn.writeMessage(msg);
		}
		command.await();
		commandHolder.rmvCommand(command.getSessionContainerId(),command.getCommandId());
	}
	//--接受消息并唤醒command
	public void reciveMsg(Message msg){
		Command command = commandHolder.getCommand(msg.getSessionContainerId(),msg.getCommandId());
		try {
			command.reciveMessage(msg);
		} catch (SessionContainerException e) {
			logger.error("唤醒命令时发生错误",e);
		}
		command.signal();
	}
	public void shutdown(){
		for(SessionConnector conn:connectors){
			conn.shutdown();
		}
	}
}
