package Controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Views.ServerConnectionStatus;
import bc.internetMessageProxy.ConnectionId;

public interface ICommunicatorController {

	void serverConnectionWasSuccesfull();

	void serverConnectionFailed();

	void serverConnectionAcceptanceButtonWasClicked();

	void setViewHandlers();
	
	void usernameInputSubmitButtonWasClicked();

	void setBulkUsers(List<Username> usernames);

	void userWasConnected(Username username);

	void userConnectionLost(Username someUserUsername);

}
