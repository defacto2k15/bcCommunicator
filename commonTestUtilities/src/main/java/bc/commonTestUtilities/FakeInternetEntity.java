package bc.commonTestUtilities;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;
import bc.internetMessageProxy.IInternetMessageProxy;
import bc.internetMessageProxy.InternetMessageProxy;
import bc.internetMessageProxy.RecievedMessage;

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
	
	public void ignoreRecievedMessage() {
		getMessageText();
	}

	
	private String getMessageText(){
		String actualMessageText = null;
		try {
			RecievedMessage message = proxy.getMessageBlockingWithTimeout();
			actualMessageText = message.getMessage();
			lastConnectionId = message.getConnectionId();
		} catch (Exception e) {
			Assert.fail("Message timeouted.");
		}	
		return actualMessageText;
	}

	protected void assertRecievedMessageContainingText( String text){
		Assert.assertThat(getMessageText(), CoreMatchers.containsString(text));
	}

	protected void assertRecievedMessageWithExactText(String expectedMessageText) {

		Assert.assertEquals(expectedMessageText, getMessageText());
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

	public void connectTo(URL clientUrl) throws UnknownHostException, IOException {
		Optional<ConnectionId> optionalId = proxy.startConnection(clientUrl);
		Assert.assertTrue("Connection to url "+clientUrl+" failed", optionalId.isPresent());
		lastConnectionId = optionalId.get();
	}
	

}
