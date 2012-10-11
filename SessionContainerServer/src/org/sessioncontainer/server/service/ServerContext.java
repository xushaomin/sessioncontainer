package org.sessioncontainer.server.service;

import java.util.concurrent.Executor;

import org.sessioncontainer.server.DefaultStaticRef;
import org.sessioncontainer.server.ServerConfig;




public class ServerContext {
	private final long connectionTimeout;
	private final Executor nettyBossExecutor;
	private final Executor nettyWorkerExecutor;
	private final int port;
	private final int commandQueueCapcity;
	
	
	public ServerContext(ServerConfig serverConfig) {
		this.connectionTimeout		= serverConfig.getConnectionTimeout();
		this.port					= serverConfig.getPort();
		this.commandQueueCapcity	= serverConfig.getCommandQueueCapcity();
		this.nettyBossExecutor		= DefaultStaticRef.NEETY_BOSS_EXECUTOR;
		this.nettyWorkerExecutor	= DefaultStaticRef.NEETY_WORKER_EXECUTOR;
	}
	public long getConnectionTimeout() {
		return connectionTimeout;
	}
	public Executor getNettyBossExecutor() {
		return nettyBossExecutor;
	}
	public Executor getNettyWorkerExecutor() {
		return nettyWorkerExecutor;
	}
	public int getPort() {
		return port;
	}
	public int getCommandQueueCapcity() {
		return commandQueueCapcity;
	}
}


