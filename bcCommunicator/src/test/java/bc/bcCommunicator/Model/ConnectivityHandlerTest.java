package bc.bcCommunicator.Model;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.internetMessageProxy.ConnectionId;

public class ConnectivityHandlerTest {
	private final Mockery context = new JUnit4Mockery();
	
	private URL clientUrl;
	ConnectivityHandler handler;

	private ICommunicatorController controller = context.mock(ICommunicatorController.class);
	private IConnectionsContainer connectionsContainer = context.mock( IConnectionsContainer.class);
	private IOtherUsersDataContainer usernameContainer = context.mock( IOtherUsersDataContainer.class);
	private IActorUsernameContainer actorUsernameContainer = context.mock( IActorUsernameContainer.class);
	private IModelMessagesSender messagesSender = context.mock( IModelMessagesSender.class);
	
	@Before
	public void setUp() throws MalformedURLException{
		clientUrl = new URL("http://localhost:5555");
		handler = new ConnectivityHandler( controller, clientUrl, connectionsContainer, usernameContainer, actorUsernameContainer, messagesSender);
	}
	
	@Test
	public void handlerNotifiesControllerAboutServerConnectionStatusViewChangeAfterConnectionWithServerIsIstablished() throws Exception{
		context.checking(new Expectations(){{
			ignoring(connectionsContainer);
			ignoring(actorUsernameContainer);
			oneOf(controller).serverConnectionWasSuccesfull(); 
		}});
		handler.serverConnectionWasSuccesfull(new ConnectionId(10));
		context.assertIsSatisfied();
	}
	
	@Test
	public void notifiesControllerWhenServerConectionFails(){
		context.checking(new Expectations(){{
			ignoring(connectionsContainer);
			oneOf(controller).serverConnectionFailed();
		}});
		handler.serverConnectionFailed();
		context.assertIsSatisfied();
	}
	
	@Test
	public void afterServerIsConnectedConnectionIdIsPassedToConnectionsContainer() throws Exception{
		ConnectionId id = new ConnectionId(99);
		context.checking(new Expectations(){{
			ignoring(controller);
			ignoring(actorUsernameContainer);		
			oneOf(connectionsContainer).setServerConnectionId(id);
		}});
		
		handler.serverConnectionWasSuccesfull(id);
		context.assertIsSatisfied();
	}
	
	@Test
	public void afterServerIsConnectedIfUsernmeWasSetIntroductoryRequestIsSentToIt() throws Exception{
		IRequest request = context.mock(IRequest.class);
		ConnectionId id = new ConnectionId(99);
		Username username = new Username("Name");
		context.checking(new Expectations(){{
			ignoring(controller);
			ignoring(connectionsContainer);
			oneOf(actorUsernameContainer).isUsernameSet(); will(returnValue(true));
			oneOf(messagesSender).sendIntroductoryRequest();
		}});
		
		handler.serverConnectionWasSuccesfull(id);
		context.assertIsSatisfied();		
	}
	
	@Test
	public void afterServerIsConnectedIfUsernmeWasNotSetWeJustNotifyController() throws Exception{
		IRequest request = context.mock(IRequest.class);
		ConnectionId id = new ConnectionId(99);
		Username username = new Username("Name");
		context.checking(new Expectations(){{
			ignoring(connectionsContainer);
			oneOf(actorUsernameContainer).isUsernameSet(); will(returnValue(false));
			oneOf(controller).serverConnectionWasSuccesfull(); 
		}});
		
		handler.serverConnectionWasSuccesfull(id);
		context.assertIsSatisfied();		
	}	
	
	@Test
	public void afterServerConnectionFailedConnectionsContainerIsNotified(){
		context.checking(new Expectations(){{
			ignoring(controller);
			oneOf(connectionsContainer).removeServerConnectionIdIfExists();
		}});
		
		handler.serverConnectionFailed();
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenConnectionToUserWasSuccessfullhandlerSavesConenctionId() throws Exception{
		URL url = new URL("http://www.sth.com");
		Username username = new Username("name");
		ConnectionId id = new ConnectionId(99);
		context.checking(new Expectations(){{
			oneOf(usernameContainer).getUsernameForAddress(url); will(returnValue(username));
			oneOf(connectionsContainer).setIdForUser(username, id);
			
			allowing(connectionsContainer);
			allowing(usernameContainer);
			allowing(controller);
			allowing(actorUsernameContainer);
			allowing(messagesSender);
		}});
		handler.userConnectionWasSuccesfull(url, id);
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenConenctionToUserWasSuccesfullTellControllerToChangeStateOfThisUserInView() throws Exception{
		URL url = new URL("http://sth.com");
		Username username = new Username("name");
		ConnectionId id = new ConnectionId(99);
		context.checking( new Expectations(){{
			oneOf(usernameContainer).getUsernameForAddress(url); will(returnValue(username));
			oneOf(controller).userWasConnected( username );
			
			allowing(connectionsContainer);
			allowing(usernameContainer);
			allowing(controller);
			allowing(actorUsernameContainer);
			allowing(messagesSender);
		}});
		handler.userConnectionWasSuccesfull(url, id);
		context.assertIsSatisfied();
	}
	
	@Test
	public void afterConnectionhandlerSendsIntroductoryTalkToOtherUser() throws Exception{
		ConnectionId id = new ConnectionId(99);
		Username actorUsername = new Username("OUR NAME");
		
		context.checking(new Expectations(){{
			allowing(usernameContainer);
			allowing(connectionsContainer);
			allowing(controller);
			
			oneOf(actorUsernameContainer).getUsername(); will(returnValue(actorUsername));
			oneOf(messagesSender).sendIntroductoryTalkToUser(id, actorUsername, clientUrl);
		}});
		handler.userConnectionWasSuccesfull(new URL("http://NOT_IMPORTANT.com"), id );
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenWeLostConnectionAndTheConnectionWasWithUserControllerIsNotified() throws Exception {
		ConnectionId lostConnectonId = new ConnectionId(99);
		ConnectionId serverConnectionId = new ConnectionId(1);
		Username someUserUsername = new Username("SomeUsername");
		
		context.checking( new Expectations(){{
			oneOf(connectionsContainer).getServerConnectionId(); will(returnValue(serverConnectionId));
			oneOf(connectionsContainer).getUsernameForConnectionId(lostConnectonId); will(returnValue(someUserUsername));
			oneOf(controller).userConnectionLost(someUserUsername);
		}});
		
		handler.connectionLost(lostConnectonId);
		context.assertIsSatisfied();
	}

}
