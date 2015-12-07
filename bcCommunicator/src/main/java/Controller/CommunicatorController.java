package Controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import bc.bcCommunicator.Model.CommunicatorModel;
import bc.bcCommunicator.Model.ICommunicatorModel;
import bc.bcCommunicator.Model.ICommunicatorModelCommandsProvider;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Views.IServerConnectionStatusView;
import bc.bcCommunicator.Views.IUsernameInputView;
import bc.bcCommunicator.Views.IUsersTableView;
import bc.bcCommunicator.Views.ServerConnectionStatus;
import bc.bcCommunicator.Views.UserConnectionState;
import bc.bcCommunicator.Views.UsernameInputStatus;
import bc.bcCommunicator.Views.UsersTableView;

public class CommunicatorController implements ICommunicatorController {
	private IServerConnectionStatusView connectionStatusView;
	private ICommunicatorModelCommandsProvider commandsProvider;
	private ICommunicatorModel model;
	private IUsernameInputView usernameInputView;
	private IUsersTableView usersTableView;


	public CommunicatorController(IServerConnectionStatusView connectionStatusView, 
			ICommunicatorModel model, ICommunicatorModelCommandsProvider commandsProvider, 
			IUsernameInputView usernameInputView, IUsersTableView usersTableView ){
		this.connectionStatusView = connectionStatusView;
		this.model = model;
		this.commandsProvider = commandsProvider;
		this.usernameInputView = usernameInputView;
		this.usersTableView = usersTableView;

	}
	
	@Override
	public void setViewHandlers(){
		this.connectionStatusView.setServerConnectionAcceptanceButtonWasClickedHandler(
				()->{this.serverConnectionAcceptanceButtonWasClicked();});	
		this.usernameInputView.setUsernameSubmitButtonWasClickedHandler(
				()->{this.usernameInputSubmitButtonWasClicked();});
	}

	@Override
	public void serverConnectionAcceptanceButtonWasClicked() {
		String serverAddressText = connectionStatusView.getServerAddress();
		URL serverAddress;
		try {
			serverAddress = new URL(serverAddressText);
		} catch (MalformedURLException e) {
			connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.ErrorMalformedUrl);
			return;
		}
		model.addCommand(commandsProvider.getConnectServerCommand(serverAddress));
	}

	@Override
	public void serverConnectionWasSuccesfull() {
		connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.Connected);
	}
	
	@Override
	public void serverConnectionFailed() {
		connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.ConnectionFailed);
		
	}

	@Override
	public void usernameInputSubmitButtonWasClicked() {
		String usernameText = usernameInputView.getUsernameText();
		if(usernameText.equals("")){
			usernameInputView.setUsernameInputStatus(UsernameInputStatus.UsernameEmpty);
		} else {
			model.addCommand(commandsProvider.getUsernameSubmittedCommand(new Username(usernameText)));
		}
	}

	@Override
	public void setBulkUsers(List<Username> usernames) {
		usersTableView.clearTable();
		for( Username oneName : usernames){
			usersTableView.addLineToTable(oneName, UserConnectionState.NotConnected);
		}
	}
}
