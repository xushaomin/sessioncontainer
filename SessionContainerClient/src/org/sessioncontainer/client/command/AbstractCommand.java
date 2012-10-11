package org.sessioncontainer.client.command;

import java.io.IOException;

import org.sessioncontainer.client.SessionContainerException;
import org.sessioncontainer.codec.CodecHelpler;
import org.sessioncontainer.codec.Message;

abstract class AbstractCommand implements Command {
	private Object attachment;
	private final String commandId;
	private final String sessionContainerId;
	private final String attributeKey;
	private int waiterNum = 0;
	
	

	protected AbstractCommand(String commandId, String sessionContainerId,String attributeKey,int waiterNum) {
		super();
		this.commandId = commandId;
		this.sessionContainerId = sessionContainerId;
		this.attributeKey = attributeKey;
		this.waiterNum = waiterNum;
	}

	
	@Override
	public String getCommandId() {
		return commandId;
	}
	@Override
	public String getSessionContainerId() {
		return sessionContainerId;
	}
	@Override
	public String getAttributeKey() {
		return attributeKey;
	}

	@Override
	public Message createMessage() throws SessionContainerException{
		Message msg = new Message();
		msg.setAttributeKey(this.attributeKey);
		msg.setCommandFlag(getCommandFlag());
		msg.setCommandId(this.commandId);
		msg.setSessionContainerId(this.sessionContainerId);
		try {
			msg.setValue(CodecHelpler.convertObject2Bytes(attachment));
		} catch (IOException e) {
			throw new SessionContainerException("序列化时发生错误！",e);
		}
		return msg;
	}
	protected abstract String getCommandFlag();

	@Override
	public void reciveMessage(Message msg) throws SessionContainerException {
		byte[] mseeageBytes = msg.getValue();
		try {
			this.attachment = (mseeageBytes==null||mseeageBytes.length==0)?null:CodecHelpler.convertBytes2Object(mseeageBytes);
		} catch (ClassNotFoundException | IOException e) {
			throw new SessionContainerException("接受消息时发生错误！ ",e);
		}
	}

	@Override
	public void signal() {
		synchronized(this){
			waiterNum--;
			if(waiterNum==0)this.notifyAll();
		}
	}
	@Override
	public void await() throws InterruptedException {
		if (Thread.interrupted()) {
            throw new InterruptedException();
        }
		synchronized(this){
			if(waiterNum!=0) this.wait();
		}
	}
	@Override
	public Object getAttachment() {
		return this.attachment;
	}

	@Override
	public Object setAttachment(Object attachment) {
		return this.attachment = attachment;
	}

}
