package org.sessioncontainer.codec;


import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.CorruptedFrameException;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/*格式化字传输的节流
 * 
 */
public class MessageBytesFrameDecoder extends FrameDecoder {
	private final static Logger logger=Logger.getLogger(MessageBytesFrameDecoder.class);
	public MessageBytesFrameDecoder(){
		super();
	}
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		buffer.markReaderIndex();//标记已经读取的偏移量
		final byte[] lengthByteBuf = new byte[4];//字节长度用一个整形表示，需要4个字节来存储
		for(int i=0,n=lengthByteBuf.length;i<n;i++){//读取4个字节先
			if (!buffer.readable()){//如果有字节未到达，则返回空
                buffer.resetReaderIndex();
                return null;
            }
			//暂存字节
            lengthByteBuf[i] = buffer.readByte();
		}
		int length = CodecHelpler.convert4BytesToInt(lengthByteBuf[0], lengthByteBuf[1], lengthByteBuf[2], lengthByteBuf[3]);//此时4个字节已经存储可以转换为一个整形
		if (length < 0) {
            throw new CorruptedFrameException("negative length: " + length);
        }
		if (buffer.readableBytes() < length) {
			logger.debug("MessageBytesFrameDecoder[client]--数据未接受完全！");
            buffer.resetReaderIndex();
            return null;
        } else {
        	logger.debug("MessageBytesFrameDecoder[client]--接收到消息，长度["+length+"]");
        	byte[] bytes = new byte[length];
            buffer.readBytes(bytes);
            return bytes;
        }
	}

}
