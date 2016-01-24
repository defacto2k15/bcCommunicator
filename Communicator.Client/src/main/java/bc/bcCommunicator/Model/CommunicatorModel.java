package bc.bcCommunicator.Model;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.bcCommunicator.Controller.ITalkStateDataFactory;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Letter.ILetterFactory;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterSendingType;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.bcCommunicator.Views.UserConnectionState;
import bc.internetMessageProxy.ConnectionId;

public class CommunicatorModel implements ICommunicatorModel {
	private IInternetMessager messager;
	private ICommunicatorController controller;
	private URL ourUrl;
	private IModelMessageProvider messageProvider;
	private IConnectionsContainer connectionsContainer;
	private IOtherUsersDataContainer usernameContainer;
	private IActorUsernameContainer actorUsernameContainer;
	private IRecievedMessagesHandler recievedHander;
	private IModelMessagesSender messagesSender;
	private ITalkStateDataFactory talkStateDataFactory;
	private ILetterFactory letterFactory;
	private ILetterContainer letterContainer;
	private IPendingLettersContainer pendingLettersContainer;

	public CommunicatorModel(IInternetMessager messager, URL clientUrl, 
			IModelMessageProvider messageProvider, IConnectionsContainer connectionsContainer, IOtherUsersDataContainer usernameContainer
			, IModelMessagesSender messagesSender
			, IActorUsernameContainer actorUsernameContainer
			, ICommunicatorController controller
			, ITalkStateDataFactory talkStateDataFactory, ILetterFactory letterFactory, ILetterContainer letterContainer, IPendingLettersContainer pendingLettersContainer) {
		this.messager = messager;
		this.ourUrl = clientUrl;
		this.messageProvider = messageProvider;
		this.connectionsContainer = connectionsContainer;
		this.usernameContainer = usernameContainer;
		this.messagesSender = messagesSender;
		this.actorUsernameContainer = actorUsernameContainer;
		this.controller = controller;
		this.talkStateDataFactory = talkStateDataFactory;
		this.letterFactory = letterFactory;
		this.letterContainer = letterContainer;
		this.pendingLettersContainer = pendingLettersContainer;
	}

	public void connectToServer(URL serverAddress) {
		try {
			messager.connectToServer(serverAddress);
		} catch (Exception e) {
			System.err.println("E540");
			e.printStackTrace();
		}
	}


	@Override
	public void usernameSubmitted(Username username) throws Exception {
		actorUsernameContainer.setUsername(username);
		if( connectionsContainer.isServerConnected() ){
			messagesSender.sendIntroductoryRequest();
		}
	}

	@Override
	public void getTalkStateData(Username username) throws ParseException {
		if( connectionsContainer.isUserConnected(username)){
			controller.talkStateChanged( talkStateDataFactory.generate(username, 
					letterContainer.getLettersOfTalkToUser(username), UserConnectionState.Connected));
		}
	}

	@Override
	public void letterWasWritten(String letterText, Username recipient) throws Exception {
		System.out.println("M452 letter was written");
		if( connectionsContainer.isUserConnected(recipient)){
			System.out.println("M453 user connected to passign letter");
			ConnectionId recipientId = connectionsContainer.getConnectionIdOfUser(recipient);
			Letter toSend = letterFactory.create(new LetterText(letterText), new LetterDate(new Date()), 
					actorUsernameContainer.getUsername(), recipient, LetterSendingType.Sent);
			System.out.println("M651 Sending letter ");
			messagesSender.sendLetterTalk(toSend, recipientId);
			pendingLettersContainer.addPendingLetter(recipient, toSend);
		} else {
			System.out.println("M454 user not connected");
			controller.letterSendingFailed(recipient);
		}
	}
}
