package bc.bcCommunicator.Model.Messages.Handling;

import java.net.URL;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.IConnectionsContainer;
import bc.bcCommunicator.Model.IOtherUsersDataContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk;
import bc.internetMessageProxy.ConnectionId;

public class IntroductoryTalkHandlerTest {
	private Mockery context = new JUnit4Mockery();
	private ICommunicatorController controller = context.mock(ICommunicatorController.class);
	private IOtherUsersDataContainer usersDataContainer = context.mock(IOtherUsersDataContainer.class);
	private IConnectionsContainer connectionsContainer = context.mock(IConnectionsContainer.class);
	
	private IntroductoryTalkHandler handler = new IntroductoryTalkHandler(controller, usersDataContainer, connectionsContainer);
	
	IIntroductoryTalk inTalk = context.mock(IIntroductoryTalk.class);
	ConnectionId newUserConnectionId = new ConnectionId(88);
	
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
