package bc.bcCommunicator.Model.Messages.Handling;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasItems;

import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.IModelMessagesSender;
import bc.bcCommunicator.Model.IOtherUsersDataContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Handling.AllUsersAddressesResponseHandler;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.internetMessageProxy.ConnectionId;

public class AllUsersAddressesResponseHandlerTest {
	private final Mockery context = new JUnit4Mockery();
	private final IOtherUsersDataContainer container = context.mock(IOtherUsersDataContainer.class);
	private final ICommunicatorController controller = context.mock(ICommunicatorController.class);
	private final IInternetMessager messager = context.mock(IInternetMessager.class);
	
	Map<Username, URL> usernameAddressMap = new HashMap<>();
	Set<Username> allUsers = new TreeSet<>();
	AllUsersAddresses allUsersAddresses;
	private final IAllUsersAddressesResponse response = context.mock(IAllUsersAddressesResponse.class);
	private final ConnectionId id = new ConnectionId(99);
	URL clientUrl;
	int clientPort;
	
	AllUsersAddressesResponseHandler handler;
	
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
