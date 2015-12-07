package bc.bcCommunicator.EndToEnd.Help;

import java.net.URL;
import java.util.Arrays;

import bc.bcCommunicator.Main;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Views.ServerConnectionStatus;
import bc.bcCommunicator.Views.UserConnectionState;
import bc.bcCommunicator.Views.UsernameInputStatus;

public class CommunicatorClientRunner {
	private CommunicatorClientDriver driver ;
	
	public void start(URL urlOfClient) {
		Thread thread = new Thread("Communicator app"){
			@Override public void run(){
				try{
					String[] inputs = {urlOfClient.toString()};
					Main.main(inputs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
		driver = new CommunicatorClientDriver(1000);
	}

	public void connectToServer(URL url) {
		driver.connectToServer(url);
	}

	public void assertIsNotConnectedToServer() {
		driver.ServerConnectionLabelHasStatus(ServerConnectionStatus.NotConnected.getText());
	}

	public void stop() {
		// TODO Auto-generated method stub
	}

	public void assertConnectionToServerFailed() throws InterruptedException {
		Thread.sleep(150);
		driver.ServerConnectionLabelHasStatus(ServerConnectionStatus.ConnectionFailed.getText());
		
	}

	public void assertConnectionToServerSucceded() throws InterruptedException {
		Thread.sleep(150);
		driver.ServerConnectionLabelHasStatus(ServerConnectionStatus.Connected.getText());
	}

	public void insertUsername(Username username) {
		driver.writeUsername(username); 
	}

	public void assertUsernameWasAccepted() throws InterruptedException {
		Thread.sleep(150); // TODO uwga na czas debugowania sen na 90 sek
		driver.UsernameInputLabelHasStatus(UsernameInputStatus.UsernameOk);
	}

	public void assertHasUserInUsersTable(Username oneUsername) throws InterruptedException {
		Thread.sleep(150);
		driver.UsersTableHasRowWithUsername(oneUsername);
	}

	public void assertUserHasConnectionState(Username oneUsername, UserConnectionState userConnectionState) throws InterruptedException {
		driver.usersTableHasRowWithValues(oneUsername, userConnectionState);
	}

}
