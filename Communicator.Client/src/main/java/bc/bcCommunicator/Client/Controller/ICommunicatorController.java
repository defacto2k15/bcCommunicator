package bc.bcCommunicator.Client.Controller;

import java.text.ParseException;
import java.util.List;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

// TODO: Auto-generated Javadoc
/**
 * The Interface ICommunicatorController.
 */
public interface ICommunicatorController {

	/**
	 * Server connection was succesfull.
	 */
	void serverConnectionWasSuccesfull();

	/**
	 * Server connection failed.
	 */
	void serverConnectionFailed();

	/**
	 * Server connection acceptance button was clicked.
	 */
	void serverConnectionAcceptanceButtonWasClicked();

	/**
	 * Sets the view handlers.
	 */
	void setViewHandlers();
	
	/**
	 * Username input submit button was clicked.
	 *
	 * @throws Exception the exception
	 */
	void usernameInputSubmitButtonWasClicked() throws Exception;

	/**
	 * Sets the bulk users.
	 *
	 * @param usernames the new bulk users
	 */
	void setBulkUsers(List<Username> usernames);

	/**
	 * User was connected.
	 *
	 * @param username the username
	 */
	void userWasConnected(Username username);

	/**
	 * User connection lost.
	 *
	 * @param someUserUsername the some user username
	 */
	void userConnectionLost(Username someUserUsername);

	/**
	 * New user connected.
	 *
	 * @param username the username
	 */
	void newUserConnected(Username username);

	/**
	 * Row in user table was clicked.
	 *
	 * @param username the username
	 * @throws ParseException the parse exception
	 */
	void rowInUserTableWasClicked(Username username) throws ParseException;

	/**
	 * Talk state changed.
	 *
	 * @param stateData the state data
	 * @throws ParseException the parse exception
	 */
	void talkStateChanged(TalkStateData stateData) throws ParseException;

	/**
	 * Recieved new letter.
	 *
	 * @param letter the letter
	 * @throws ParseException the parse exception
	 */
	void recievedNewLetter(Letter letter) throws ParseException;

	/**
	 * Letter was written.
	 *
	 * @param username the username
	 * @param text the text
	 * @throws Exception the exception
	 */
	void letterWasWritten(Username username, String text) throws Exception;

	/**
	 * Letter was sent.
	 *
	 * @param letter the letter
	 * @throws ParseException the parse exception
	 */
	void letterWasSent(Letter letter) throws ParseException;

	/**
	 * Letter sending failed.
	 *
	 * @param talkingUsername the talking username
	 */
	void letterSendingFailed(Username talkingUsername);

	/**
	 * Username was ok.
	 */
	void usernameWasOk();

	/**
	 * Username was bad.
	 */
	void usernameWasBad();

}
