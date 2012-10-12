package org.sessioncontainer.codec;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

public class MessageDecoder extends OneToOneDecoder {
	private final static Logger logger=Logger.getLogger(MessageDecoder.class);
	public MessageDecoder() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		logger.debug("MessageDecoder");
		if (!(msg instanceof byte[])) {
            return msg;
        }
		Message message = new Message();
		message.mergeFrom((byte[]) msg);
		return message;
	}

}
