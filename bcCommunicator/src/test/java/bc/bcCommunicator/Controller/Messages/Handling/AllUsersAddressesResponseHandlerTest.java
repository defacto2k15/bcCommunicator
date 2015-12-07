package bc.bcCommunicator.Controller.Messages.Handling;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.IModelMessagesSender;
import bc.bcCommunicator.Model.IUsernameContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.Handling.AllUsersAddressesResponseHandler;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.internetMessageProxy.ConnectionId;

public class AllUsersAddressesResponseHandlerTest {
	private final Mockery context = new JUnit4Mockery();
	private final IUsernameContainer container = context.mock(IUsernameContainer.class);
	private final ICommunicatorController controller = context.mock(ICommunicatorController.class);
	private final IModelMessagesSender messagesSender = context.mock(IModelMessagesSender.class);
	AllUsersAddressesResponseHandler handler = new AllUsersAddressesResponseHandler(container, messagesSender);
	Map<Username, URL> usernameAddressMap = new HashMap<>();
	List<Username> allUsers = new ArrayList<>();
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
		
		allUsers.add(user1);
		allUsers.add(user2);
		allUsers.add(user3);
		handler.setController(controller);
	}
	

	@Test
	public void correctlyFillsUsernameContainerWithData() throws Exception {
		context.checking(new Expectations(){{
			ignoring(controller);
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
			allowing(response).getAllUsersAddresses(); will(returnValue(allUsersAddresses));
			oneOf(controller).setBulkUsers(allUsers);
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();
	}
	
	@Test
	public void sendsIntroductoryTalkToUsers() throws Exception {
		context.checking(new Expectations(){{
			ignoring(container);
			ignoring(controller);
			allowing(response).getAllUsersAddresses(); will(returnValue(allUsersAddresses));
			oneOf(messagesSender).sendIntroductoryTalkToAllUsers();
		}});		
		handler.handle(response, id);
		context.assertIsSatisfied();
	}

}
