package org.sessioncontainer.client.command;

public class CommandSetAttribute extends AbstractCommand {

	
	CommandSetAttribute(String commandId, String sessionContainerId,
			String attributeKey, int waiterNum) {
		super(commandId, sessionContainerId, attributeKey, waiterNum);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getCommandFlag() {
		// TODO Auto-generated method stub
		return CommandDef.COMMAND_SET_SESSION_ATTRIBUTE;
	}

}
