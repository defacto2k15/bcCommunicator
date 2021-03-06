package bc.bcCommunicator.Server;
import java.net.URL;
import java.util.Map;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.Request.IAllUsersAddressesRequest;
import bc.bcCommunicator.Model.Messages.Request.IIntroductoryRequest;
import bc.bcCommunicator.Model.Messages.Response.AllUsersAddressesResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameBadResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameOkResponse;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerMessagesHandler.
 */
public class ServerMessagesHandler extends AbstractMessageHandler implements IServerMessagesHandler{
	
	/** The messager. */
	private IInternetMessager messager;
	
	/** The users container. */
	private UsersContainer usersContainer;

	/**
	 * Instantiates a new server messages handler.
	 *
	 * @param messager the messager
	 * @param usersContainer the users container
	 */
	public ServerMessagesHandler(IInternetMessager messager, UsersContainer usersContainer) {
		this.messager = messager;
		this.usersContainer = usersContainer;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler#handle(bc.bcCommunicator.Model.Messages.IMessage, bc.internetMessageProxy.ConnectionId)
	 */
	public void handle(IMessage message, ConnectionId id){
		try {
			message.chooseHandler(this, id);
		} catch (Exception e) {
			String messageText = null;
			try {
				messageText = message.getMessageText();
			} catch (Exception e1) {
				e1.printStackTrace();
			}		
			System.err.println("Exception during handling of message "+messageText);
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Request.IIntroductoryRequest, bc.internetMessageProxy.ConnectionId)
	 */
	public void handle(IIntroductoryRequest introductoryRequest, ConnectionId id) throws Exception{
		Username username = introductoryRequest.getUsername();
		IMessage outMessage;
		if( usersContainer.containsUserWithUsername(username) ){
			outMessage = new UsernameBadResponse(username);
		} else {
			usersContainer.addUser( username, introductoryRequest.getClientUrl(), id);
			outMessage = new UsernameOkResponse(username);
		}
		messager.sendMessage(id, outMessage.getMessageText());
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Request.IAllUsersAddressesRequest, bc.internetMessageProxy.ConnectionId)
	 */
	public void handle( IAllUsersAddressesRequest request, ConnectionId id ){
		try{
			Username user = usersContainer.getUserWithConnectionId(id);
			Map<Username, URL> allUsersAddressesMap = usersContainer.getAllUsersAddressesMap();
			allUsersAddressesMap.remove(user);
			messager.sendMessage(id, new AllUsersAddressesResponse(new AllUsersAddresses(allUsersAddressesMap)).getMessageText());
		} catch( Exception e){
			e.printStackTrace();
			System.err.println("E005");
		}
	}
}
