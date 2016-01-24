package bc.bcCommunicator.Client.Model.Messages.Handling;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.Model.IOtherUsersDataContainer;
import bc.bcCommunicator.Client.Model.Messages.Handling.AllUsersAddressesResponseHandler;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class AllUsersAddressesResponseHandlerTest.
 */
public class AllUsersAddressesResponseHandlerTest {
	
	/** The context. */
	private final Mockery context = new JUnit4Mockery();
	
	/** The container. */
	private final IOtherUsersDataContainer container = context.mock(IOtherUsersDataContainer.class);
	
	/** The controller. */
	private final ICommunicatorController controller = context.mock(ICommunicatorController.class);
	
	/** The messager. */
	private final IInternetMessager messager = context.mock(IInternetMessager.class);
	
	/** The username address map. */
	Map<Username, URL> usernameAddressMap = new HashMap<>();
	
	/** The all users. */
	Set<Username> allUsers = new TreeSet<>();
	
	/** The all users addresses. */
	AllUsersAddresses allUsersAddresses;
	
	/** The response. */
	private final IAllUsersAddressesResponse response = context.mock(IAllUsersAddressesResponse.class);
	
	/** The id. */
	private final ConnectionId id = new ConnectionId(99);
	
	/** The client url. */
	URL clientUrl;
	
	/** The client port. */
	int clientPort;
	
	/** The handler. */
	AllUsersAddressesResponseHandler handler;
	
	/**
	 * Sets the up.
	 *
	 * @throws MalformedURLException the malformed url exception
	 */
	@Before
	public void setUp() throws MalformedURLException{
		Username user1 = new Username("User1");
		Username user2 = new Username("User2");
		Username user3 = new Username("User3");
		usernameAddressMap.put(user1, new URL("http://127.0.0.1:9921"));
		usernameAddressMap.put(user2, new URL("http://127.0.0.1:9922"));
		usernameAddressMap.put(user3, new URL("http://127.0.0.1:9923"));
		
		allUsersAddresses = new AllUsersAddresses( usernameAddressMap );
		
		allUsers.add(user3);
		allUsers.add(user2);
		allUsers.add(user1);
		
		
		clientPort = 2020;
		clientUrl = new URL("http://127.0.0.1:"+clientPort);
		handler = new AllUsersAddressesResponseHandler(container, messager, clientUrl, controller);
		
	}
	

	/**
	 * Correctly fills username container with data.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void correctlyFillsUsernameContainerWithData() throws Exception {
		context.checking(new Expectations(){{
			ignoring(controller);
			ignoring(messager);
			allowing(response).getAllUsersAddresses(); will(returnValue(allUsersAddresses));
			for( Username oneUsername : usernameAddressMap.keySet() ){
				oneOf(container).addUserWithAddress(oneUsername, usernameAddressMap.get(oneUsername));
			}
			
			allowing(messager);
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();
	}
	
	/**
	 * Correctly passes users data to controller.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void correctlyPassesUsersDataToController() throws Exception {
		context.checking(new Expectations(){{
			ignoring(container);
			ignoring(messager);
			allowing(response).getAllUsersAddresses(); will(returnValue(allUsersAddresses));
			oneOf(controller).setBulkUsers(new ArrayList<Username>(allUsers));
			
			allowing(messager);
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();
	}
	
	
	/**
	 * It is ordered to messager to connect to users.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void ItIsOrderedToMessagerToConnectToUsers() throws Exception {
		context.checking(new Expectations(){{
			ignoring(container);
			ignoring(controller);
			allowing(response).getAllUsersAddresses(); will(returnValue(allUsersAddresses));
			for( URL oneAddress : usernameAddressMap.values() ){
				oneOf(messager).connectToUser(oneAddress);
			}
			
			allowing(messager);
		}});		
		handler.handle(response, id);
		context.assertIsSatisfied();
	}
	
	/**
	 * It is ordered to messager to start listening on port.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void ItIsOrderedToMessagerToStartListeningOnPort() throws Exception {
		context.checking(new Expectations(){{
			ignoring(container);
			ignoring(controller);
			allowing(response).getAllUsersAddresses(); will(returnValue(allUsersAddresses));
			
			oneOf(messager).listenOnPort(clientPort);
			
			allowing(messager);
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();
	}

}
