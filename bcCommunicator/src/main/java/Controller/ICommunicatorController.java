package Controller;

import java.net.MalformedURLException;
import java.net.URL;

import bc.bcCommunicator.Views.ServerConnectionStatus;
import bc.internetMessageProxy.ConnectionId;

public interface ICommunicatorController {

	void serverConnectionWasSuccesfull();

	void serverConnectionFailed();

	void serverConnectionAcceptanceButtonWasClicked();

	void setViewHandlers();
	
	void usernameInputSubmitButtonWasClicked();

}
