package org.sessioncontainer.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

import org.sessioncontainer.client.ClientConfig;
import org.sessioncontainer.client.DefaultStaticRef;
import org.sessioncontainer.client.command.CommandHolder;


public class ServiceContext {
	private final long connectionTimeout;
	private final NodeAddress[] nodeAddressArray;
	private final Executor nettyBossExecutor;
	private final Executor nettyWorkerExecutor;
	private final CommandHolder commandHolder;
	
	
	public ServiceContext() {
		this.connectionTimeout = DefaultStaticRef.CLIENT_CONFIG.getConnectionTimeout();
		this.nodeAddressArray = formateNodeStr(DefaultStaticRef.CLIENT_CONFIG.getServerNodes());
		this.nettyBossExecutor = DefaultStaticRef.NEETY_BOSS_EXECUTOR;
		this.nettyWorkerExecutor = DefaultStaticRef.NEETY_WORKER_EXECUTOR;
		this.commandHolder = new CommandHolder();
	}
	private NodeAddress[] formateNodeStr(Set<String> nodesStr){
		List<NodeAddress> nodeList=new ArrayList<NodeAddress>();
		for(String str: nodesStr){
			nodeList.add(new NodeAddress(str));
		}
		return nodeList.toArray(new NodeAddress[nodeList.size()]);
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
	public NodeAddress[] getNodeAddressArray() {
		return nodeAddressArray;
	}
	public CommandHolder getCommandHolder() {
		return commandHolder;
	}
	
}


