package bc.bcCommunicator.Client.Model;

import java.net.URL;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectivityHandler.
 */
public class ConnectivityHandler implements IConnectivityHandler {
	
	/** The controller. */
	private ICommunicatorController controller;
	
	/** The our url. */
	private URL ourUrl;
	
	/** The connections container. */
	private IConnectionsContainer connectionsContainer;
	
	/** The username container. */
	private IOtherUsersDataContainer usernameContainer;
	
	/** The actor username container. */
	private IActorUsernameContainer actorUsernameContainer;
	
	/** The messages sender. */
	private IModelMessagesSender messagesSender;
	
	/** The pending letters container. */
	private IPendingLettersContainer pendingLettersContainer;
	
	/** The letter container. */
	private ILetterContainer letterContainer;
	
	/** The recieved hander. */
	private IRecievedMessagesHandler recievedHander;
	

	/**
	 * Instantiates a new connectivity handler.
	 *
	 * @param controller the controller
	 * @param ourUrl the our url
	 * @param connectionsContainer the connections container
	 * @param usernameContainer the username container
	 * @param actorUsernameContainer the actor username container
	 * @param messagesSender the messages sender
	 * @param pendingLettersContainer the pending letters container
	 * @param letterContainer the letter container
	 * @param recievedHander the recieved hander
	 */
	public ConnectivityHandler(ICommunicatorController controller, URL ourUrl,
			IConnectionsContainer connectionsContainer, IOtherUsersDataContainer usernameContainer,
			IActorUsernameContainer actorUsernameContainer, IModelMessagesSender messagesSender,
			IPendingLettersContainer pendingLettersContainer,  ILetterContainer letterContainer,
			IRecievedMessagesHandler recievedHander) {
		this.ourUrl = ourUrl;
		this.connectionsContainer = connectionsContainer;
		this.usernameContainer = usernameContainer;
		this.actorUsernameContainer = actorUsernameContainer;
		this.messagesSender = messagesSender;
		this.controller = controller;
		this.pendingLettersContainer = pendingLettersContainer;
		this.letterContainer = letterContainer;
		this.recievedHander = recievedHander;
	}


	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#userConnectionFailed(java.net.URL)
	 */
	@Override
	public void userConnectionFailed(URL failedUrl) {
		System.err.println("X001 Not expecting this, so not written some hndling code");
		// TODO implement this, check which username it is, and  than put it on screen
		
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#userConnectionWasSuccesfull(java.net.URL, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void userConnectionWasSuccesfull(URL sucessfullUrl, ConnectionId result) throws Exception {
		Username username = usernameContainer.getUsernameForAddress(sucessfullUrl);
		connectionsContainer.setIdForUser(username, result);
		controller.userWasConnected(username);
		messagesSender.sendIntroductoryTalkToUser(result, actorUsernameContainer.getUsername(), ourUrl);
	}
	

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#serverConnectionFailed()
	 */
	@Override
	public void serverConnectionFailed() {
		connectionsContainer.removeServerConnectionIdIfExists();
		controller.serverConnectionFailed();
	}
	

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#serverConnectionWasSuccesfull(bc.internetMessageProxy.ConnectionId)
	 */
	public void serverConnectionWasSuccesfull(ConnectionId serverConnectionId) throws Exception {
		connectionsContainer.setServerConnectionId(serverConnectionId);
		controller.serverConnectionWasSuccesfull();
		if( actorUsernameContainer.isUsernameSet() ){	
			messagesSender.sendIntroductoryRequest();
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#connectionLost(bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void connectionLost(ConnectionId id) {
		if( id == connectionsContainer.getServerConnectionId() ){
			serverConnectionFailed();
		} else {
			System.out.println("M451 connectionLost nd setting controller");
			Username lostConnectionUserUsername = connectionsContainer.getUsernameForConnectionId(id);
			controller.userConnectionLost(lostConnectionUserUsername);
			connectionsContainer.connectionLost(lostConnectionUserUsername);
		}
	}


	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#messageSentSuccesfully(bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void messageSentSuccesfully(ConnectionId id) throws Exception {
		if( connectionsContainer.isThereUserWithThisConnectionId(id) ){
			Username username =  connectionsContainer.getUsernameForConnectionId(id);
			if( pendingLettersContainer.isPendingLetterAvalible(username)){
				Letter pendingLetter = pendingLettersContainer.getPendingLetter(username);
				controller.letterWasSent(pendingLetter);
				letterContainer.addLetterOfTalkToUser(username, pendingLetter);
			}
		}
	}


	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#messageSendingFailed(bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void messageSendingFailed(ConnectionId id) {
		if( connectionsContainer.isThereUserWithThisConnectionId(id) ){
			Username username =  connectionsContainer.getUsernameForConnectionId(id);
			controller.letterSendingFailed(username);
		}
	}


	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerListener#messageWasRecieved(bc.bcCommunicator.Model.Messages.IMessage, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void messageWasRecieved(IMessage recievedMessage, ConnectionId id) {
		recievedHander.handle(recievedMessage, id);
	}
}
