package org.sessioncontainer.server.command;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.sessioncontainer.codec.Message;
import org.sessioncontainer.server.SessionContainerRuntimeException;
import org.sessioncontainer.server.service.ServerContext;
import org.sessioncontainer.server.service.SessionHolder;
import org.sessioncontainer.server.service.SessonServer;

public class CommandService{
	private final static Logger logger=Logger.getLogger(CommandService.class);
	
	
	private Executor commandExecutor;
	//private BlockingQueue<CommandHandler> commandQueue;
	private SessionHolder sessionHolder;
	
	public CommandService(ServerContext serverContext,SessionHolder sessionHolder){
		this.commandExecutor = Executors.newCachedThreadPool();
		//this.commandQueue = new ArrayBlockingQueue<CommandHandler>(serverContext.getCommandQueueCapcity());
		this.sessionHolder = sessionHolder;
	}
	private CommandHandler newCommandHandler(Channel channel,Message msg){
		String commandFlag = msg.getCommandFlag();
		if(CommandDef.COMMAND_DEL_SESSION.equals(commandFlag)){
			return new CommandHanlderDelSession(channel, msg);
		}else if(CommandDef.COMMAND_GET_SESSION_ATTRIBUTE.equals(commandFlag)){
			return new CommandHandlerGetAttribute(channel, msg);
		}else if(CommandDef.COMMAND_GET_SESSION_ATTRIBUTE_NAMES.equals(commandFlag)){
			return new CommandHandlerGetAttributeNames(channel, msg);
		}else if(CommandDef.COMMAND_RMV_SESSION_ATTRIBUTE.equals(commandFlag)){
			return new CommandHandlerRmvAttribute(channel, msg);
		}else if(CommandDef.COMMAND_SET_SESSION_ATTRIBUTE.equals(commandFlag)){
			return new CommandHandlerSetAttribute(channel, msg);
		}else{
			throw new SessionContainerRuntimeException("非法消息参数！");
		}
	}
	public void recevieMsg(Channel channel,Message msg){
		logger.debug("收到消息："+msg);
		CommandHandler commandHandler = newCommandHandler(channel,msg);
		this.commandExecutor.execute(new CommandJob(commandHandler));
	}
	
	
	private class CommandJob implements Runnable{
		private final CommandHandler commandHandler;
		private CommandJob(CommandHandler commandHandler){
			this.commandHandler = commandHandler;
		}
		@Override
		public void run() {
			try{
				this.commandHandler.doHandler(sessionHolder);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
