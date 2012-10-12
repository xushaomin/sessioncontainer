package org.sessioncontainer.codec;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class MessageBytesFrameEncoder extends OneToOneEncoder {
	private final static Logger logger=Logger.getLogger(MessageBytesFrameEncoder.class);
	public MessageBytesFrameEncoder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,Object msg) throws Exception {
		logger.debug("MessageBytesFrameEncoder");
		if (!(msg instanceof byte[])){return msg;}
		byte[] body = (byte[])msg;
		byte[] lengthHead = CodecHelpler.convertIntTo4Bytes(body.length);
		return ChannelBuffers.wrappedBuffer(lengthHead,body);
	}

}
