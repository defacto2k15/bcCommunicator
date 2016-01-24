package bc.bcCommunicator.Client.Help;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.driver.JTextComponentDriver;
import com.objogate.wl.swing.driver.JTextFieldDriver;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.matcher.IterableComponentsMatcher;

import bc.bcCommunicator.Client.WindowNames;
import bc.bcCommunicator.Client.Views.TalkState;
import bc.bcCommunicator.Client.Views.UserConnectionState;
import bc.bcCommunicator.Client.Views.UsernameInputStatus;
import bc.bcCommunicator.Model.BasicTypes.Username;

import static org.hamcrest.Matchers.equalTo;

import java.net.URL;

import javax.swing.JButton;
import javax.swing.JTextField;

// TODO: Auto-generated Javadoc
/**
 * The Class CommunicatorClientDriver.
 */
public class CommunicatorClientDriver extends JFrameDriver {
	
	/**
	 * Instantiates a new communicator client driver.
	 *
	 * @param timeoutMiliseconds the timeout miliseconds
	 */
	public CommunicatorClientDriver(int timeoutMiliseconds){
		super(new GesturePerformer(),
			JFrameDriver.topLevelFrame(	
				named(WindowNames.MAIN_WINDOW_NAME),
				showingOnScreen()),
			new AWTEventQueueProber(timeoutMiliseconds, 500));
	}

	/**
	 * Server connection label has status.
	 *
	 * @param expectedText the expected text
	 */
	public void ServerConnectionLabelHasStatus(String expectedText) {
		new JLabelDriver(this, named(WindowNames.SERVER_CONNECTION_STATUS_LABEL)).hasText(equalTo(expectedText));
	}

	/**
	 * Connect to server.
	 *
	 * @param url the url
	 */
	public void connectToServer(URL url) {
		getServerNameInputField().replaceAllText(url.toString());
		getServerConnectionAcceptanceButton().click();
	}
	
	/**
	 * Gets the server name input field.
	 *
	 * @return the server name input field
	 */
	private JTextFieldDriver getServerNameInputField(){
		JTextFieldDriver driver = new JTextFieldDriver(this, JTextField.class, named(WindowNames.SERVER_CONNECTION_INPUT_FIELD));
		driver.focusWithMouse();
		return driver;
	}
	
	/**
	 * Gets the server connection acceptance button.
	 *
	 * @return the server connection acceptance button
	 */
	private JButtonDriver getServerConnectionAcceptanceButton(){
		JButtonDriver driver = new JButtonDriver(this, JButton.class, named(WindowNames.SERVER_CONNECTION_ACCEPTANCE_BUTTON));
		return driver;
	}
	
	/**
	 * Gets the users table.
	 *
	 * @return the users table
	 */
	private JTableDriver getUsersTable(){
		JTableDriver driver = new JTableDriver(this, named(WindowNames.USERS_TABLE));
		return driver;
	}

	/**
	 * Write username.
	 *
	 * @param username the username
	 */
	public void writeUsername(Username username) {
		getUsernameInputField().replaceAllText(username.getName());
		getUsernameInputSubmitButton().click();
	}

	/**
	 * Gets the username input field.
	 *
	 * @return the username input field
	 */
	private JTextFieldDriver getUsernameInputField() {
		JTextFieldDriver driver = new JTextFieldDriver(this, JTextField.class, named(WindowNames.USERNAME_INPUT_TEXTFIELD));
		driver.focusWithMouse();
		return driver;
	}
	
	/**
	 * Gets the username input submit button.
	 *
	 * @return the username input submit button
	 */
	private JButtonDriver getUsernameInputSubmitButton(){
		JButtonDriver driver = new JButtonDriver(this, JButton.class, named(WindowNames.USERNAME_INPUT_ACCEPTANCE_BUTTON));
		return driver;
	}

	/**
	 * Username input label has status.
	 *
	 * @param status the status
	 */
	public void UsernameInputLabelHasStatus(UsernameInputStatus status) {
		new JLabelDriver(this, named(WindowNames.USERNAME_INPUT_STATUS_LABEL)).hasText(equalTo(status.getText()));
	}

	/**
	 * Users table has row with username.
	 *
	 * @param oneUsername the one username
	 */
	public void UsersTableHasRowWithUsername(Username oneUsername) {
		getUsersTable().hasCell(withLabelText(equalTo(oneUsername.getName())));
	}

	/**
	 * Users table has row with values.
	 *
	 * @param oneUsername the one username
	 * @param userConnectionState the user connection state
	 */
	public void usersTableHasRowWithValues(Username oneUsername, UserConnectionState userConnectionState) {
		getUsersTable().hasCell(withLabelText(equalTo(oneUsername.getName())));
		getUsersTable().hasCell(withLabelText(equalTo(userConnectionState.getStateDescription())));
	}

	/**
	 * Click on user table row with username.
	 *
	 * @param clickedUser the clicked user
	 */
	public void clickOnUserTableRowWithUsername(Username clickedUser) {
		getUsersTable().selectCell(withLabelText(equalTo(clickedUser.getName())));
	}

	/**
	 * Users table has row with values.
	 *
	 * @param username the username
	 * @param state the state
	 */
	public void usersTableHasRowWithValues(Username username, TalkState state) {
		getUsersTable().hasCell(withLabelText(state.getText()));
		getUsersTable().hasCell( withLabelText(username.getName()) );
	}

	/**
	 * User table has one row with username.
	 *
	 * @param username the username
	 */
	public void userTableHasOneRowWithUsername(Username username) {
		/* TODO implement this */
	}

//	public void thereIsWindowWithName(String string) {
//		new JFrameDriver(this, named(string)).hasTitle(string);
//	}
//	
	
}
