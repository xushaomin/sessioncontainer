
package org.sessioncontainer.client.command;

import org.sessioncontainer.client.SessionContainerException;
import org.sessioncontainer.codec.Message;
public interface Command {
	Message createMessage() throws SessionContainerException;
	void reciveMessage(Message msg) throws SessionContainerException;
	void signal();
	void await() throws InterruptedException;
	Object getAttachment();
	Object setAttachment(Object attachment);
	String getCommandId();
	String getSessionContainerId();
	String getAttributeKey();
}
