package org.sessioncontainer.server.service;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.sessioncontainer.codec.Message;
import org.sessioncontainer.server.command.CommandService;

public class SessionChannelHandler extends SimpleChannelUpstreamHandler{
	private final static Logger logger=Logger.getLogger(SessionChannelHandler.class);
	private ChannelGroup channelGroup;
	private CommandService commandService;
	public SessionChannelHandler(ChannelGroup channelGroup,CommandService commandService) {
		this.channelGroup = channelGroup;
		this.commandService = commandService;
	}
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		channelGroup.remove(ctx.getChannel());
	}

	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)	throws Exception {
		channelGroup.add(ctx.getChannel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		logger.error(e, e.getCause());
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e){
		Message msg=(Message)e.getMessage();
		Channel channel = e.getChannel();
		commandService.recevieMsg(channel, msg);
	}
}
