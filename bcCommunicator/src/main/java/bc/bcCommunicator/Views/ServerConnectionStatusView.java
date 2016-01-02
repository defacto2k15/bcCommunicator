package bc.bcCommunicator.Views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bc.bcCommunicator.WindowNames;

public class ServerConnectionStatusView extends JPanel implements IServerConnectionStatusView {
	private ServerConnectionStatus connectionStatus = ServerConnectionStatus.NotConnected;
	private final JLabel serverConnectionStatusLabel = new JLabel(connectionStatus.getText());
	private final JTextField serverConnectionInputField = new JTextField(10);
	private final JButton serverConnectionAcceptanceButton = new JButton();
	public ServerConnectionStatusView(){

		serverConnectionStatusLabel.setName(WindowNames.SERVER_CONNECTION_STATUS_LABEL);
		serverConnectionAcceptanceButton.setName(WindowNames.SERVER_CONNECTION_ACCEPTANCE_BUTTON);
		serverConnectionInputField.setName(WindowNames.SERVER_CONNECTION_INPUT_FIELD);
		serverConnectionStatusLabel.setSize(200,200);
		serverConnectionInputField.setSize(400, 50);
		serverConnectionInputField.setText(" ");
		serverConnectionStatusLabel.setText(ServerConnectionStatus.NotConnected.getText());
		add(serverConnectionStatusLabel);
		add(serverConnectionAcceptanceButton);
		add(serverConnectionInputField);
		setVisible(true);
		setLayout(new FlowLayout());
		setSize(100, 100);
	}
	public String getServerAddress() {
		return serverConnectionInputField.getText();
	}
	
	public void setServerConnectionStatus(ServerConnectionStatus status){
		serverConnectionStatusLabel.setText(status.getText());
	}
	@Override
	public void setServerConnectionAcceptanceButtonWasClickedHandler(Runnable procedure) {
		serverConnectionAcceptanceButton.addActionListener((ActionEvent e)->{ procedure.run();});
	}
}
