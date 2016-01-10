package bc.bcCommunicator.Model;

import java.net.URL;
import java.util.Map;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.bcCommunicator.Model.Messages.Talk.ITalk;
import bc.internetMessageProxy.ConnectionId;

public class ModelMessagesSender implements IModelMessagesSender{

	private IActorUsernameContainer usernameContainer;
	private IConnectionsContainer connectionsContainer;
	private IInternetMessagerCommandProvider commandProvider;
	private IModelMessageProvider messageProvider;
	private IInternetMessager messager;
	private URL clientUrl;

	public ModelMessagesSender( IActorUsernameContainer usernameContainer, IConnectionsContainer connectionsContainer,
			IInternetMessagerCommandProvider commandProvider, IModelMessageProvider messageProvider,
			IInternetMessager messager, URL clientUrl){
		this.usernameContainer = usernameContainer;
		this.connectionsContainer = connectionsContainer;
		this.commandProvider = commandProvider;
		this.messageProvider = messageProvider;
		this.messager = messager;
		this.clientUrl = clientUrl;
	}
	
	@Override
	public void sendIntroductoryRequest() throws Exception {
		IRequest request = messageProvider.getIntroductoryRequest(usernameContainer.getUsername(), clientUrl);
		messager.addCommand(commandProvider.getSendMessageCommand(connectionsContainer.getServerConnectionId(), request));
	}

	@Override
	public void sendAllUsersAddressesRequest() throws Exception {
		IRequest request = messageProvider.getAllUsersAddressesRequest();
		messager.addCommand(commandProvider.getSendMessageCommand(connectionsContainer.getServerConnectionId(), request));			
	}

	@Override
	public void sendIntroductoryTalkToUser(ConnectionId connection, Username actorUsername, URL ourUrl) throws Exception {
		ITalk talk = messageProvider.getIntroductoryTalk(actorUsername, ourUrl);
		messager.addCommand( commandProvider.getSendMessageCommand( connection , talk));
	}

	@Override
	public void sendLetterTalk(Letter createdLetter, ConnectionId recipientConnectionId) throws Exception {
		ITalk talk = messageProvider.getLetterTalk( createdLetter.date, createdLetter.text, createdLetter.sender, createdLetter.recipient );
		messager.addCommand( commandProvider.getSendMessageCommand(recipientConnectionId, talk));
	}



}
