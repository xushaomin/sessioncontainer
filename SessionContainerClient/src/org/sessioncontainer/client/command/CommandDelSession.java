package org.sessioncontainer.client.command;

class CommandDelSession extends AbstractCommand {

	CommandDelSession(String commandId, String sessionContanerId, String attributeKey,
			int waiterNum) {
		super(commandId, sessionContanerId, attributeKey, waiterNum);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getCommandFlag() {
		// TODO Auto-generated method stub
		return CommandDef.COMMAND_GET_SESSION_ATTRIBUTE_NAMES;
	}

}
