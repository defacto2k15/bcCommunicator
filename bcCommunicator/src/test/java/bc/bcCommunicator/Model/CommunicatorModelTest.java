package bc.bcCommunicator.Model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Expectation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommand;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.internetMessageProxy.ConnectionId;

public class CommunicatorModelTest {
	private URL clientUrl;
	private final Mockery context = new JUnit4Mockery();
	private final IInternetMessager messager = context.mock(IInternetMessager.class);
	private final IInternetMessagerCommandProvider commandProvider = context.mock(IInternetMessagerCommandProvider.class);
	private ICommunicatorModel model;
	private final ICommunicatorController controller = context.mock(ICommunicatorController.class);
	private final IInternetMessagerCommand command = context.mock(IInternetMessagerCommand.class);
	private final IModelMessageProvider messageProvider = context.mock(IModelMessageProvider.class);
	private final IConnectionsContainer connectionsContainer = context.mock(IConnectionsContainer.class);
	private final IOtherUsersDataContainer usernameContainer = context.mock(IOtherUsersDataContainer.class);
	private final IActorUsernameContainer actorUsernameContainer = context.mock(IActorUsernameContainer.class);
	private final IRecievedMessagesHandler recievedHandler = context.mock(IRecievedMessagesHandler.class);
	private final IConnectivityHandler connectivityHandler = context.mock(IConnectivityHandler.class);
	
	private final IModelMessagesSender messagesSender = context.mock(IModelMessagesSender.class);
	
	@Before
	public void setUp() throws MalformedURLException{
		clientUrl = new URL("http://localhost:5555");
		model = new CommunicatorModel(messager, commandProvider, clientUrl, messageProvider, connectionsContainer, usernameContainer, recievedHandler, messagesSender, actorUsernameContainer, connectivityHandler );
		model.setController(controller);
	}
	
	@Test
	public void modelPassesConnectionToServerRequestToInternetMessager() throws MalformedURLException{
		final URL serverAddress = new URL("http://localhost:9090");
		context.checking(new Expectations(){{
			oneOf(commandProvider).getConnectToServerCommand(serverAddress); will(returnValue(command));
			oneOf(messager).addCommand(command);
		}});
		
		model.connectToServer(serverAddress);
		context.assertIsSatisfied();
	}
	
	
	@Test
	public void afterUsernameIsSubmittedItIsPassedToUsernameContainer() throws Exception{
		Username username = new Username("Name");
		context.checking(new Expectations(){{
			ignoring(connectionsContainer);
			ignoring(connectionsContainer);
			ignoring(messageProvider);
			ignoring(commandProvider);
			ignoring(messager);
			oneOf(actorUsernameContainer).setUsername(username);
		}});
		
		model.usernameSubmitted(username);
		context.assertIsSatisfied();
	}
	
	@Test
	public void afterUsernameIsSubmittedAndConnectionToServerWasEstablishedIntroductoryMessageIsSent() throws Exception{
		Username username = new Username("Name");
		IRequest request = context.mock(IRequest.class);
		ConnectionId id = new ConnectionId(99);
		context.checking(new Expectations(){{
			ignoring(usernameContainer);
			oneOf(connectionsContainer).isServerConnected(); will(returnValue(true));
			oneOf(messagesSender).sendIntroductoryRequest();
		}});
		
		model.usernameSubmitted(username);
		context.assertIsSatisfied();
	}		
	
	@Test
	public void whenMessageIsRecievedItIsPassedToHandler(){
		IMessage message = context.mock(IMessage.class);
		ConnectionId id = new ConnectionId(99);
		context.checking(new Expectations(){{
			oneOf(recievedHandler).handle( message, id );
		}});
		model.messageWasRecieved(message, id);
		context.assertIsSatisfied();
	}
	
	@Test
	public void ConnectivityCommandsAreAppropiatelyCalled() throws Exception{
		IConnectivityCommand command = context.mock(IConnectivityCommand.class);
		context.checking(new Expectations(){{
			oneOf(command).run(connectivityHandler);
		}});
		model.doConnectivityCommand(command);
		context.assertIsSatisfied();
	}
	

}
