package org.sessioncontainer.server.command;

import org.jboss.netty.channel.Channel;
import org.sessioncontainer.codec.Message;
import org.sessioncontainer.server.service.SessionHolder;
import org.sessioncontainer.server.service.SessionInfo;

public class CommandHandlerSetAttribute extends CommandHandler {

	public CommandHandlerSetAttribute(Channel channel, Message msg) {
		super(channel, msg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doHandler(SessionHolder sessionHolder) {
		SessionInfo session = sessionHolder.getSessionInfo(getSessionContainerId());
		session.setAttribute(getMsg().getAttributeKey(), getMsg().getValue());
		getMsg().setValue(null);
		sendMsg();
	}

}
