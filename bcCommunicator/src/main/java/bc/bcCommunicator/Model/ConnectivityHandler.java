package bc.bcCommunicator.Model;

import java.net.URL;

import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.internetMessageProxy.ConnectionId;

public class ConnectivityHandler implements IConnectivityHandler {
	private ICommunicatorController controller;
	private URL ourUrl;
	private IConnectionsContainer connectionsContainer;
	private IOtherUsersDataContainer usernameContainer;
	private IActorUsernameContainer actorUsernameContainer;
	private IModelMessagesSender messagesSender;
	private IPendingLettersContainer pendingLettersContainer;
	

	public ConnectivityHandler(ICommunicatorController controller, URL ourUrl,
			IConnectionsContainer connectionsContainer, IOtherUsersDataContainer usernameContainer,
			IActorUsernameContainer actorUsernameContainer, IModelMessagesSender messagesSender,
			IPendingLettersContainer pendingLettersContainer) {
		this.ourUrl = ourUrl;
		this.connectionsContainer = connectionsContainer;
		this.usernameContainer = usernameContainer;
		this.actorUsernameContainer = actorUsernameContainer;
		this.messagesSender = messagesSender;
		this.controller = controller;
		this.pendingLettersContainer = pendingLettersContainer;
	}


	@Override
	public void userConnectionFailed(URL failedUrl) {
		// TODO implement this, check which username it is, and  than put it on screen
		
	}

	@Override
	public void userConnectionWasSuccesfull(URL sucessfullUrl, ConnectionId result) throws Exception {
		Username username = usernameContainer.getUsernameForAddress(sucessfullUrl);
		connectionsContainer.setIdForUser(username, result);
		controller.userWasConnected(username);
		messagesSender.sendIntroductoryTalkToUser(result, actorUsernameContainer.getUsername(), ourUrl);
	}
	

	@Override
	public void serverConnectionFailed() {
		connectionsContainer.removeServerConnectionIdIfExists();
		controller.serverConnectionFailed();
	}
	

	public void serverConnectionWasSuccesfull(ConnectionId serverConnectionId) throws Exception {
		connectionsContainer.setServerConnectionId(serverConnectionId);
		controller.serverConnectionWasSuccesfull();
		if( actorUsernameContainer.isUsernameSet() ){	
			messagesSender.sendIntroductoryRequest();
		}
	}

	@Override
	public void connectionLost(ConnectionId id) {
		if( id == connectionsContainer.getServerConnectionId() ){
			// TODO un grey out server connection button etc..
		} else {
			Username lostConnectionUserUsername = connectionsContainer.getUsernameForConnectionId(id);
			controller.userConnectionLost(lostConnectionUserUsername);
		}
	}


	@Override
	public void messageSentSuccesfully(ConnectionId id) throws Exception {
		if( connectionsContainer.isThereUserWithThisConnectionId(id) ){
			Username username =  connectionsContainer.getUsernameForConnectionId(id);
			if( pendingLettersContainer.isPendingLetterAvalible(username)){
				Letter pendingLetter = pendingLettersContainer.getPendingLetter(username);
				controller.letterWasSent(pendingLetter);
			}
		}
	}
}
