package bc.bcCommunicator.Client.Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import bc.bcCommunicator.Client.WindowNames;

// TODO: Auto-generated Javadoc
/**
 * The Class UsernameInputView.
 */
public class UsernameInputView extends JPanel implements IUsernameInputView{
	
	/** The name input status label. */
	private JLabel nameInputStatusLabel = new JLabel("NOT_USED", SwingConstants.CENTER);
	
	/** The name input field. */
	private JTextField nameInputField = new JTextField(10);
	
	/** The submit name button. */
	private JButton submitNameButton = new JButton();
	
	/**
	 * Instantiates a new username input view.
	 */
	public UsernameInputView(){
		nameInputStatusLabel.setName(WindowNames.USERNAME_INPUT_STATUS_LABEL);
		nameInputField.setName(WindowNames.USERNAME_INPUT_TEXTFIELD);
		submitNameButton.setName(WindowNames.USERNAME_INPUT_ACCEPTANCE_BUTTON);
		nameInputStatusLabel.setText(UsernameInputStatus.UsernameNotInserted.getText());
		submitNameButton.setText("Set\n username!");

		setVisible(true);
		setLayout(new GridBagLayout());
		
		ComponentHelp.setAllThreeSizes(nameInputStatusLabel, new Dimension(300, 20));
		ComponentHelp.setAllThreeSizes(nameInputField, new Dimension(200, 30));
		Font labelFont = nameInputField.getFont();
		nameInputField.setFont( new Font(labelFont.getName(), Font.PLAIN, 20));
		
		ComponentHelp.setAllThreeSizes(submitNameButton, new Dimension(100, 28));
		Font submitFont = submitNameButton.getFont();
		submitNameButton.setFont(new Font( submitFont.getName(),Font.BOLD, 10 ));
		submitNameButton.setMargin(new Insets(2, 2, 2, 2));
		
		ComponentHelp.setAllThreeSizes(this, new Dimension(300, 52));
		setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		
		ComponentHelp.addComponent(this, nameInputStatusLabel, 0, 0, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE  );
		ComponentHelp.addComponent(this, nameInputField, 0, 1, 2, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE  );
		ComponentHelp.addComponent(this, submitNameButton, 2, 1, 1, 1, 0, 0, GridBagConstraints.LINE_END, GridBagConstraints.NONE  );
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IUsernameInputView#getUsernameText()
	 */
	@Override
	public String getUsernameText(){
		return nameInputField.getText();
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IUsernameInputView#submitUsernameButtonWasClickedHandler(java.lang.Runnable)
	 */
	@Override
	public void submitUsernameButtonWasClickedHandler(Runnable procedure) {
		submitNameButton.addActionListener((ActionEvent e)->{ procedure.run();});
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IUsernameInputView#setUsernameInputStatus(bc.bcCommunicator.Client.Views.UsernameInputStatus)
	 */
	@Override
	public void setUsernameInputStatus(UsernameInputStatus status) {	
		nameInputStatusLabel.setText(status.getText());
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IUsernameInputView#setUsernameSubmitButtonWasClickedHandler(java.lang.Runnable)
	 */
	@Override
	public void setUsernameSubmitButtonWasClickedHandler(Runnable procedure) {
		submitNameButton.addActionListener((ActionEvent e)->{ procedure.run();});
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IUsernameInputView#disableView()
	 */
	@Override
	public void disableView() {
		submitNameButton.setEnabled(false);
		nameInputField.setEditable(false);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IUsernameInputView#enableView()
	 */
	@Override
	public void enableView() {
		submitNameButton.setEnabled(true);
		nameInputField.setEditable(true);		
	}
	

}
