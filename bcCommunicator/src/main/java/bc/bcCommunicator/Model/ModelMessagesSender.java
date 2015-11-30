package bc.bcCommunicator.Model;

import java.net.URL;

import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.IRequest;

public class ModelMessagesSender implements IModelMessagesSender{

	private IUsernameContainer usernameContainer;
	private IConnectionsContainer connectionsContainer;
	private IInternetMessagerCommandProvider commandProvider;
	private IModelMessageProvider messageProvider;
	private IInternetMessager messager;
	private URL clientUrl;

	public ModelMessagesSender( IUsernameContainer usernameContainer, IConnectionsContainer connectionsContainer,
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
		AllUsersAddresses allUsersAddresses =  usernameContainer.getUsernamesWithAddresses();
		IRequest request = messageProvider.getAllUsersAddressesRequest(allUsersAddresses);
		messager.addCommand(commandProvider.getSendMessageCommand(connectionsContainer.getServerConnectionId(), request));		
	}

}
