package bc.bcCommunicator.Views;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import bc.bcCommunicator.WindowNames;



public class MainWindow extends JFrame{

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
