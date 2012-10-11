package org.sessioncontainer.client.command;

public class CommandGetAttributeNames extends AbstractCommand{

	CommandGetAttributeNames(String commandId, String sessionContainerId, String attributeKey,int waiterNum) {
		super(commandId, sessionContainerId, attributeKey, waiterNum);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getCommandFlag() {
		// TODO Auto-generated method stub
		return CommandDef.COMMAND_RMV_SESSION_ATTRIBUTE;
	}

}
