package bc.bcCommunicator.Model.Messages.Handling;

import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.Messages.Response.IUsernameBadResponse;
import bc.internetMessageProxy.ConnectionId;

public class UsernameBadMessageHandler extends AbstractMessageHandler {
	private ICommunicatorController controller;

	public UsernameBadMessageHandler(ICommunicatorController controller) {
		this.controller = controller;
	}
	
	@Override
	public void handle( IUsernameBadResponse response, ConnectionId id) throws Exception{
		controller.usernameWasBad();
	}
	
}
