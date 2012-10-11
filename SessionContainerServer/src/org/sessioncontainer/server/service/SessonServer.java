package org.sessioncontainer.server.service;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.sessioncontainer.server.Main;
import org.sessioncontainer.server.ServerConfig;
import org.sessioncontainer.server.command.CommandService;

public class SessonServer {
	private final static Logger logger=Logger.getLogger(SessonServer.class);
	private ServerContext serverContext;
	
	private final ChannelGroup channelGroup;
	private final InetSocketAddress address;
	private final NioServerSocketChannelFactory channelFactory;
	private final ServerBootstrap bootstrap;
	private final ChannelPipelineFactory pipelineFactory;
	
	
	private CommandService commandService;
	/**
	 * @param args
	 */
	public SessonServer(ServerConfig config) {
		this.serverContext = new ServerContext(config);
		this.address = new InetSocketAddress(config.getPort());
		this.channelGroup = new DefaultChannelGroup("httpSessionGroup");
		this.commandService = new CommandService(serverContext,new SessionHolder());
		
		this.channelFactory = new NioServerSocketChannelFactory(serverContext.getNettyBossExecutor(),serverContext.getNettyWorkerExecutor());
		this.bootstrap = new ServerBootstrap(channelFactory);
		this.pipelineFactory = new ServerPipelineFactory(channelGroup,commandService);
		this.bootstrap.setPipelineFactory(this.pipelineFactory);
		this.bootstrap.setOption("child.tcpNoDelay", true);
		this.bootstrap.setOption("child.keepAlive", true);
		this.channelGroup.add(this.bootstrap.bind(this.address));
	}
	
	public void shutDown(){
		logger.debug("服务器关闭!");
		channelGroup.close().awaitUninterruptibly();
        channelFactory.releaseExternalResources();
	}

}
