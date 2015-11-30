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
import bc.bcCommunicator.Model.Messages.IRequest;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.internetMessageProxy.ConnectionId;

public class CommunicatorModel implements ICommunicatorModel {
	private BlockingQueue<ICommunicatorModelCommand> commands = new ArrayBlockingQueue<ICommunicatorModelCommand>(10);
	private IInternetMessager messager;
	private IInternetMessagerCommandProvider commandProvider;
	private ICommunicatorController controller;
	private URL ourUrl;
	private IModelMessageProvider messageProvider;
	private IConnectionsContainer connectionsContainer;
	private IUsernameContainer usernameContainer;
	private IRecievedMessagesHandler recievedHander;
	private IModelMessagesSender messagesSender;

	public CommunicatorModel(IInternetMessager messager, IInternetMessagerCommandProvider commandProvider, URL clientUrl, 
			IModelMessageProvider messageProvider, IConnectionsContainer connectionsContainer, IUsernameContainer usernameContainer
			, IRecievedMessagesHandler recievedHandler, IModelMessagesSender messagesSender) {
		this.messager = messager;
		this.commandProvider = commandProvider;
		this.ourUrl = clientUrl;
		this.messageProvider = messageProvider;
		this.connectionsContainer = connectionsContainer;
		this.usernameContainer = usernameContainer;
		this.recievedHander = recievedHandler;
		this.messagesSender = messagesSender;
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
		if( usernameContainer.isUsernameSet() ){	
			messagesSender.sendIntroductoryRequest();
			/*IRequest request = messageProvider.getIntroductoryRequest(usernameContainer.getUsername(), ourUrl);
			messager.addCommand(commandProvider.getSendMessageCommand(serverConnectionId, request)); TODO delete*/
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
		usernameContainer.setUsername(username);
		if( connectionsContainer.isServerConnected() ){
			messagesSender.sendIntroductoryRequest();
			/*IRequest request = messageProvider.getIntroductoryRequest(username, ourUrl);
			messager.addCommand(commandProvider.getSendMessageCommand(connectionsContainer.getServerConnectionId(), request)); TODO delete this*/
		}
	}

	@Override
	public void messageWasRecieved(IMessage recievedMessage, ConnectionId connectionId) {
		recievedHander.handle(recievedMessage, connectionId);
	}

}
