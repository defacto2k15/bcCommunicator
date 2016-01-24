package bc.bcCommunicator.Client.Model;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.bcCommunicator.Model.Messages.Talk.ITalk;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class ModelMessagesSender.
 */
public class ModelMessagesSender implements IModelMessagesSender{

	/** The username container. */
	private IActorUsernameContainer usernameContainer;
	
	/** The connections container. */
	private IConnectionsContainer connectionsContainer;
	
	/** The message provider. */
	private IModelMessageProvider messageProvider;
	
	/** The messager. */
	private IInternetMessager messager;
	
	/** The client url. */
	private URL clientUrl;

	/**
	 * Instantiates a new model messages sender.
	 *
	 * @param usernameContainer the username container
	 * @param connectionsContainer the connections container
	 * @param messageProvider the message provider
	 * @param messager the messager
	 * @param clientUrl the client url
	 */
	public ModelMessagesSender( IActorUsernameContainer usernameContainer, IConnectionsContainer connectionsContainer,
			 IModelMessageProvider messageProvider,
			IInternetMessager messager, URL clientUrl){
		this.usernameContainer = usernameContainer;
		this.connectionsContainer = connectionsContainer;
		this.messageProvider = messageProvider;
		this.messager = messager;
		this.clientUrl = clientUrl;
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IModelMessagesSender#sendIntroductoryRequest()
	 */
	@Override
	public void sendIntroductoryRequest() throws Exception {
		IRequest request = messageProvider.getIntroductoryRequest(usernameContainer.getUsername(), clientUrl);
		messager.sendMessage(connectionsContainer.getServerConnectionId(), request.getMessageText());
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IModelMessagesSender#sendAllUsersAddressesRequest()
	 */
	@Override
	public void sendAllUsersAddressesRequest() throws Exception {
		IRequest request = messageProvider.getAllUsersAddressesRequest();
		messager.sendMessage(connectionsContainer.getServerConnectionId(), request.getMessageText());			
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IModelMessagesSender#sendIntroductoryTalkToUser(bc.internetMessageProxy.ConnectionId, bc.bcCommunicator.Model.BasicTypes.Username, java.net.URL)
	 */
	@Override
	public void sendIntroductoryTalkToUser(ConnectionId connection, Username actorUsername, URL ourUrl) throws Exception {
		ITalk talk = messageProvider.getIntroductoryTalk(actorUsername, ourUrl);
		messager.sendMessage( connection , talk.getMessageText());
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IModelMessagesSender#sendLetterTalk(bc.bcCommunicator.Model.Messages.Letter.Letter, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void sendLetterTalk(Letter createdLetter, ConnectionId recipientConnectionId) throws Exception {
		ITalk talk = messageProvider.getLetterTalk( createdLetter.date, createdLetter.text, createdLetter.sender, createdLetter.recipient );
		messager.sendMessage(recipientConnectionId, talk.getMessageText());
	}



}
