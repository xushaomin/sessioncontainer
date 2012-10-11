package org.sessioncontainer.server.service;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.sessioncontainer.server.command.CommandService;

public class ServerPipelineFactory implements ChannelPipelineFactory{
	private ChannelGroup channelGroup;
	private CommandService commandService;
	
	public ServerPipelineFactory(ChannelGroup channelGroup,CommandService commandService) {
		this.channelGroup = channelGroup;
		this.commandService = commandService;
	}

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		//---字节流格式化
		pipeline.addLast("frameDecoder", new org.sessioncontainer.codec.MessageBytesFrameDecoder());
		pipeline.addLast("messageDecoder", new org.sessioncontainer.codec.MessageDecoder());
		pipeline.addLast("frameEncoder", new org.sessioncontainer.codec.MessageBytesFrameEncoder());
		pipeline.addLast("messageEncoder", new org.sessioncontainer.codec.MessageEncoder());
		//---接受消息
		pipeline.addLast("serverHandler", new SessionChannelHandler(channelGroup,commandService));
		
		return pipeline;
	}

}
