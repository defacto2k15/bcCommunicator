package bc.bcCommunicator.Views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.ICommunicatorController;
import bc.bcCommunicator.WindowNames;
import bc.bcCommunicator.Model.IConnectionsContainer;

public class UsernameInputView extends JPanel implements IUsernameInputView{
	private JLabel nameInputStatusLabel = new JLabel();
	private JTextField nameInputField = new JTextField(10);
	private JButton submitNameButton = new JButton();
	
	public UsernameInputView(){
		nameInputStatusLabel.setName(WindowNames.USERNAME_INPUT_STATUS_LABEL);
		nameInputField.setName(WindowNames.USERNAME_INPUT_TEXTFIELD);
		submitNameButton.setName(WindowNames.USERNAME_INPUT_ACCEPTANCE_BUTTON);
		nameInputStatusLabel.setText(UsernameInputStatus.UsernameNotInserted.getText());
		submitNameButton.setText("Set username!");
		add(nameInputStatusLabel);
		add(nameInputField);
		add(submitNameButton);
		setVisible(true);
		setLayout(new FlowLayout());
		setSize(100, 100);
	}
	
	@Override
	public String getUsernameText(){
		return nameInputField.getText();
	}
	
	@Override
	public void submitUsernameButtonWasClickedHandler(Runnable procedure) {
		submitNameButton.addActionListener((ActionEvent e)->{ procedure.run();});
	}

	@Override
	public void setUsernameInputStatus(UsernameInputStatus status) {	
		nameInputStatusLabel.setText(status.getText());
	}

	@Override
	public void setUsernameSubmitButtonWasClickedHandler(Runnable procedure) {
		submitNameButton.addActionListener((ActionEvent e)->{ procedure.run();});
	}
}
