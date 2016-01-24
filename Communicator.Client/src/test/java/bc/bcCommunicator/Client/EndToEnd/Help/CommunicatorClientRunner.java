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

public class CommunicatorClientRunner {
	private CommunicatorClientDriver driver ;
	private TalkWindowDriverContainer talkWindowContainers = new TalkWindowDriverContainer();
	
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

	public void connectToServer(URL url) {
		driver.connectToServer(url);
	}

	public void assertIsNotConnectedToServer() {
		driver.ServerConnectionLabelHasStatus(ServerConnectionStatus.NotConnected.getText());
	}

	public void stop() {
		// TODO Auto-generated method stub
	}

	public void assertConnectionToServerFailed() throws InterruptedException {
		Thread.sleep(150);
		driver.ServerConnectionLabelHasStatus(ServerConnectionStatus.ConnectionFailed.getText());
		
	}

	public void assertConnectionToServerSucceded() throws InterruptedException {
		Thread.sleep(150);
		driver.ServerConnectionLabelHasStatus(ServerConnectionStatus.Connected.getText());
	}

	public void insertUsername(Username username) {
		driver.writeUsername(username); 
	}

	public void assertUsernameWasAccepted() throws InterruptedException {
		Thread.sleep(150); // TODO uwga na czas debugowania sen na 90 sek
		driver.UsernameInputLabelHasStatus(UsernameInputStatus.UsernameOk);
	}

	public void assertHasUserInUsersTable(Username oneUsername) throws InterruptedException {
		Thread.sleep(150);
		driver.UsersTableHasRowWithUsername(oneUsername);
	}

	public void assertUserHasConnectionState(Username oneUsername, UserConnectionState userConnectionState) throws InterruptedException {
		driver.usersTableHasRowWithValues(oneUsername, userConnectionState);
	}

	public void clickOnUserTableRow(Username clickedUser) {
		driver.clickOnUserTableRowWithUsername(clickedUser);
	}

	public void assertHasTalkWindowForUser(Username clickedUser) {
		talkWindowContainers.getDriver(clickedUser).assertHasTitle(WindowNames.TALK_WINDOW_PREFIX+clickedUser.getName());
	}

	public void assertTalkWindowHasUserConnectionState(Username username, UserConnectionState state) {
		talkWindowContainers.getDriver(username).assertHasConnectionState( state );
	}

	public void assertTalkWindowHasUsernameSet(Username username) {
		talkWindowContainers.getDriver(username).assertHasTalkUserUsername( username );
	}

	public void assertTalkWindowHasLetterStateSet(Username username, LetterState state) {
		talkWindowContainers.getDriver(username).assertHasLetterStateLabel(state);
	}

	public void userTalkHasNewMessageInTable(Username username) {
		driver.usersTableHasRowWithValues(username, TalkState.NewMessage);
	}

	public void talkWindowsHasLetter(Username username, LetterText letterText) {
		talkWindowContainers.getDriver(username).assertHasLetterViewWithText( letterText);
		
	}

	public void writeLetterTextToInputField(Username username, LetterText letterText) {
		talkWindowContainers.getDriver(username).writeLetterInput(letterText);
	}

	public void clickSendButton(Username username) {
		talkWindowContainers.getDriver(username).clickSendButton(username);
	}

	public void assertTalkWindowHasEmptyInputField(Username username) {
		talkWindowContainers.getDriver(username).assertLetterInputHasText("");
	}

	public void closeTalkWindow(Username username) {
		talkWindowContainers.getDriver(username).dispose();
	}

	public void assertThereIsOneUserInTable(Username username) {
		driver.userTableHasOneRowWithUsername( username );
	}

}
