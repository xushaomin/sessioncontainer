package org.sessioncontainer.server.command;

import org.jboss.netty.channel.Channel;
import org.sessioncontainer.codec.Message;
import org.sessioncontainer.server.service.SessionHolder;

public class CommandHanlderDelSession extends CommandHandler {
	public CommandHanlderDelSession(Channel channel, Message msg) {
		super(channel, msg);
	}
	@Override
	public void doHandler(SessionHolder sessionHolder) {
		sessionHolder.rmvSessionInfo(getSessionContainerId());
		sendMsg();
	}
}
