package bc.bcCommunicator.Model.Messages.Handling;

import java.util.ArrayList;
import java.util.List;

import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.IModelMessagesSender;
import bc.bcCommunicator.Model.IUsernameContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.internetMessageProxy.ConnectionId;

public class AllUsersAddressesResponseHandler extends AbstractMessageHandler{
	IUsernameContainer usersContainer;
	private ICommunicatorController controller;
	private IModelMessagesSender messagesSender;
	
	public AllUsersAddressesResponseHandler(IUsernameContainer container, IModelMessagesSender messagesSender) {
		this.usersContainer = container;
		this.messagesSender = messagesSender;
	}
	
	public void setController(  ICommunicatorController controller ){
		this.controller = controller;
	}
	
	public void handle( IAllUsersAddressesResponse usernameOkResponse, ConnectionId id) throws Exception{
		assert(controller != null);
		List<Username > usernames = new ArrayList<>();
		for( Username name : usernameOkResponse.getAllUsersAddresses().getAllUsersAddresses().keySet() ){
			usernames.add(name);
			usersContainer.addUserWithAddress(name, usernameOkResponse.getAllUsersAddresses().getAllUsersAddresses().get(name));
		}
			
		controller.setBulkUsers(usernames);
	}

}
