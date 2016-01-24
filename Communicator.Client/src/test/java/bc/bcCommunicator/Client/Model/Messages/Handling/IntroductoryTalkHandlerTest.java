package bc.bcCommunicator.Client.Model.Messages.Handling;

import java.net.URL;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.Model.IConnectionsContainer;
import bc.bcCommunicator.Client.Model.IOtherUsersDataContainer;
import bc.bcCommunicator.Client.Model.Messages.Handling.IntroductoryTalkHandler;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class IntroductoryTalkHandlerTest.
 */
public class IntroductoryTalkHandlerTest {
	
	/** The context. */
	private Mockery context = new JUnit4Mockery();
	
	/** The controller. */
	private ICommunicatorController controller = context.mock(ICommunicatorController.class);
	
	/** The users data container. */
	private IOtherUsersDataContainer usersDataContainer = context.mock(IOtherUsersDataContainer.class);
	
	/** The connections container. */
	private IConnectionsContainer connectionsContainer = context.mock(IConnectionsContainer.class);
	
	/** The handler. */
	private IntroductoryTalkHandler handler = new IntroductoryTalkHandler(controller, usersDataContainer, connectionsContainer);
	
	/** The in talk. */
	IIntroductoryTalk inTalk = context.mock(IIntroductoryTalk.class);
	
	/** The new user connection id. */
	ConnectionId newUserConnectionId = new ConnectionId(88);
	
	/**
	 * When introductory talk is sent and username is new controller is told to add user.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void whenIntroductoryTalkIsSentAndUsernameIsNewControllerIsToldToAddUser() throws Exception {	
		Username username = new Username("SomeUsername");
		context.checking( new Expectations(){{
			oneOf(usersDataContainer).isUsernameInDatabase(username); will(returnValue(false));
			oneOf(inTalk).getUsername(); will(returnValue(username));
			oneOf(controller).newUserConnected(username);
			
			allowing(inTalk);
			ignoring(connectionsContainer);
			ignoring(usersDataContainer);
		}});
		
		handler.handle(inTalk, newUserConnectionId);
		context.assertIsSatisfied();
	}
	
	/**
	 * New connection is added to connections container.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void newConnectionIsAddedToConnectionsContainer() throws Exception {
		Username username = new Username("SomeUsername");
		context.checking( new Expectations(){{
			oneOf(usersDataContainer).isUsernameInDatabase(username); will(returnValue(false));
			oneOf(inTalk).getUsername(); will(returnValue(username));
			oneOf(connectionsContainer).setIdForUser(username, newUserConnectionId);
			
			allowing(inTalk);
			ignoring(controller);
			ignoring(usersDataContainer);
		}});
		
		handler.handle(inTalk, newUserConnectionId);
		context.assertIsSatisfied();
	}
	
	/**
	 * When username is new address of user is saved in container.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void WhenUsernameIsNewAddressOfUserIsSavedInContainer() throws Exception {
		Username username = new Username("SomeUsername");
		URL address = new URL("http://sth.com");
		context.checking(new Expectations(){{
			oneOf(usersDataContainer).isUsernameInDatabase(username); will(returnValue(false));
			oneOf(inTalk).getUsername(); will(returnValue(username));
			oneOf(inTalk).getUrl(); will(returnValue(address));
			oneOf(usersDataContainer).addUserWithAddress(username, address);
			
			allowing(inTalk);
			ignoring(controller);
			ignoring(connectionsContainer);
		}});
		
		handler.handle(inTalk, newUserConnectionId);
		context.assertIsSatisfied();
	}
	
	/**
	 * When user is reconnecting we notify controller and users data container is updated.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void whenUserIsReconnectingWeNotifyControllerAndUsersDataContainerIsUpdated() throws Exception {
		Username username = new Username("SomeUsername");
		URL address = new URL("http://sth.com");
		context.checking(new Expectations() {{
			oneOf(inTalk).getUsername(); will(returnValue(username));
			oneOf(inTalk).getUrl(); will(returnValue(address));
			oneOf(connectionsContainer).setIdForUser(username, newUserConnectionId);
			oneOf(usersDataContainer).isUsernameInDatabase(username); will(returnValue(true));
			oneOf(controller).userWasConnected(username);
			oneOf(usersDataContainer).updateUrlOfUser( username, address);
		}});
		
		handler.handle(inTalk, newUserConnectionId);
		context.assertIsSatisfied();
	}
}
