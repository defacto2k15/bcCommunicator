package bc.bcCommunicator.Client.Model;

import java.net.MalformedURLException;
import java.net.URL;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.EndToEnd.Help.ConstantSampleInstances;
import bc.bcCommunicator.Client.Model.ConnectivityHandler;
import bc.bcCommunicator.Client.Model.IActorUsernameContainer;
import bc.bcCommunicator.Client.Model.IConnectionsContainer;
import bc.bcCommunicator.Client.Model.ILetterContainer;
import bc.bcCommunicator.Client.Model.IModelMessagesSender;
import bc.bcCommunicator.Client.Model.IOtherUsersDataContainer;
import bc.bcCommunicator.Client.Model.IPendingLettersContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectivityHandlerTest.
 */
public class ConnectivityHandlerTest {
	
	/** The context. */
	private final Mockery context = new JUnit4Mockery();
	
	/** The client url. */
	private URL clientUrl;
	
	/** The handler. */
	ConnectivityHandler handler;

	/** The controller. */
	private ICommunicatorController controller = context.mock(ICommunicatorController.class);
	
	/** The connections container. */
	private IConnectionsContainer connectionsContainer = context.mock( IConnectionsContainer.class);
	
	/** The username container. */
	private IOtherUsersDataContainer usernameContainer = context.mock( IOtherUsersDataContainer.class);
	
	/** The actor username container. */
	private IActorUsernameContainer actorUsernameContainer = context.mock( IActorUsernameContainer.class);
	
	/** The messages sender. */
	private IModelMessagesSender messagesSender = context.mock( IModelMessagesSender.class);
	
	/** The pending letters container. */
	private IPendingLettersContainer pendingLettersContainer = context.mock(IPendingLettersContainer.class);
	
	/** The letter container. */
	private  ILetterContainer letterContainer = context.mock(ILetterContainer.class);
	
	/** The recieved messages handler. */
	private IRecievedMessagesHandler recievedMessagesHandler = context.mock(IRecievedMessagesHandler.class);
	
	/**
	 * Sets the up.
	 *
	 * @throws MalformedURLException the malformed url exception
	 */
	@Before
	public void setUp() throws MalformedURLException{
		clientUrl = new URL("http://localhost:5555");
		handler = new ConnectivityHandler( controller, clientUrl, connectionsContainer, usernameContainer,
				actorUsernameContainer, messagesSender, pendingLettersContainer, letterContainer, recievedMessagesHandler);
	}
	
	/**
	 * Handler notifies controller about server connection status view change after connection with server is istablished.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * Notifies controller when server conection fails.
	 */
	@Test
	public void notifiesControllerWhenServerConectionFails(){
		context.checking(new Expectations(){{
			ignoring(connectionsContainer);
			oneOf(controller).serverConnectionFailed();
		}});
		handler.serverConnectionFailed();
		context.assertIsSatisfied();
	}
	
	/**
	 * After server is connected connection id is passed to connections container.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * After server is connected if usernme was set introductory request is sent to it.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * After server is connected if usernme was not set we just notify controller.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * After server connection failed connections container is notified.
	 */
	@Test
	public void afterServerConnectionFailedConnectionsContainerIsNotified(){
		context.checking(new Expectations(){{
			ignoring(controller);
			oneOf(connectionsContainer).removeServerConnectionIdIfExists();
		}});
		
		handler.serverConnectionFailed();
		context.assertIsSatisfied();
	}
	
	/**
	 * When connection to user was successfullhandler saves conenction id.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * When conenction to user was succesfull tell controller to change state of this user in view.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * After connectionhandler sends introductory talk to other user.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * When we lost connection and the connection was with user controller is notified.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void whenWeLostConnectionAndTheConnectionWasWithUserControllerIsNotified() throws Exception {
		ConnectionId lostConnectonId = new ConnectionId(99);
		ConnectionId serverConnectionId = new ConnectionId(1);
		Username someUserUsername = new Username("SomeUsername");
		
		context.checking( new Expectations(){{
			oneOf(connectionsContainer).getServerConnectionId(); will(returnValue(serverConnectionId));
			oneOf(connectionsContainer).getUsernameForConnectionId(lostConnectonId); will(returnValue(someUserUsername));
			oneOf(controller).userConnectionLost(someUserUsername);
			oneOf(connectionsContainer).connectionLost(someUserUsername);
		}});
		
		handler.connectionLost(lostConnectonId);
		context.assertIsSatisfied();
	}
	
	/**
	 * If message was sent to user and pending letter in this user talk is set we say to controller that letter was send.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void ifMessageWasSentToUserAndPendingLetterInThisUserTalkIsSetWeSayToControllerThatLetterWasSend() throws Exception{
		ConnectionId userConnectionId = new ConnectionId(99);
		Username talkingUsername = new Username("SomeUsername");
		Letter letter = ConstantSampleInstances.getSampleLetter();
		
		context.checking(new Expectations(){{
			oneOf(connectionsContainer).isThereUserWithThisConnectionId(userConnectionId); will(returnValue(true));
			oneOf(connectionsContainer).getUsernameForConnectionId(userConnectionId); will(returnValue(talkingUsername));
			oneOf(pendingLettersContainer).isPendingLetterAvalible(talkingUsername); will(returnValue(true));
			oneOf(pendingLettersContainer).getPendingLetter(talkingUsername); will(returnValue(letter));
			oneOf(controller).letterWasSent(letter);
			oneOf(letterContainer).addLetterOfTalkToUser(talkingUsername, letter);
		}});
		
		handler.messageSentSuccesfully(userConnectionId);
		context.assertIsSatisfied();
	}
	
	/**
	 * When message sending failed we pass this info to controller.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void whenMessageSendingFailedWePassThisInfoToController() throws Exception {
		ConnectionId userConnectionId = new ConnectionId(99);
		Username talkingUsername = new Username("SomeUsername");
		
		context.checking(new Expectations(){{
			oneOf(connectionsContainer).isThereUserWithThisConnectionId(userConnectionId); will(returnValue(true));
			oneOf(connectionsContainer).getUsernameForConnectionId(userConnectionId); will(returnValue(talkingUsername));
			oneOf(controller).letterSendingFailed(talkingUsername);
		}});
	
		handler.messageSendingFailed(userConnectionId);
		context.assertIsSatisfied();
	}
	
	
	/**
	 * When message is recieved it is passed to handler.
	 */
	@Test
	public void whenMessageIsRecievedItIsPassedToHandler(){
		IMessage message = context.mock(IMessage.class);
		ConnectionId id = new ConnectionId(99);
		context.checking(new Expectations(){{
			oneOf(recievedMessagesHandler).handle( message, id );
		}});
		handler.messageWasRecieved(message, id);
		context.assertIsSatisfied();
	}

}
