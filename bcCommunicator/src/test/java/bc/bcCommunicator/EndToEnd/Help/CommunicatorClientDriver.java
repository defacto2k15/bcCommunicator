package bc.bcCommunicator.EndToEnd.Help;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.driver.JTextComponentDriver;
import com.objogate.wl.swing.driver.JTextFieldDriver;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;
import com.objogate.wl.swing.gesture.GesturePerformer;

import bc.bcCommunicator.WindowNames;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Views.UserConnectionState;
import bc.bcCommunicator.Views.UsernameInputStatus;

import static org.hamcrest.Matchers.equalTo;

import java.net.URL;

import javax.swing.JButton;
import javax.swing.JTextField;

public class CommunicatorClientDriver extends JFrameDriver {
	public CommunicatorClientDriver(int timeoutMiliseconds){
		super(new GesturePerformer(),
			JFrameDriver.topLevelFrame(	
				named(WindowNames.MAIN_WINDOW_NAME),
				showingOnScreen()),
			new AWTEventQueueProber(timeoutMiliseconds, 500));
	}

	public void ServerConnectionLabelHasStatus(String expectedText) {
		new JLabelDriver(this, named(WindowNames.SERVER_CONNECTION_STATUS_LABEL)).hasText(equalTo(expectedText));
	}

	public void connectToServer(URL url) {
		getServerNameInputField().replaceAllText(url.toString());
		getServerConnectionAcceptanceButton().click();
	}
	
	private JTextFieldDriver getServerNameInputField(){
		JTextFieldDriver driver = new JTextFieldDriver(this, JTextField.class, named(WindowNames.SERVER_CONNECTION_INPUT_FIELD));
		driver.focusWithMouse();
		return driver;
	}
	
	private JButtonDriver getServerConnectionAcceptanceButton(){
		JButtonDriver driver = new JButtonDriver(this, JButton.class, named(WindowNames.SERVER_CONNECTION_ACCEPTANCE_BUTTON));
		return driver;
	}

	public void writeUsername(Username username) {
		getUsernameInputField().replaceAllText(username.getName());
		getUsernameInputSubmitButton().click();
	}

	private JTextFieldDriver getUsernameInputField() {
		JTextFieldDriver driver = new JTextFieldDriver(this, JTextField.class, named(WindowNames.USERNAME_INPUT_TEXTFIELD));
		driver.focusWithMouse();
		return driver;
	}
	
	private JButtonDriver getUsernameInputSubmitButton(){
		JButtonDriver driver = new JButtonDriver(this, JButton.class, named(WindowNames.USERNAME_INPUT_ACCEPTANCE_BUTTON));
		return driver;
	}

	public void UsernameInputLabelHasStatus(UsernameInputStatus status) {
		new JLabelDriver(this, named(WindowNames.USERNAME_INPUT_STATUS_LABEL)).hasText(equalTo(status.getText()));
	}

	public void UsersTableHasRowWithUsername(Username oneUsername) {
		new JTableDriver(this, named(WindowNames.USERS_TABLE)).hasCell(withLabelText(equalTo(oneUsername.getName())));
	}

	public void usersTableHasRowWithValues(Username oneUsername, UserConnectionState userConnectionState) {
		new JTableDriver(this, named(WindowNames.USERS_TABLE)).hasCell(withLabelText(equalTo(oneUsername.getName())));
		new JTableDriver(this, named(WindowNames.USERS_TABLE)).hasCell(withLabelText(equalTo(userConnectionState.getStateDescription())));
				//matching(withLabelText(equalTo(oneUsername.getName())));
	}
	
	
}