package bc.bcCommunicator.Client.Help;

import static org.hamcrest.Matchers.equalTo;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.driver.JTextFieldDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import bc.bcCommunicator.Client.WindowNames;
import bc.bcCommunicator.Client.Views.LetterState;
import bc.bcCommunicator.Client.Views.LetterViewNamesCreator;
import bc.bcCommunicator.Client.Views.UserConnectionState;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;

// TODO: Auto-generated Javadoc
/**
 * The Class TalkWindowDriver.
 */
public class TalkWindowDriver extends JFrameDriver  {
	
	/**
	 * Instantiates a new talk window driver.
	 *
	 * @param name the name
	 */
	public TalkWindowDriver(String name){
		super(new GesturePerformer(),
			JFrameDriver.topLevelFrame(	
				named(name),
				showingOnScreen()),
			new AWTEventQueueProber(1000, 500));
	}

	/**
	 * Assert has title.
	 *
	 * @param titleName the title name
	 */
	public void assertHasTitle(String titleName) {
		this.hasTitle(titleName);
	}

	/**
	 * Assert has connection state.
	 *
	 * @param state the state
	 */
	public void assertHasConnectionState(UserConnectionState state) {
		new JLabelDriver(this, named(WindowNames.TALK_USER_CONNECTION_STATE_LABEL)).hasText(equalTo(state.getStateDescription()));
	}

	/**
	 * Assert has talk user username.
	 *
	 * @param username the username
	 */
	public void assertHasTalkUserUsername(Username username) {
		new JLabelDriver(this, named(WindowNames.TALK_USER_USERNAME_LABEL)).hasText(equalTo(username.getName()));
	}

	/**
	 * Assert has letter state label.
	 *
	 * @param state the state
	 */
	public void assertHasLetterStateLabel(LetterState state) {
		new JLabelDriver(this, named(WindowNames.LETTER_STATE_LABEL)).hasText(equalTo(state.getMessage()));
	}

	/**
	 * Assert has letter view with text.
	 *
	 * @param letterText the letter text
	 */
	public void assertHasLetterViewWithText(LetterText letterText) {
		new JTextAreaDriver(this, JTextArea.class, named(LetterViewNamesCreator.createLetterViewLetterTextName(letterText.getTextValue())))
				.hasText(equalTo(letterText.getTextValue()));
	}

	/**
	 * Write letter input.
	 *
	 * @param letterText the letter text
	 */
	public void writeLetterInput(LetterText letterText) {
		JTextAreaDriver driver = new JTextAreaDriver(this, JTextArea.class, 
				named(WindowNames.LETTER_TEXT_INPUT_FIELD));
		driver.typeText("X");
		driver.replaceAllText(letterText.getTextValue());
	}

	/**
	 * Click send button.
	 *
	 * @param username the username
	 */
	public void clickSendButton(Username username) {
		new JButtonDriver(this, JButton.class, named(WindowNames.SEND_LETTER_BUTTON)).click();
	}

	/**
	 * Assert letter input has text.
	 *
	 * @param string the string
	 */
	public void assertLetterInputHasText(String string) {
		JTextAreaDriver driver = new JTextAreaDriver(this, JTextArea.class, 
				named(WindowNames.LETTER_TEXT_INPUT_FIELD));
		driver.hasText(string);
	}
}
