package org.sessioncontainer.server.command;

import org.jboss.netty.channel.Channel;
import org.sessioncontainer.codec.Message;
import org.sessioncontainer.server.service.SessionHolder;

public abstract class CommandHandler{
	private final Channel channel;
	private final Message msg;
	private final String sessionContainerId;
	public CommandHandler(Channel channel,Message msg) {
		this.channel = channel;
		this.msg = msg;
		this.sessionContainerId = msg.getSessionContainerId();
	}

	protected Message getMsg() {
		return msg;
	}
	protected String getSessionContainerId() {
		return sessionContainerId;
	}
	protected void sendMsg(){
		this.channel.write(msg);
	}
	public abstract void doHandler(SessionHolder sessionHolder);
}
