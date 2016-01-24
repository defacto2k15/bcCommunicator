package bc.bcCommunicator.Model;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.bcCommunicator.Controller.ITalkStateDataFactory;
import bc.bcCommunicator.Controller.TalkStateData;
import bc.bcCommunicator.EndToEnd.Help.ConstantSampleInstances;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Letter.ILetterFactory;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterSendingType;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.bcCommunicator.Views.UserConnectionState;
import bc.internetMessageProxy.ConnectionId;

public class CommunicatorModelTest {
	private URL clientUrl;
	private final Mockery context = new JUnit4Mockery();
	private final IInternetMessager messager = context.mock(IInternetMessager.class);
	private ICommunicatorModel model;
	private final ICommunicatorController controller = context.mock(ICommunicatorController.class);
	private final IModelMessageProvider messageProvider = context.mock(IModelMessageProvider.class);
	private final IConnectionsContainer connectionsContainer = context.mock(IConnectionsContainer.class);
	private final IOtherUsersDataContainer usernameContainer = context.mock(IOtherUsersDataContainer.class);
	private final IActorUsernameContainer actorUsernameContainer = context.mock(IActorUsernameContainer.class);
	//private final IRecievedMessagesHandler recievedHandler = context.mock(IRecievedMessagesHandler.class);
	private final ILetterContainer letterContainer = context.mock(ILetterContainer.class);
	private final IPendingLettersContainer pendingLettersContainer = context.mock(IPendingLettersContainer.class);
	
	private final IModelMessagesSender messagesSender = context.mock(IModelMessagesSender.class);
	private final ITalkStateDataFactory talkStateDataFactory = context.mock(ITalkStateDataFactory.class);
	private final ILetterFactory letterFactory = context.mock(ILetterFactory.class);
	
	@Before
	public void setUp() throws MalformedURLException{
		clientUrl = new URL("http://localhost:5555");
		model = new CommunicatorModel(messager, clientUrl, messageProvider, 
				connectionsContainer, usernameContainer, messagesSender,
				actorUsernameContainer, controller, talkStateDataFactory, 
				letterFactory, letterContainer, pendingLettersContainer );
	}
	
	@Test
	public void modelPassesConnectionToServerRequestToInternetMessager() throws Exception{
		final URL serverAddress = new URL("http://localhost:9090");
		context.checking(new Expectations(){{
			oneOf(messager).connectToServer(serverAddress);
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
			allowing(actorUsernameContainer);
			oneOf(connectionsContainer).isServerConnected(); will(returnValue(true));
			oneOf(messagesSender).sendIntroductoryRequest();
		}});
		
		model.usernameSubmitted(username);
		context.assertIsSatisfied();
	}		

	
	@Test
	public void afterBeginAskedForTalkStateDataModelFetchesThatDataAndPassesItToController() throws ParseException{
		Username username = new Username("SomeName");
		List<Letter> lettersList = new ArrayList<Letter>();
		TalkStateData stateData = new TalkStateData(username, lettersList, UserConnectionState.Connected);
		context.checking( new Expectations(){{
			oneOf(letterContainer).getLettersOfTalkToUser(username); will(returnValue(lettersList));
			oneOf(connectionsContainer).isUserConnected(username); will(returnValue(true));
			oneOf(talkStateDataFactory).generate(username, lettersList, UserConnectionState.Connected); will(returnValue(stateData));
			oneOf(controller).talkStateChanged(stateData);
		}});
		
		model.getTalkStateData(username);
		context.assertIsSatisfied();
	}
	
	@Test
	public void afterLetterIsWrittenAndConnectionWithUserIsOkLetterTalkIsSent() throws Exception{
		String letterText = "SomeLetterText";
		Username recipient = new Username("SomeUsername");
		Username clientUsername = new Username("clientUsername");
		ConnectionId recipientConnectionId = new ConnectionId(99);
		Letter createdLetter = ConstantSampleInstances.getSampleLetter();
		context.checking(new Expectations(){{
			oneOf(actorUsernameContainer).getUsername(); will(returnValue(clientUsername));
			oneOf(letterFactory).create(with(new LetterText(letterText)), with(any(LetterDate.class)), 
					with(clientUsername), with(recipient), with(LetterSendingType.Sent));
				will(returnValue(createdLetter));
			oneOf(connectionsContainer).isUserConnected(recipient); will(returnValue(true));
			oneOf(connectionsContainer).getConnectionIdOfUser(recipient); will(returnValue(recipientConnectionId));
			oneOf(messagesSender).sendLetterTalk(createdLetter, recipientConnectionId);
			oneOf(pendingLettersContainer).addPendingLetter(recipient, createdLetter);
		}});
		
		model.letterWasWritten(letterText, recipient);
		context.assertIsSatisfied();
	}
	
	@Test
	public void ifLetterWasWrittenButUserIsNotConnectedThisInfoIsPassedToController() throws Exception{
		Username recipient = new Username("SomeName");
		
		context.checking(new Expectations(){{
			oneOf(connectionsContainer).isUserConnected(recipient); will(returnValue(false));
			oneOf(controller).letterSendingFailed(recipient);
		}});
		
		model.letterWasWritten("LetterTextNOT_USED", recipient);
		context.assertIsSatisfied();
	}
	

}
