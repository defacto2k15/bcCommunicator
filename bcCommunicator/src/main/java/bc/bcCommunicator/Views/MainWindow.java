package bc.bcCommunicator.Views;

import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.objogate.wl.swing.driver.JFrameDriver;

import bc.bcCommunicator.WindowNames;

public class MainWindow extends JFrame{

	public MainWindow(ServerConnectionStatusView serverConnectionStatusView, UsernameInputView usernameInputView) {
		super("Communicator");
		setName(WindowNames.MAIN_WINDOW_NAME);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(serverConnectionStatusView);
		add(usernameInputView);
		setSize(400, 400);
		setLayout(new FlowLayout());
		setVisible(true);
	}


}
