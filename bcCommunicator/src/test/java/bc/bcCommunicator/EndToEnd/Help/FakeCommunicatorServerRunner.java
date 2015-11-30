package bc.bcCommunicator.EndToEnd.Help;

import java.net.URL;

import org.junit.Assert;
import org.junit.Assert.*;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.AllUsersAddressesRequest;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.IResponse;
import bc.bcCommunicator.Model.Messages.IntroductoryRequest;
import bc.bcCommunicator.Model.Messages.UsernameOkResponse;
import bc.internetMessageProxy.ConnectionId;
import bc.internetMessageProxy.IInternetMessageProxy;
import bc.internetMessageProxy.InternetMessageProxy;
import bc.internetMessageProxy.RecievedMessage;
import junit.framework.AssertionFailedError;

public class FakeCommunicatorServerRunner {

	private int port;
	private ConnectionId lastConnectionId;
	
	private final IInternetMessageProxy proxy = new InternetMessageProxy(
			(ConnectionId id)->{ System.err.println("connection at fake server lost of id "+id);
			});

	public FakeCommunicatorServerRunner(int port) {
		this.port = port;
	}

	public void start() {
		proxy.startListening(port);
	}

	public void stop() {
		proxy.closeConnections();
	}

	public void assertClientAskedForUsersId() {
		// TODO Auto-generated method stub
		assert false;
	}

	public void assertHasRecievedIntrodutionRequestWith(Username username, URL clientUrl) throws Exception {
		assertRecievedMessageWithText( new IntroductoryRequest(username, clientUrl).getMessageText() );
	}

	private void assertRecievedMessageWithText(String expectedMessageText) {
		String actualMessageText = null;
		try {
			RecievedMessage message = proxy.getMessageBlockingWithTimeout();
			actualMessageText = message.getMessage();
			lastConnectionId = message.getConnectionId();
		} catch (Exception e) {
			Assert.fail("Message timeouted.");
		}
		System.out.println("CHECKING "+actualMessageText);
		Assert.assertEquals(expectedMessageText, actualMessageText);
	}

	public void sendUsernameOkResponseWith(Username username, URL clientUrl) throws Exception {
		IResponse response = new UsernameOkResponse( username );
		assert( lastConnectionId != null);
		sendMessageToLastConnectionSocket(response);

	}
	
	private void sendMessageToLastConnectionSocket( IMessage message){
		if( lastConnectionId == null){
			System.out.println("No message recieved by fakeCommunicatorServer. Will try to get mesage to response to sender");
			try {
				lastConnectionId = proxy.getMessageBlocking().getConnectionId();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			System.out.println("X159 Sending messge "+message.getMessageText());
			proxy.sendMessage(lastConnectionId, message.getMessageText());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void assertHasRecievedRequestForUsersAdresses(AllUsersAddresses allUsersAddresses) throws Exception {
		assertRecievedMessageWithText( new AllUsersAddressesRequest(allUsersAddresses).getMessageText() );
	}
	

}
