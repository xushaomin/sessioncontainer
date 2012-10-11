package org.sessioncontainer.server.command;

import org.jboss.netty.channel.Channel;
import org.sessioncontainer.codec.Message;
import org.sessioncontainer.server.service.SessionHolder;
import org.sessioncontainer.server.service.SessionInfo;

public class CommandHandlerRmvAttribute extends CommandHandler {
	public CommandHandlerRmvAttribute(Channel channel, Message msg) {
		super(channel, msg);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void doHandler(SessionHolder sessionHolder) {
		SessionInfo session = sessionHolder.getSessionInfo(getSessionContainerId());
		session.rmvAttribute(getMsg().getAttributeKey());
		sendMsg();
	}
}
