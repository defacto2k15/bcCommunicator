package bc.Communicator.Server.Test;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.commonTestUtilities.FakeUserRunner;
import bc.commonTestUtilities.FreePortGetter;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerEndToEndTest.
 */
public class ServerEndToEndTest {
	
	/** The getter. */
	FreePortGetter getter = new FreePortGetter();
	
	/** The server port number. */
	int serverPortNumber;
	
	/** The server url. */
	URL serverUrl;
	
	/** The users. */
	private static List<FakeUserRunner> users = new ArrayList<>();
	
	/** The Constant CLIENT_COUNT. */
	private final static int CLIENT_COUNT = 3;
	
	/** The server. */
	ServerRunner server = new ServerRunner();
	
	/**
	 * Sets the up.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Before
	public void setUp() throws IOException{
		serverPortNumber = getter.getFreePortNumber();
		server.start( serverPortNumber );
		serverUrl = new URL("http://127.0.0.1:"+serverPortNumber);
		
		for( int i = 0; i < CLIENT_COUNT; i++ ){
			int port = getter.getFreePortNumber();
			FakeUserRunner runner = new FakeUserRunner(port, new Username("User"+i), new URL("http://127.0.0.1:"+port));
			runner.start();
			users.add( runner );		
		}
	}
	
	/**
	 * After introductory message and username is unique username ok response is sent.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterIntroductoryMessageAndUsernameIsUniqueUsernameOkResponseIsSent() throws Exception{
		users.get(0).connectTo(serverUrl);
		users.get(0).sendIntroductoryRequest();
		users.get(0).assertRecievedUsernameOkResponse();
	}
	
	/**
	 * After user have arleady taken username username bad response is sent.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterUserHaveArleadyTakenUsernameUsernameBadResponseIsSent() throws Exception {
		int port = getter.getFreePortNumber();
		FakeUserRunner user1  = new FakeUserRunner(port, new Username("User"), new URL("http://127.0.0.1:"+port));
		 port = getter.getFreePortNumber();
		FakeUserRunner user2  = new FakeUserRunner(port, new Username("User"), new URL("http://127.0.0.1:"+port));
		
		user1.connectTo(serverUrl);
		user1.sendIntroductoryRequest();
		user1.assertRecievedUsernameOkResponse();
		
		user2.connectTo(serverUrl);
		user2.sendIntroductoryRequest();
		user2.assertRecievedUsernameBadResponse();
	}
	
	/**
	 * After user have username taken by user before but that user disconnected that username is ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterUserHaveUsernameTakenByUserBeforeButThatUserDisconnectedThatUsernameIsOk() throws Exception{
		int port = getter.getFreePortNumber();
		FakeUserRunner user1  = new FakeUserRunner(port, new Username("User"), new URL("http://127.0.0.1:"+port));
		 port = getter.getFreePortNumber();
		FakeUserRunner user2  = new FakeUserRunner(port, new Username("User"), new URL("http://127.0.0.1:"+port));
		
		user1.connectTo(serverUrl);
		user1.sendIntroductoryRequest();
		user1.assertRecievedUsernameOkResponse();
		user1.stop();
		Thread.sleep(200);
		
		user2.connectTo(serverUrl);
		user2.sendIntroductoryRequest();
		user2.assertRecievedUsernameOkResponse();		
	}
	
	/**
	 * All users and addesses work as expected.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void allUsersAndAddessesWorkAsExpected() throws Exception{
		for( FakeUserRunner runner : users ){
			runner.connectTo(serverUrl);
			runner.sendIntroductoryRequest();
			runner.assertRecievedUsernameOkResponse();
		}
		
		Map<Username, URL > allUsersAddressesMap = users.stream()
				.collect(Collectors.toMap(FakeUserRunner::getUsername, FakeUserRunner::getUrl));
		allUsersAddressesMap.remove(users.get(0).getUsername() );
		users.get(0).sendAllUsersAddressesRequest();
		users.get(0).assertRecievedAllUsersAddressesResponse( allUsersAddressesMap );
	}
	
	/**
	 * When some user disconnects it is not present in all users and addresses.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void whenSomeUserDisconnectsItIsNotPresentInAllUsersAndAddresses() throws Exception{
		for( FakeUserRunner runner : users ){
			runner.connectTo(serverUrl);
			runner.sendIntroductoryRequest();
			runner.assertRecievedUsernameOkResponse();
		}
		users.get(2).stop();
		users.remove(2);
		Thread.sleep(300);
		
		Map<Username, URL > allUsersAddressesMap = users.stream()
				.collect(Collectors.toMap(FakeUserRunner::getUsername, FakeUserRunner::getUrl));
		allUsersAddressesMap.remove(users.get(0).getUsername() );
		users.get(0).sendAllUsersAddressesRequest();
		users.get(0).assertRecievedAllUsersAddressesResponse( allUsersAddressesMap );		
	}
	
}
