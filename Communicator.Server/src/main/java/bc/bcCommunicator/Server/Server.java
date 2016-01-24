package bc.bcCommunicator.Server;
import java.net.URL;

import bc.bcCommunicator.Model.Internet.IInternetMessagerListener;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class Server.
 */
public class Server implements IInternetMessagerListener{
	
	/** The message handler. */
	private IRecievedMessagesHandler messageHandler;
	
	/** The users container. */
	private UsersContainer usersContainer;

	/**
	 * Instantiates a new server.
	 *
	 * @param messageHandler the message handler
	 * @param usersContainer the users container
	 */
	public Server( IRecievedMessagesHandler messageHandler, UsersContainer usersContainer) {
		this.messageHandler = messageHandler;
		this.usersContainer = usersContainer;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#messageWasRecieved(bc.bcCommunicator.Model.Messages.IMessage, bc.internetMessageProxy.ConnectionId)
	 */
	public void messageWasRecieved(IMessage recievedMessage, ConnectionId connectionId) {
		try {
			System.out.println("Recieved message: \n"+recievedMessage.getMessageText());
		} catch (Exception e) {
			System.err.println("E001");
			e.printStackTrace();
		}
		messageHandler.handle(recievedMessage, connectionId);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#connectionLost(bc.internetMessageProxy.ConnectionId)
	 */
	public void connectionLost(ConnectionId id) {
		usersContainer.removeUserWithConnectionId(id);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#messageSentSuccesfully(bc.internetMessageProxy.ConnectionId)
	 */
	public void messageSentSuccesfully(ConnectionId id) throws Exception {
		System.out.println("M004 message sent succesfully");
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#messageSendingFailed(bc.internetMessageProxy.ConnectionId)
	 */
	public void messageSendingFailed(ConnectionId id) {
		System.out.println("M003 message sending failed");
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#userConnectionFailed(java.net.URL)
	 */
	public void userConnectionFailed(URL userAddress) {
		System.err.println("E001 not expected function");
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#userConnectionWasSuccesfull(java.net.URL, bc.internetMessageProxy.ConnectionId)
	 */
	public void userConnectionWasSuccesfull(URL userAddress, ConnectionId connectionId) throws Exception {
		System.err.println("E001 not expected function");
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#serverConnectionFailed()
	 */
	public void serverConnectionFailed() {
		System.err.println("E001 not expected function");
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#serverConnectionWasSuccesfull(bc.internetMessageProxy.ConnectionId)
	 */
	public void serverConnectionWasSuccesfull(ConnectionId connectionId) throws Exception {
		System.err.println("E002 not expected function");
	}

}
