package org.sessioncontainer.server.command;

import java.io.IOException;
import java.util.List;

import org.jboss.netty.channel.Channel;
import org.sessioncontainer.codec.CodecHelpler;
import org.sessioncontainer.codec.Message;
import org.sessioncontainer.server.service.SessionHolder;
import org.sessioncontainer.server.service.SessionInfo;

public class CommandHandlerGetAttributeNames extends CommandHandler {

	public CommandHandlerGetAttributeNames(Channel channel, Message msg) {
		super(channel, msg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doHandler(SessionHolder sessionHolder) {
		SessionInfo session = sessionHolder.getSessionInfo(getSessionContainerId());
		List<String> attributeNames = session.getAttributeNames();
		byte[] data = null;
		try {
			data = CodecHelpler.convertObject2Bytes(attributeNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
		getMsg().setValue(data);
		sendMsg();
	}

}
