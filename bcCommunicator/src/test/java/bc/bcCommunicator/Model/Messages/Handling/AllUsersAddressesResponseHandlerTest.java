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

import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.IModelMessagesSender;
import bc.bcCommunicator.Model.IUsernameContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommand;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Handling.AllUsersAddressesResponseHandler;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.internetMessageProxy.ConnectionId;

public class AllUsersAddressesResponseHandlerTest {
	private final Mockery context = new JUnit4Mockery();
	private final IUsernameContainer container = context.mock(IUsernameContainer.class);
	private final ICommunicatorController controller = context.mock(ICommunicatorController.class);
	private final IInternetMessagerCommandProvider commandProvider = context.mock(IInternetMessagerCommandProvider.class);
	private final IInternetMessager messager = context.mock(IInternetMessager.class);
	AllUsersAddressesResponseHandler handler = new AllUsersAddressesResponseHandler(container, commandProvider, messager);
	Map<Username, URL> usernameAddressMap = new HashMap<>();
	Set<Username> allUsers = new TreeSet<>();
	AllUsersAddresses allUsersAddresses;
	private final IAllUsersAddressesResponse response = context.mock(IAllUsersAddressesResponse.class);
	private final ConnectionId id = new ConnectionId(99);
	
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
		handler.setController(controller);
	}
	

	@Test
	public void correctlyFillsUsernameContainerWithData() throws Exception {
		context.checking(new Expectations(){{
			ignoring(controller);
			ignoring(messager);
			ignoring(commandProvider);
			allowing(response).getAllUsersAddresses(); will(returnValue(allUsersAddresses));
			for( Username oneUsername : usernameAddressMap.keySet() ){
				oneOf(container).addUserWithAddress(oneUsername, usernameAddressMap.get(oneUsername));
			}
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();
	}
	
	@Test
	public void correctlyPassesUsersDataToController() throws Exception {
		context.checking(new Expectations(){{
			ignoring(container);
			ignoring(messager);
			ignoring(commandProvider);
			allowing(response).getAllUsersAddresses(); will(returnValue(allUsersAddresses));
			oneOf(controller).setBulkUsers(new ArrayList<Username>(allUsers));
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();
	}
	
/*	@Test
	public void sendsIntroductoryTalkToUsers() throws Exception {
		context.checking(new Expectations(){{
			ignoring(container);
			ignoring(controller);
			allowing(response).getAllUsersAddresses(); will(returnValue(allUsersAddresses));
			oneOf(messagesSender).sendIntroductoryTalkToAllUsers();
		}});		
		handler.handle(response, id);
		context.assertIsSatisfied();
	}*/
	
	@Test
	public void ItIsOrderedToMessagerToConnectToUsers() throws Exception {
		IInternetMessagerCommand command = context.mock(IInternetMessagerCommand.class);
		context.checking(new Expectations(){{
			ignoring(container);
			ignoring(controller);
			allowing(response).getAllUsersAddresses(); will(returnValue(allUsersAddresses));
			for( URL oneAddress : usernameAddressMap.values() ){
				oneOf(commandProvider).getConnectToUserCommand(oneAddress); will(returnValue(command));
				oneOf(messager).addCommand(command);
			}
		}});		
		handler.handle(response, id);
		context.assertIsSatisfied();

		
	}

}
