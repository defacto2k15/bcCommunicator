package bc.bcCommunicator.Client.Model.Messages.Handling;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.Model.IActorUsernameContainer;
import bc.bcCommunicator.Client.Model.IModelMessagesSender;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse;
import bc.internetMessageProxy.ConnectionId;

public class UsernameOkResponseHandler extends AbstractMessageHandler {
	private IActorUsernameContainer container;
	private IModelMessagesSender messagesSender;
	private ICommunicatorController controller;

	public UsernameOkResponseHandler( IActorUsernameContainer container,
			IModelMessagesSender messagesSender, ICommunicatorController controller){
		this.container = container;
		this.messagesSender = messagesSender;
		this.controller = controller;
	}
	
	@Override
	public void handle( IUsernameOkResponse response, ConnectionId id) throws Exception{
		container.setUsername(response.getUsername());
		controller.usernameWasOk();
		messagesSender.sendAllUsersAddressesRequest();
	}
}
