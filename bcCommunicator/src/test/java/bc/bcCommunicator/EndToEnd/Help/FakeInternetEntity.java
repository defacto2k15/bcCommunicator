package bc.bcCommunicator.EndToEnd.Help;

import java.net.URL;

import org.junit.Assert;
import org.junit.Assert.*;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Request.AllUsersAddressesRequest;
import bc.bcCommunicator.Model.Messages.Request.IntroductoryRequest;
import bc.bcCommunicator.Model.Messages.Response.AllUsersAddressesResponse;
import bc.bcCommunicator.Model.Messages.Response.IResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameOkResponse;
import bc.internetMessageProxy.ConnectionId;
import bc.internetMessageProxy.IInternetMessageProxy;
import bc.internetMessageProxy.InternetMessageProxy;
import bc.internetMessageProxy.RecievedMessage;
import junit.framework.AssertionFailedError;

public class FakeInternetEntity {

	protected int port;
	protected ConnectionId lastConnectionId;
	
	private final IInternetMessageProxy proxy = new InternetMessageProxy(
			(ConnectionId id)->{ System.err.println("connection at fake server lost of id "+id);
			});

	public FakeInternetEntity(int port) {
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



	protected void assertRecievedMessageWithText(String expectedMessageText) {
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


	
	protected void sendMessageToLastConnectionSocket( IMessage message){
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


	

}
