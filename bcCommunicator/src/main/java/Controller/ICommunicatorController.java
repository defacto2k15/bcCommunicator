package Controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
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

	void newUserConnected(Username username);

	void rowInUserTableWasClicked(Username username);

	void talkStateChanged(TalkStateData stateData) throws ParseException;

	void recievedNewLetter(Letter letter) throws ParseException;

	void letterWasWritten(Username username, String text);

	void letterWasSent(Letter letter);

}
