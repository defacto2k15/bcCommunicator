package bc.internetMessageProxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import bc.help.MyFreePortGetter;
import bc.internetMessageSender.FakeObjects.FakeMessageReciever;
import bc.internetMessageSender.FakeObjects.FakeMessageSender;

public class InternetMessageProxyTest {
	int freePortNumber;
	
	@Before
	public void setUpFreePortNumber() throws IOException, InterruptedException{
		Thread.sleep(1000);
		MyFreePortGetter getter = new MyFreePortGetter();
		freePortNumber = getFreePortNumber();
	}
	
	int getFreePortNumber() throws IOException{
		MyFreePortGetter getter = new MyFreePortGetter();
		return getter.getFreePortNumber();		
	}
	
	
	@Test
	public void canSendMessages() throws UnknownHostException, IOException, InterruptedException{
		String messageToSend = "MessageText";
		
		IInternetMessageProxy proxy = new InternetMessageProxy();
		FakeMessageReciever fakeReciever = new FakeMessageReciever();
		fakeReciever.startListening(freePortNumber);
		ConnectionId id = proxy.startConnection(new URL("http://localhost:"+freePortNumber)).get();	
		proxy.sendMessage(id, messageToSend);
		fakeReciever.assertRecieved(messageToSend);
	
		fakeReciever.close();
		proxy.closeConnections();
	}
	
	@Test
	public void canSendMultipleLinedMessages() throws IOException, InterruptedException {
		String messageToSend = "MessageText\nMessageText2\nMessageText3";
		
		IInternetMessageProxy proxy = new InternetMessageProxy();
		FakeMessageReciever fakeReciever = new FakeMessageReciever();
		fakeReciever.startListening(freePortNumber);
		ConnectionId id = proxy.startConnection(new URL("http://localhost:"+freePortNumber)).get();
		proxy.sendMessage(id, messageToSend);
		fakeReciever.assertRecieved(messageToSend);	
		fakeReciever.close();
		proxy.closeConnections();			
	}
	
	@Test
	public void canSendMessagesToMultipleRecievers() throws IOException {
		String[] messagesToSend = {"MessageText1", "MessageText2", "MessageText3" };
		
		IInternetMessageProxy proxy = new InternetMessageProxy();
		FakeMessageReciever[] fakeRecievers = { new FakeMessageReciever(), new FakeMessageReciever(), new FakeMessageReciever()};
		int[] ports = {getFreePortNumber(), getFreePortNumber(), getFreePortNumber()};
		for( int i = 0; i < fakeRecievers.length; i++){
			fakeRecievers[i].startListening(ports[i]);
		}
		
		for( int i = 0; i < fakeRecievers.length; i++){
			ConnectionId id = proxy.startConnection(new URL("http://localhost:"+ports[i])).get();
			proxy.sendMessage(id, messagesToSend[i]);
		}

		for( int i = 0; i < fakeRecievers.length; i++){
			fakeRecievers[i].assertRecieved(messagesToSend[i]);
			fakeRecievers[i].close();
		}
		
		proxy.closeConnections();
	}
	
	@Test
	public void sentMessagesCanBeRecieved() throws UnknownHostException, IOException{
		String messageToSend = "MessageText";
		
		IInternetMessageProxy proxy = new InternetMessageProxy();
		FakeMessageReciever fakeReciever = new FakeMessageReciever();
		fakeReciever.startListening(freePortNumber);
		ConnectionId id = proxy.startConnection(new URL("http://localhost:"+freePortNumber)).get();	
		proxy.sendMessage(id, messageToSend);
		fakeReciever.assertRecieved(messageToSend);
		fakeReciever.close();
		proxy.closeConnections();
	}
	
	@Test
	public void canRecieveMessages() throws Exception {
		String messageToRecieve = "MessageText";
		
		IInternetMessageProxy proxy = new InternetMessageProxy();
		FakeMessageSender fakeSender = new FakeMessageSender(new URL("http://localhost:"+freePortNumber));
		proxy.startListening(freePortNumber);
		fakeSender.connect();
		fakeSender.sendMessage(messageToRecieve);
		RecievedMessage recievedMessage = proxy.getMessageBlocking();
		assertEquals(messageToRecieve, recievedMessage.getMessage());
		fakeSender.close();
		proxy.closeConnections();
	}
	
	@Test
	public void canSendMessagesToRecieversDistinguishedByConnectionId() throws IOException{
		int portNumbers[] = {getFreePortNumber(), getFreePortNumber() };
		FakeMessageReciever[] fakeRecievers = {new FakeMessageReciever(), new FakeMessageReciever() };
		for(int i = 0; i < fakeRecievers.length; i++ ){
			fakeRecievers[i].startListening(portNumbers[i]);
		}
		
		IInternetMessageProxy proxy = new InternetMessageProxy();
		ConnectionId connection0 = proxy.startConnection(new URL("http://localhost:"+portNumbers[0])).get();
		ConnectionId connection1 = proxy.startConnection(new URL("http://localhost:"+portNumbers[1])).get();
		String[] messages = {"MessageToConnection1", "MessageToConnection2"};
		proxy.sendMessage(connection0, messages[0] );
		proxy.sendMessage(connection1, messages[1] );
		
		for( int i = 0; i < fakeRecievers.length; i++){
			fakeRecievers[i].assertRecieved(messages[i]);
			fakeRecievers[i].close();
		}
		proxy.closeConnections();
	}
	
	@Test
	public void whenConnectionIsClosedAppropiateListenerIsNotified() throws UnknownHostException, MalformedURLException, IOException, InterruptedException{
		BlockingQueue<ConnectionId> closedConnectionsIds = new ArrayBlockingQueue<ConnectionId>(10);
		
		IInternetMessageProxy proxy = new InternetMessageProxy((brokenConnectionId)->{
			try {
				closedConnectionsIds.put(brokenConnectionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		FakeMessageReciever fakeReciever = new FakeMessageReciever();
		fakeReciever.startListening(freePortNumber);
		Thread.sleep(100);
		ConnectionId id = proxy.startConnection(new URL("http://localhost:"+freePortNumber)).get();	
		fakeReciever.close();
		Thread.sleep(1000);
		assertEquals(id, closedConnectionsIds.poll(1,TimeUnit.SECONDS));
		proxy.closeConnections();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void whenSendingMessageToConnectionWithNonExistingIdExceptionIsThrown(){
		IInternetMessageProxy proxy = new InternetMessageProxy();
		proxy.sendMessage(new ConnectionId(99), "Some mesage text");
	}
	
	@Test
	public void whenConnectingToBadAddressEmptyOptionWillBeReturned() throws UnknownHostException, MalformedURLException, IOException
	{
		IInternetMessageProxy proxy = new InternetMessageProxy();	
		Optional<ConnectionId> opt = proxy.startConnection(new URL("http://fffff.com:1232"));
		assertFalse(opt.isPresent());
	}
	
	
	
}
