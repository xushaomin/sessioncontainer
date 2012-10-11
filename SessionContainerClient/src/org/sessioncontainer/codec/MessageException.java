package org.sessioncontainer.codec;

public class MessageException extends Exception{
	private static final long serialVersionUID = 1L;
	public static MessageException makeMessageException(String message,Exception e){
		return new MessageException(message,e);
	}
	public static MessageException makeMessageException(String message){
		return new MessageException(message);
	}
	private MessageException(String message){
		super(message);
	}
	private MessageException(String message,Exception e){
		super(message,e);
	}
}
