package org.sessioncontainer.codec;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class MessageEncoder extends OneToOneEncoder {
	private final static Logger logger=Logger.getLogger(MessageEncoder.class);
	public MessageEncoder() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		if (!(msg instanceof Message)){return msg;}
		logger.debug("MessageEncoder[client]--msg["+msg+"]");
		return ((Message)msg).toByteArray();
	}
}
