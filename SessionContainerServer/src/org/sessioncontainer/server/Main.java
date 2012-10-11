package org.sessioncontainer.server;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.sessioncontainer.server.service.SessonServer;

public class Main {
	private final static Logger logger=Logger.getLogger(Main.class);
	/**
	 * @param args
	 * @throws DocumentException 
	 */
	public static void main(String[] args) throws DocumentException {
		long time = System.currentTimeMillis();
		ServerConfig serverConfig = new ServerConfig();
		serverConfig.load();
		final SessonServer sessonServer = new SessonServer(serverConfig);
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				sessonServer.shutDown();
			}
		}));
		time = System.currentTimeMillis() - time;
		System.out.println("启动完成,耗时"+time+"ms.");
	}

}
