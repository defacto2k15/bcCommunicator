package bc.bcCommunicator.Model.Messages.Handling;

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
	private IUsernameInputView inputView;
	private IModelMessagesSender messagesSender;

	public UsernameOkResponseHandler( IActorUsernameContainer container, IUsernameInputView inputView,
			IModelMessagesSender messagesSender){
		this.container = container;
		this.inputView = inputView;
		this.messagesSender = messagesSender;
	}
	
	@Override
	public void handle( IUsernameOkResponse response, ConnectionId id) throws Exception{
		container.setUsername(response.getUsername());
		inputView.setUsernameInputStatus(UsernameInputStatus.UsernameOk);
		messagesSender.sendAllUsersAddressesRequest();
	}
}
