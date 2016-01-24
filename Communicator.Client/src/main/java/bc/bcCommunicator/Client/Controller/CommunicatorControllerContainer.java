package bc.bcCommunicator.Client.Controller;

import java.text.ParseException;
import java.util.List;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

// TODO: Auto-generated Javadoc
/**
 * The Class CommunicatorControllerContainer.
 */
public class CommunicatorControllerContainer implements ICommunicatorController {
	
	/** The real controller. */
	ICommunicatorController realController;
	

	/**
	 * Sets the real controller.
	 *
	 * @param realController the new real controller
	 */
	public void setRealController(ICommunicatorController realController) {
		this.realController = realController;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#serverConnectionWasSuccesfull()
	 */
	@Override
	public void serverConnectionWasSuccesfull() {
		realController.serverConnectionWasSuccesfull();
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#serverConnectionFailed()
	 */
	@Override
	public void serverConnectionFailed() {
		realController.serverConnectionFailed();
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#serverConnectionAcceptanceButtonWasClicked()
	 */
	@Override
	public void serverConnectionAcceptanceButtonWasClicked() {
		realController.serverConnectionAcceptanceButtonWasClicked();
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#setViewHandlers()
	 */
	@Override
	public void setViewHandlers() {
		realController.setViewHandlers();
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#usernameInputSubmitButtonWasClicked()
	 */
	@Override
	public void usernameInputSubmitButtonWasClicked() {
		try {
			realController.usernameInputSubmitButtonWasClicked();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#setBulkUsers(java.util.List)
	 */
	@Override
	public void setBulkUsers(List<Username> usernames) {
		realController.setBulkUsers(usernames);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#userWasConnected(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void userWasConnected(Username username) {
		realController.userWasConnected(username);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#userConnectionLost(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void userConnectionLost(Username someUserUsername) {
		realController.userConnectionLost(someUserUsername);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#newUserConnected(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void newUserConnected(Username username) {
		realController.newUserConnected(username);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#rowInUserTableWasClicked(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void rowInUserTableWasClicked(Username username) {
		try {
			realController.rowInUserTableWasClicked(username);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#talkStateChanged(bc.bcCommunicator.Client.Controller.TalkStateData)
	 */
	@Override
	public void talkStateChanged(TalkStateData stateData) throws ParseException {
		realController.talkStateChanged(stateData);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#recievedNewLetter(bc.bcCommunicator.Model.Messages.Letter.Letter)
	 */
	@Override
	public void recievedNewLetter(Letter letter) throws ParseException {
		realController.recievedNewLetter(letter);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#letterWasWritten(bc.bcCommunicator.Model.BasicTypes.Username, java.lang.String)
	 */
	@Override
	public void letterWasWritten(Username username, String text) {
		try {
			realController.letterWasWritten(username, text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#letterWasSent(bc.bcCommunicator.Model.Messages.Letter.Letter)
	 */
	@Override
	public void letterWasSent(Letter letter) throws ParseException {
		realController.letterWasSent(letter);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#letterSendingFailed(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void letterSendingFailed(Username talkingUsername) {
		realController.letterSendingFailed(talkingUsername);
		
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#usernameWasOk()
	 */
	@Override
	public void usernameWasOk() {
		// TODO Auto-generated method stub
		System.err.println("NOT EXPECTED");
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#usernameWasBad()
	 */
	@Override
	public void usernameWasBad() {
		// TODO Auto-generated method stub
		System.err.println("NOT EXPECTED");
	}

}
