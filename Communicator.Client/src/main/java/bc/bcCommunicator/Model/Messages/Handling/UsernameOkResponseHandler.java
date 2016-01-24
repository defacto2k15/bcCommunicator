package bc.bcCommunicator.Model.Messages.Handling;

import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.IActorUsernameContainer;
import bc.bcCommunicator.Model.IModelMessagesSender;
import bc.bcCommunicator.Model.IOtherUsersDataContainer;
import bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameOkResponse;
import bc.bcCommunicator.Views.IUsernameInputView;
import bc.bcCommunicator.Views.UsernameInputStatus;
import bc.bcCommunicator.Views.UsernameInputView;
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
