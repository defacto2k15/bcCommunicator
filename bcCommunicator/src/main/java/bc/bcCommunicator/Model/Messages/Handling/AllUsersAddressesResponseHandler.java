package bc.bcCommunicator.Model.Messages.Handling;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.IModelMessagesSender;
import bc.bcCommunicator.Model.IOtherUsersDataContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.internetMessageProxy.ConnectionId;

public class AllUsersAddressesResponseHandler extends AbstractMessageHandler{
	IOtherUsersDataContainer usersContainer;
	private ICommunicatorController controller;
	private IInternetMessagerCommandProvider commandProvider;
	private IInternetMessager messager;
	
	
	public AllUsersAddressesResponseHandler(IOtherUsersDataContainer container,
			IInternetMessagerCommandProvider commandProvider, IInternetMessager messager) {
				this.usersContainer = container;
				this.commandProvider = commandProvider;
				this.messager = messager;
	}

	public void setController(  ICommunicatorController controller ){
		this.controller = controller;
	}
	
	public void handle( IAllUsersAddressesResponse usernameOkResponse, ConnectionId id) throws Exception{
		assert(controller != null);
		List<Username > usernames = new ArrayList<>();
		AllUsersAddresses allUserAddresses = usernameOkResponse.getAllUsersAddresses();
		Set<Username> sortedUsernames = new TreeSet<Username>(allUserAddresses.getAllUsersAddresses().keySet() );
		for( Username name : sortedUsernames){
			usernames.add(name);
			usersContainer.addUserWithAddress(name, usernameOkResponse.getAllUsersAddresses().getAllUsersAddresses().get(name));
		}
			
		controller.setBulkUsers(usernames);
		
		for( URL key : allUserAddresses.getAllUsersAddresses().values()){
			messager.addCommand(commandProvider.getConnectToUserCommand(key));
		}
	}

}
