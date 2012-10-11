package org.sessioncontainer.client.command;

public class CommandGetAttribute extends AbstractCommand{

	CommandGetAttribute(String commandId, String sessionContainerId,
			String attributeKey, int waiterNum) {
		super(commandId, sessionContainerId, attributeKey, waiterNum);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getCommandFlag() {
		return CommandDef.COMMAND_GET_SESSION_ATTRIBUTE;
	}

}
