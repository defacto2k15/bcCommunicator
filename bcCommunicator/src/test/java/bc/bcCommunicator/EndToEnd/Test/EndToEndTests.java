package bc.bcCommunicator.EndToEnd.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bc.bcCommunicator.EndToEnd.Help.CommunicatorClientRunner;
import bc.bcCommunicator.EndToEnd.Help.FakeUserRunner;
import bc.bcCommunicator.EndToEnd.Help.FakeInternetEntity;
import bc.bcCommunicator.EndToEnd.Help.FakeServerRunner;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.Talk.IntroductoryTalk;
import bc.bcCommunicator.Views.UserConnectionState;
import bc.commonTestUtilities.FreePortGetter;

public class EndToEndTests {
	private int SERVER_PORT;
	private int CLIENT_PORT;
	private FakeServerRunner server;
	private final CommunicatorClientRunner client = new CommunicatorClientRunner();
	private final Username username = new Username("USER_NME");
	private URL clientUrl;
	//private AllUsersAddresses allUsersAddresses;
	private List<FakeUserRunner> users = new ArrayList<>();
	private final int CLIENT_COUNT = 3;
	
	FreePortGetter getter = new FreePortGetter();
	

	@Before
	public void setUp() throws Exception {
		
		SERVER_PORT = getter.getFreePortNumber();
		CLIENT_PORT = getter.getFreePortNumber();
		server = new FakeServerRunner(SERVER_PORT);
		clientUrl = new URL("http://127.0.0.1:"+CLIENT_PORT);
		client.start(clientUrl);
		server.start();
		
		//Map<Username, URL> usernameAddressesMap = new HashMap<>();
		for( int i = 0; i < CLIENT_COUNT; i++ ){
			int port = getter.getFreePortNumber();
			FakeUserRunner runner = new FakeUserRunner(port, new Username("User"+i), new URL("http://127.0.0.1:"+port));
			runner.start();
			users.add( runner );		
			//usernameAddressesMap.put(new Username("User"+i), new URL("http://127.0.0.1:"+port));
		}
		
		//allUsersAddresses = new AllUsersAddresses(usernameAddressesMap);
	}
	
	private List<Username> getUsernames(){
		return users.stream().map(FakeUserRunner::getUsername).collect(Collectors.toList());
	}
	
	private Map<Username, URL> getUsernamesWithAddresses(){
		return users.stream().collect(Collectors.toMap(FakeUserRunner::getUsername, FakeUserRunner::getUrl));
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

	@Test
	public void afterConnectionClientRequestsServerForUsersAddresses() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		server.assertHasRecievedRequestForUsersAdresses();
	}
	
	@Test
	public void afterRecievingAllUsersAddressesTheyAreWrittenToTableInView() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());		
		for( Username oneUsername : getUsernames()) {
			client.assertHasUserInUsersTable(oneUsername);
		}
	}
	
	@Test
	public void afterRecievingAllUsersAddressesInViewItsStateIsSetAsNotConnected() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());		

		for( Username oneUsername : getUsernames()) {
			client.assertUserHasConnectionState( oneUsername, UserConnectionState.NotConnected);
		}
	}
	
	@Test
	public void afterGettingAllUsersAddressesClientSendsIntroductoryTalkToOtherUsers() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		
		Thread.sleep(100);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);	
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());
		for( FakeUserRunner user : users ){
			user.assertRecievedIntroductoryTalkWith(username, new URL("http://127.0.0.1:"+CLIENT_PORT));
		}
	}
	
	@Test
	public void afterConnectingToUserAppropiateChangeOfStateIsWrittenToTable() throws Exception {
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);	
		
		Username someUserUsername = getUsernamesWithAddresses().keySet().iterator().next();
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());		
		client.assertUserHasConnectionState( someUserUsername, UserConnectionState.Connected);
	}
	
	@Test
	public void whenConnectionToUserFailsAppropiateChangeOfStateIsWrittenToTable() throws Exception {
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);	
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());	
		
		Thread.sleep(400);
		
		Username someUserUsername = users.get(0).getUsername();
		users.get(0).stop();
		
		client.assertUserHasConnectionState( someUserUsername, UserConnectionState.NotConnected );
	}
	

	
	/*@Test
	public void afterGettingAllUsersAddressesClientIsTryingToConnectToOtherUsers() throws Exception {
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		
		
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		
		users.get(0).stop();
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());
		
		client.assertUserHasConnectionState(users.get(0).getUsername(), UserConnectionState.CantConnect );
		client.assertUserHasConnectionState(users.get(1).getUsername(), UserConnectionState.CantConnect );
		client.assertUserHasConnectionState(users.get(2).getUsername(), UserConnectionState.CantConnect );
	}*/
	

}
