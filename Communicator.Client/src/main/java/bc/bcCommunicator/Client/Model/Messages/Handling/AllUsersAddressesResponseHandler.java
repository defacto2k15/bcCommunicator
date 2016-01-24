package bc.bcCommunicator.Client.Model.Messages.Handling;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.Model.IOtherUsersDataContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class AllUsersAddressesResponseHandler.
 */
public class AllUsersAddressesResponseHandler extends AbstractMessageHandler{
	
	/** The users container. */
	IOtherUsersDataContainer usersContainer;
	
	/** The controller. */
	private ICommunicatorController controller;
	
	/** The messager. */
	private IInternetMessager messager;
	
	/** The client url. */
	private URL clientUrl;
	
	
	/**
	 * Instantiates a new all users addresses response handler.
	 *
	 * @param container the container
	 * @param messager the messager
	 * @param clientUrl the client url
	 * @param controller the controller
	 */
	public AllUsersAddressesResponseHandler(IOtherUsersDataContainer container, 
			IInternetMessager messager, URL clientUrl,  ICommunicatorController controller) {
				this.usersContainer = container;
				this.messager = messager;
				this.clientUrl = clientUrl;
				this.controller = controller;
				
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse, bc.internetMessageProxy.ConnectionId)
	 */
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
			messager.connectToUser(key);
		}
		
		int clientPort = clientUrl.getPort();
		messager.listenOnPort(clientPort);
	}

}
