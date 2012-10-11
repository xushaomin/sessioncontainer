package org.sessioncontainer.client;

import java.util.concurrent.Executor;

import org.dom4j.DocumentException;

public class DefaultStaticRef {
	public static final Executor NEETY_BOSS_EXECUTOR	= java.util.concurrent.Executors.newCachedThreadPool();
	public static final Executor NEETY_WORKER_EXECUTOR	= java.util.concurrent.Executors.newCachedThreadPool();
	public static final ClientConfig CLIENT_CONFIG;
	static{
		CLIENT_CONFIG		= new ClientConfig();
		try {
			CLIENT_CONFIG.load();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
