package bc.bcCommunicator.Client.Model;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.Controller.ITalkStateDataFactory;
import bc.bcCommunicator.Client.Views.UserConnectionState;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Letter.ILetterFactory;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterSendingType;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class CommunicatorModel.
 */
public class CommunicatorModel implements ICommunicatorModel {
	
	/** The messager. */
	private IInternetMessager messager;
	
	/** The controller. */
	private ICommunicatorController controller;
	
	/** The our url. */
	private URL ourUrl;
	
	/** The message provider. */
	private IModelMessageProvider messageProvider;
	
	/** The connections container. */
	private IConnectionsContainer connectionsContainer;
	
	/** The username container. */
	private IOtherUsersDataContainer usernameContainer;
	
	/** The actor username container. */
	private IActorUsernameContainer actorUsernameContainer;
	
	/** The recieved hander. */
	private IRecievedMessagesHandler recievedHander;
	
	/** The messages sender. */
	private IModelMessagesSender messagesSender;
	
	/** The talk state data factory. */
	private ITalkStateDataFactory talkStateDataFactory;
	
	/** The letter factory. */
	private ILetterFactory letterFactory;
	
	/** The letter container. */
	private ILetterContainer letterContainer;
	
	/** The pending letters container. */
	private IPendingLettersContainer pendingLettersContainer;

	/**
	 * Instantiates a new communicator model.
	 *
	 * @param messager the messager
	 * @param clientUrl the client url
	 * @param messageProvider the message provider
	 * @param connectionsContainer the connections container
	 * @param usernameContainer the username container
	 * @param messagesSender the messages sender
	 * @param actorUsernameContainer the actor username container
	 * @param controller the controller
	 * @param talkStateDataFactory the talk state data factory
	 * @param letterFactory the letter factory
	 * @param letterContainer the letter container
	 * @param pendingLettersContainer the pending letters container
	 */
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

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.ICommunicatorModel#connectToServer(java.net.URL)
	 */
	public void connectToServer(URL serverAddress) {
		try {
			messager.connectToServer(serverAddress);
		} catch (Exception e) {
			System.err.println("E540");
			e.printStackTrace();
		}
	}


	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.ICommunicatorModel#usernameSubmitted(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void usernameSubmitted(Username username) throws Exception {
		actorUsernameContainer.setUsername(username);
		if( connectionsContainer.isServerConnected() ){
			messagesSender.sendIntroductoryRequest();
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.ICommunicatorModel#getTalkStateData(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void getTalkStateData(Username username) throws ParseException {
		if( connectionsContainer.isUserConnected(username)){
			controller.talkStateChanged( talkStateDataFactory.generate(username, 
					letterContainer.getLettersOfTalkToUser(username), UserConnectionState.Connected));
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.ICommunicatorModel#letterWasWritten(java.lang.String, bc.bcCommunicator.Model.BasicTypes.Username)
	 */
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
