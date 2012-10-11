package org.sessioncontainer.client.service;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.sessioncontainer.client.SessionContainerException;
import org.sessioncontainer.codec.Message;
//如果一个节点连接失败了，那么应当丢弃当前的类，重建一个
public class SessionConnector {
	private final static Logger logger = Logger.getLogger(SessionConnector.class);
	
	private final InetSocketAddress address;
	private final NioClientSocketChannelFactory channelFactory;
	private final ChannelPipelineFactory pipelineFactory;
	private final ClientBootstrap bootstrap;
	private final Channel channel;
	private final SessionServiceNode serviceNode;
	
	public SessionConnector(ServiceContext serviceContext,SessionServiceNode serviceNode,InetSocketAddress address) throws SessionContainerException {
		this.address = address;
		this.serviceNode = serviceNode;
		channelFactory = new NioClientSocketChannelFactory(serviceContext.getNettyBossExecutor(),serviceContext.getNettyWorkerExecutor());
		pipelineFactory = new ConnectorPipelineFactory(this);
		bootstrap = new ClientBootstrap(channelFactory);
		bootstrap.setPipelineFactory(pipelineFactory);
		
		ChannelFuture future = bootstrap.connect(this.address);
		channel=future.awaitUninterruptibly().getChannel();
		
		//连接失败
		if (!future.isSuccess()) {
			try{
				throw new SessionContainerException("节点连接失败("+this.toString()+")",future.getCause());
			}finally{
				shutdown();
			}
		}
	}
	
	
	public void writeMessage(Message msg){
		ChannelFuture future= channel.write(msg);
	}
	public void readMessage(Message msg){
		serviceNode.reciveMsg(msg);
	}
	
	
	
	
	public void shutdown(){
        channel.close().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("IP:");
		sb.append(address.getHostName());
		sb.append(",Port:");
		sb.append(address.getPort());
		return sb.toString();
	}
	
}
