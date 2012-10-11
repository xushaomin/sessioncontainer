package org.sessioncontainer.codec;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

public class MessageDecoder extends OneToOneDecoder {

	public MessageDecoder() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		if (!(msg instanceof byte[])) {
            return msg;
        }
		Message message = new Message();
		message.mergeFrom((byte[]) msg);
		return message;
	}

}
