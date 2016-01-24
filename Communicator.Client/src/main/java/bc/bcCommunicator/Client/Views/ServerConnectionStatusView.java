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
 * The Class ServerConnectionStatusView.
 */
public class ServerConnectionStatusView extends JPanel implements IServerConnectionStatusView {
	
	/** The connection status. */
	private ServerConnectionStatus connectionStatus = ServerConnectionStatus.NotConnected;
	
	/** The server connection status label. */
	private final JLabel serverConnectionStatusLabel = new JLabel(connectionStatus.getText(), SwingConstants.CENTER);
	
	/** The server connection input field. */
	private final JTextField serverConnectionInputField = new JTextField(10);
	
	/** The server connection acceptance button. */
	private final JButton serverConnectionAcceptanceButton = new JButton();
	
	/**
	 * Instantiates a new server connection status view.
	 */
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
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IServerConnectionStatusView#getServerAddress()
	 */
	public String getServerAddress() {
		return serverConnectionInputField.getText();
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IServerConnectionStatusView#setServerConnectionStatus(bc.bcCommunicator.Client.Views.ServerConnectionStatus)
	 */
	public void setServerConnectionStatus(ServerConnectionStatus status){
		serverConnectionStatusLabel.setText(status.getText());
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IServerConnectionStatusView#setServerConnectionAcceptanceButtonWasClickedHandler(java.lang.Runnable)
	 */
	@Override
	public void setServerConnectionAcceptanceButtonWasClickedHandler(Runnable procedure) {
		serverConnectionAcceptanceButton.addActionListener((ActionEvent e)->{ procedure.run();});
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IServerConnectionStatusView#disableViev()
	 */
	@Override
	public void disableViev() {
		serverConnectionInputField.setEditable(false);
		serverConnectionAcceptanceButton.setEnabled(false);
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.IServerConnectionStatusView#enableView()
	 */
	@Override
	public void enableView() {
		serverConnectionInputField.setEditable(true);
		serverConnectionAcceptanceButton.setEnabled(true);
	}
}
