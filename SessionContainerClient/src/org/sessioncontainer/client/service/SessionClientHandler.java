package org.sessioncontainer.client.service;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.sessioncontainer.codec.Message;

class SessionClientHandler extends SimpleChannelUpstreamHandler {
	private final SessionConnector sessionConnector;
	public SessionClientHandler(SessionConnector sessionConnector) {
		this.sessionConnector = sessionConnector;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		Message msg=(Message)e.getMessage();
		sessionConnector.readMessage(msg);
	}
}
