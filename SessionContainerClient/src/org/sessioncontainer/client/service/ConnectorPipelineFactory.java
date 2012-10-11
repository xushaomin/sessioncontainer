package org.sessioncontainer.client.service;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class ConnectorPipelineFactory implements ChannelPipelineFactory{
	private final SessionConnector sessionConnector;
	public ConnectorPipelineFactory(SessionConnector sessionConnector) {
		this.sessionConnector = sessionConnector;
	}
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		//---字节流格式化
		pipeline.addLast("frameDecoder", new org.sessioncontainer.codec.MessageBytesFrameDecoder());
		pipeline.addLast("messageDecoder", new org.sessioncontainer.codec.MessageDecoder());
		pipeline.addLast("frameEncoder", new org.sessioncontainer.codec.MessageBytesFrameEncoder());
		pipeline.addLast("messageEncoder", new org.sessioncontainer.codec.MessageEncoder());
		//收到消息时回调
		pipeline.addLast("clientHolder",new SessionClientHandler(sessionConnector));
		return pipeline;
	}

}
