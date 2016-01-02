package bc.bcCommunicator.Model;

import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.internetMessageProxy.ConnectionId;

public class CommunicatorModel implements ICommunicatorModel {
	private BlockingQueue<ICommunicatorModelCommand> commands = new ArrayBlockingQueue<ICommunicatorModelCommand>(10);
	private IInternetMessager messager;
	private IInternetMessagerCommandProvider commandProvider;
	private ICommunicatorController controller;
	private URL ourUrl;
	private IModelMessageProvider messageProvider;
	private IConnectionsContainer connectionsContainer;
	private IOtherUsersDataContainer usernameContainer;
	private IActorUsernameContainer actorUsernameContainer;
	private IRecievedMessagesHandler recievedHander;
	private IModelMessagesSender messagesSender;

	public CommunicatorModel(IInternetMessager messager, IInternetMessagerCommandProvider commandProvider, URL clientUrl, 
			IModelMessageProvider messageProvider, IConnectionsContainer connectionsContainer, IOtherUsersDataContainer usernameContainer
			, IRecievedMessagesHandler recievedHandler, IModelMessagesSender messagesSender,  IActorUsernameContainer actorUsernameContainer) {
		this.messager = messager;
		this.commandProvider = commandProvider;
		this.ourUrl = clientUrl;
		this.messageProvider = messageProvider;
		this.connectionsContainer = connectionsContainer;
		this.usernameContainer = usernameContainer;
		this.recievedHander = recievedHandler;
		this.messagesSender = messagesSender;
		this.actorUsernameContainer = actorUsernameContainer;
		Thread newThread = new Thread(()->{
								while(true){
									try {
										commands.take().execute(this);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
		newThread.setDaemon(true);
		newThread.start();	
	}

	public void addCommand(ICommunicatorModelCommand command) {
		commands.add(command);
	}

	public void connectToServer(URL serverAddress) {
		messager.addCommand(commandProvider.getConnectToServerCommand(serverAddress));
	}

	public void setController(ICommunicatorController controller) {	
		this.controller = controller;
	}

	public void serverConnectionWasSuccesfull(ConnectionId serverConnectionId) throws Exception {
		connectionsContainer.setServerConnectionId(serverConnectionId);
		controller.serverConnectionWasSuccesfull();
		if( actorUsernameContainer.isUsernameSet() ){	
			messagesSender.sendIntroductoryRequest();
		}
	}

	@Override
	public void serverConnectionFailed() {
		connectionsContainer.removeServerConnectionIdIfExists();
		controller.serverConnectionFailed();
	}

	@Override
	public void connectionLost(ConnectionId id) {
		// TODO IMPLEMENT WITH TESTS
	}

	@Override
	public void usernameSubmitted(Username username) throws Exception {
		actorUsernameContainer.setUsername(username);
		if( connectionsContainer.isServerConnected() ){
			messagesSender.sendIntroductoryRequest();
		}
	}

	@Override
	public void messageWasRecieved(IMessage recievedMessage, ConnectionId connectionId) {
		recievedHander.handle(recievedMessage, connectionId);
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

}
