package bc.bcCommunicator.EndToEnd.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bc.bcCommunicator.EndToEnd.Help.CommunicatorClientRunner;
import bc.bcCommunicator.EndToEnd.Help.FakeCommunicatorServerRunner;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.commonTestUtilities.FreePortGetter;

public class EndToEndTests {
	private int SERVER_PORT;
	private int CLIENT_PORT;
	private FakeCommunicatorServerRunner server;
	private final CommunicatorClientRunner client = new CommunicatorClientRunner();
	private final Username username = new Username("USER_NME");
	private URL clientUrl;

	@Before
	public void setUp() throws Exception {
		FreePortGetter getter = new FreePortGetter();
		SERVER_PORT = getter.getFreePortNumber();
		CLIENT_PORT = getter.getFreePortNumber();
		server = new FakeCommunicatorServerRunner(SERVER_PORT);
		clientUrl = new URL("http://127.0.0.1:"+CLIENT_PORT);
		client.start(clientUrl);
		server.start();
	}

	@After
	public void stopObjects() {
		server.stop();
		client.stop();
	}

	@Test
	public void whenClientStartsItIsNotConnectedToServer() {
		client.assertIsNotConnectedToServer();
	}
	@Test
	public void afterClientWritesHisNameIntroductionRequestIsSentToServer() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl);
	}
	

	@Test
	public void whenClientIsTryingToConnectToBadUriServerCanntConnectMessageIsShown() throws Exception {
		client.connectToServer(new URL("http://bad_URI.com:1111"));
		client.assertConnectionToServerFailed();
	}
	
	@Test
	public void whenConnectionToServerSuccededMessageIsShown() throws MalformedURLException, InterruptedException{
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		client.assertConnectionToServerSucceded();
	}
	
	@Test
	public void afterConnectionClientSendsIntroductionRequest() throws Exception{
		client.insertUsername(username); 
		client.connectToServer(new URL("http://127.0.0.1:"+SERVER_PORT));
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
	}
	
	@Test
	public void afterClientWritesHisNameIsIsWrittenThatUsernameWasAccepted() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		client.assertUsernameWasAccepted(); 		
	}

	/* THIS TEST IS NEXT!!
	@Test
	public void afterConnectionClientRequestsServerForUsersAddresses() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		server.assertHasRecievedRequestForUsersAdresses(null);
		
	}*/
	
	/*
	 * @Test public void clientSendsRequestForUserAdresses() { server.start();
	 * client.start(); client.connectToServerAt(new
	 * URL("http://localhost:"+SERVER_PORT));
	 * server.assertGotRequestForUserAdresses(); client.stop(); server.stop(); }
	 */

}
