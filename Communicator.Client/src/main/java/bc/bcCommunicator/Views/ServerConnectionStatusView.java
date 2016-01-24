package bc.bcCommunicator.Views;

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

import bc.bcCommunicator.WindowNames;

public class ServerConnectionStatusView extends JPanel implements IServerConnectionStatusView {
	private ServerConnectionStatus connectionStatus = ServerConnectionStatus.NotConnected;
	private final JLabel serverConnectionStatusLabel = new JLabel(connectionStatus.getText(), SwingConstants.CENTER);
	private final JTextField serverConnectionInputField = new JTextField(10);
	private final JButton serverConnectionAcceptanceButton = new JButton();
	public ServerConnectionStatusView(){

		serverConnectionStatusLabel.setName(WindowNames.SERVER_CONNECTION_STATUS_LABEL);
		serverConnectionAcceptanceButton.setName(WindowNames.SERVER_CONNECTION_ACCEPTANCE_BUTTON);
		serverConnectionInputField.setName(WindowNames.SERVER_CONNECTION_INPUT_FIELD);
		
		setLayout(new GridBagLayout());

		ComponentHelp.setAllThreeSizes(serverConnectionStatusLabel, new Dimension(300, 20));
		ComponentHelp.setAllThreeSizes(serverConnectionInputField, new Dimension(200, 28));
		ComponentHelp.setAllThreeSizes(serverConnectionAcceptanceButton, new Dimension(100, 30));
		serverConnectionAcceptanceButton.setText("Connect to server");
		serverConnectionAcceptanceButton.setMargin(new Insets(2, 2, 2, 2));
		Font buttonFond = serverConnectionAcceptanceButton.getFont();
		serverConnectionAcceptanceButton.setFont( new Font(buttonFond.getName(), Font.BOLD, 9));
		
		serverConnectionStatusLabel.setText(ServerConnectionStatus.NotConnected.getText());
		
		ComponentHelp.addComponent(this, serverConnectionStatusLabel, 0, 0, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE  );
		ComponentHelp.addComponent(this, serverConnectionInputField, 0, 1, 2, 1, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE  );
		ComponentHelp.addComponent(this, serverConnectionAcceptanceButton, 2, 1, 1, 1, 0, 0, GridBagConstraints.LINE_END, GridBagConstraints.NONE  );
		
		setVisible(true);
		
		ComponentHelp.setAllThreeSizes(this, new Dimension(300, 50));
		setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		
		disableViev();
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
	@Override
	public void disableViev() {
		serverConnectionInputField.setEditable(false);
		serverConnectionAcceptanceButton.setEnabled(false);
	}
	@Override
	public void enableView() {
		serverConnectionInputField.setEditable(true);
		serverConnectionAcceptanceButton.setEnabled(true);
	}
}
