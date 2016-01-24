package bc.bcCommunicator.Server;
import java.net.URL;

import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.IInternetMessagerListener;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.internetMessageProxy.ConnectionId;

public class Server implements IInternetMessagerListener{
	private IRecievedMessagesHandler messageHandler;
	private UsersContainer usersContainer;

	public Server( IRecievedMessagesHandler messageHandler, UsersContainer usersContainer) {
		this.messageHandler = messageHandler;
		this.usersContainer = usersContainer;
	}

	public void messageWasRecieved(IMessage recievedMessage, ConnectionId connectionId) {
		try {
			System.out.println("Recieved message: \n"+recievedMessage.getMessageText());
		} catch (Exception e) {
			System.err.println("E001");
			e.printStackTrace();
		}
		messageHandler.handle(recievedMessage, connectionId);
	}

	public void connectionLost(ConnectionId id) {
		usersContainer.removeUserWithConnectionId(id);
	}

	public void messageSentSuccesfully(ConnectionId id) throws Exception {
		System.out.println("M004 message sent succesfully");
	}

	public void messageSendingFailed(ConnectionId id) {
		System.out.println("M003 message sending failed");
	}

	public void userConnectionFailed(URL userAddress) {
		System.err.println("E001 not expected function");
	}

	public void userConnectionWasSuccesfull(URL userAddress, ConnectionId connectionId) throws Exception {
		System.err.println("E001 not expected function");
	}
	
	public void serverConnectionFailed() {
		System.err.println("E001 not expected function");
	}

	public void serverConnectionWasSuccesfull(ConnectionId connectionId) throws Exception {
		System.err.println("E002 not expected function");
	}

}
