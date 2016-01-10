package Controller;

import java.text.ParseException;
import java.util.List;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

public class CommunicatorControllerContainer implements ICommunicatorController {
	ICommunicatorController realController;
	

	public void setRealController(ICommunicatorController realController) {
		this.realController = realController;
	}

	@Override
	public void serverConnectionWasSuccesfull() {
		realController.serverConnectionWasSuccesfull();
	}

	@Override
	public void serverConnectionFailed() {
		realController.serverConnectionFailed();
	}

	@Override
	public void serverConnectionAcceptanceButtonWasClicked() {
		realController.serverConnectionAcceptanceButtonWasClicked();
	}

	@Override
	public void setViewHandlers() {
		realController.setViewHandlers();
	}

	@Override
	public void usernameInputSubmitButtonWasClicked() {
		realController.usernameInputSubmitButtonWasClicked();
	}

	@Override
	public void setBulkUsers(List<Username> usernames) {
		realController.setBulkUsers(usernames);
	}

	@Override
	public void userWasConnected(Username username) {
		realController.userWasConnected(username);
	}

	@Override
	public void userConnectionLost(Username someUserUsername) {
		realController.userConnectionLost(someUserUsername);
	}

	@Override
	public void newUserConnected(Username username) {
		realController.newUserConnected(username);
	}

	@Override
	public void rowInUserTableWasClicked(Username username) {
		realController.rowInUserTableWasClicked(username);
	}

	@Override
	public void talkStateChanged(TalkStateData stateData) throws ParseException {
		realController.talkStateChanged(stateData);
	}

	@Override
	public void recievedNewLetter(Letter letter) throws ParseException {
		realController.recievedNewLetter(letter);
	}

	@Override
	public void letterWasWritten(Username username, String text) {
		realController.letterWasWritten(username, text);
	}

	@Override
	public void letterWasSent(Letter letter) {
		realController.letterWasSent(letter);
	}

}
