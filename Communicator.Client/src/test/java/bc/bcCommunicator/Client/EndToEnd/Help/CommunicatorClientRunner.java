package bc.bcCommunicator.Client.EndToEnd.Help;

import java.net.MalformedURLException;
import java.net.URL;

import bc.bcCommunicator.Client.Main;
import bc.bcCommunicator.Client.WindowNames;
import bc.bcCommunicator.Client.Views.LetterState;
import bc.bcCommunicator.Client.Views.ServerConnectionStatus;
import bc.bcCommunicator.Client.Views.TalkState;
import bc.bcCommunicator.Client.Views.UserConnectionState;
import bc.bcCommunicator.Client.Views.UsernameInputStatus;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;

// TODO: Auto-generated Javadoc
/**
 * The Class CommunicatorClientRunner.
 */
public class CommunicatorClientRunner {
	
	/** The driver. */
	private CommunicatorClientDriver driver ;
	
	/** The talk window containers. */
	private TalkWindowDriverContainer talkWindowContainers = new TalkWindowDriverContainer();
	
	/**
	 * Start.
	 *
	 * @param portOfClient the port of client
	 * @throws MalformedURLException the malformed url exception
	 */
	public void start(int portOfClient) throws MalformedURLException {
		Thread thread = new Thread("Communicator app"){
			@Override public void run(){
				try{
					String[] inputs = {new Integer(portOfClient).toString()};
					Main.main(inputs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
		driver = new CommunicatorClientDriver(1000);
	}

	/**
	 * Connect to server.
	 *
	 * @param url the url
	 */
	public void connectToServer(URL url) {
		driver.connectToServer(url);
	}

	/**
	 * Assert is not connected to server.
	 */
	public void assertIsNotConnectedToServer() {
		driver.ServerConnectionLabelHasStatus(ServerConnectionStatus.NotConnected.getText());
	}

	/**
	 * Stop.
	 */
	public void stop() {
		// TODO Auto-generated method stub
	}

	/**
	 * Assert connection to server failed.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void assertConnectionToServerFailed() throws InterruptedException {
		Thread.sleep(150);
		driver.ServerConnectionLabelHasStatus(ServerConnectionStatus.ConnectionFailed.getText());
	}

	/**
	 * Assert connection to server succeded.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void assertConnectionToServerSucceded() throws InterruptedException {
		Thread.sleep(150);
		driver.ServerConnectionLabelHasStatus(ServerConnectionStatus.Connected.getText());
	}

	/**
	 * Insert username.
	 *
	 * @param username the username
	 */
	public void insertUsername(Username username) {
		driver.writeUsername(username); 
	}

	/**
	 * Assert username was accepted.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void assertUsernameWasAccepted() throws InterruptedException {
		Thread.sleep(150); // TODO uwga na czas debugowania sen na 90 sek
		driver.UsernameInputLabelHasStatus(UsernameInputStatus.UsernameOk);
	}

	/**
	 * Assert has user in users table.
	 *
	 * @param oneUsername the one username
	 * @throws InterruptedException the interrupted exception
	 */
	public void assertHasUserInUsersTable(Username oneUsername) throws InterruptedException {
		Thread.sleep(150);
		driver.UsersTableHasRowWithUsername(oneUsername);
	}

	/**
	 * Assert user has connection state.
	 *
	 * @param oneUsername the one username
	 * @param userConnectionState the user connection state
	 * @throws InterruptedException the interrupted exception
	 */
	public void assertUserHasConnectionState(Username oneUsername, UserConnectionState userConnectionState) throws InterruptedException {
		driver.usersTableHasRowWithValues(oneUsername, userConnectionState);
	}

	/**
	 * Click on user table row.
	 *
	 * @param clickedUser the clicked user
	 */
	public void clickOnUserTableRow(Username clickedUser) {
		driver.clickOnUserTableRowWithUsername(clickedUser);
	}

	/**
	 * Assert has talk window for user.
	 *
	 * @param clickedUser the clicked user
	 */
	public void assertHasTalkWindowForUser(Username clickedUser) {
		talkWindowContainers.getDriver(clickedUser).assertHasTitle(WindowNames.TALK_WINDOW_PREFIX+clickedUser.getName());
	}

	/**
	 * Assert talk window has user connection state.
	 *
	 * @param username the username
	 * @param state the state
	 */
	public void assertTalkWindowHasUserConnectionState(Username username, UserConnectionState state) {
		talkWindowContainers.getDriver(username).assertHasConnectionState( state );
	}

	/**
	 * Assert talk window has username set.
	 *
	 * @param username the username
	 */
	public void assertTalkWindowHasUsernameSet(Username username) {
		talkWindowContainers.getDriver(username).assertHasTalkUserUsername( username );
	}

	/**
	 * Assert talk window has letter state set.
	 *
	 * @param username the username
	 * @param state the state
	 */
	public void assertTalkWindowHasLetterStateSet(Username username, LetterState state) {
		talkWindowContainers.getDriver(username).assertHasLetterStateLabel(state);
	}

	/**
	 * User talk has new message in table.
	 *
	 * @param username the username
	 */
	public void userTalkHasNewMessageInTable(Username username) {
		driver.usersTableHasRowWithValues(username, TalkState.NewMessage);
	}

	/**
	 * Talk windows has letter.
	 *
	 * @param username the username
	 * @param letterText the letter text
	 */
	public void talkWindowsHasLetter(Username username, LetterText letterText) {
		talkWindowContainers.getDriver(username).assertHasLetterViewWithText( letterText);
		
	}

	/**
	 * Write letter text to input field.
	 *
	 * @param username the username
	 * @param letterText the letter text
	 */
	public void writeLetterTextToInputField(Username username, LetterText letterText) {
		talkWindowContainers.getDriver(username).writeLetterInput(letterText);
	}

	/**
	 * Click send button.
	 *
	 * @param username the username
	 */
	public void clickSendButton(Username username) {
		talkWindowContainers.getDriver(username).clickSendButton(username);
	}

	/**
	 * Assert talk window has empty input field.
	 *
	 * @param username the username
	 */
	public void assertTalkWindowHasEmptyInputField(Username username) {
		talkWindowContainers.getDriver(username).assertLetterInputHasText("");
	}

	/**
	 * Close talk window.
	 *
	 * @param username the username
	 */
	public void closeTalkWindow(Username username) {
		talkWindowContainers.getDriver(username).dispose();
	}

	/**
	 * Assert there is one user in table.
	 *
	 * @param username the username
	 */
	public void assertThereIsOneUserInTable(Username username) {
		driver.userTableHasOneRowWithUsername( username );
	}

	public void assertUsernameWasNotAccepted() throws InterruptedException {
		Thread.sleep(150); // TODO uwga na czas debugowania sen na 90 sek
		driver.UsernameInputLabelHasStatus(UsernameInputStatus.UsernameBad);
	}

}
