package bc.bcCommunicator.Client.Views;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import bc.bcCommunicator.Client.WindowNames;



// TODO: Auto-generated Javadoc
/**
 * The Class MainWindow.
 */
public class MainWindow extends JFrame{

	/**
	 * Instantiates a new main window.
	 *
	 * @param serverConnectionStatusView the server connection status view
	 * @param usernameInputView the username input view
	 * @param usersTableView the users table view
	 */
	public MainWindow(ServerConnectionStatusView serverConnectionStatusView, UsernameInputView usernameInputView, UsersTableView usersTableView) {
		super("Communicator");
		setName(WindowNames.MAIN_WINDOW_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(serverConnectionStatusView);
		add(usernameInputView);
		add(usersTableView);
		setSize(300, 600);
		setLayout(new FlowLayout());
		setVisible(true);
	}


}
