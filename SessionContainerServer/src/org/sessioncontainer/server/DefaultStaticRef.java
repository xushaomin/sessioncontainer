package org.sessioncontainer.server;

import java.util.concurrent.Executor;

public class DefaultStaticRef {
	public static final Executor NEETY_BOSS_EXECUTOR	= java.util.concurrent.Executors.newCachedThreadPool();
	public static final Executor NEETY_WORKER_EXECUTOR	= java.util.concurrent.Executors.newCachedThreadPool();
	public static final ServerConfig CLIENT_CONFIG		= new ServerConfig();
}
