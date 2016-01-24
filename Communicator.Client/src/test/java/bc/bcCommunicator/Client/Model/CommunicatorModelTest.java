package bc.bcCommunicator.Client.Model;

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

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.Controller.ITalkStateDataFactory;
import bc.bcCommunicator.Client.Controller.TalkStateData;
import bc.bcCommunicator.Client.EndToEnd.Help.ConstantSampleInstances;
import bc.bcCommunicator.Client.Model.CommunicatorModel;
import bc.bcCommunicator.Client.Model.IActorUsernameContainer;
import bc.bcCommunicator.Client.Model.ICommunicatorModel;
import bc.bcCommunicator.Client.Model.IConnectionsContainer;
import bc.bcCommunicator.Client.Model.ILetterContainer;
import bc.bcCommunicator.Client.Model.IModelMessagesSender;
import bc.bcCommunicator.Client.Model.IOtherUsersDataContainer;
import bc.bcCommunicator.Client.Model.IPendingLettersContainer;
import bc.bcCommunicator.Client.Views.UserConnectionState;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Letter.ILetterFactory;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterSendingType;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class CommunicatorModelTest.
 */
public class CommunicatorModelTest {
	
	/** The client url. */
	private URL clientUrl;
	
	/** The context. */
	private final Mockery context = new JUnit4Mockery();
	
	/** The messager. */
	private final IInternetMessager messager = context.mock(IInternetMessager.class);
	
	/** The model. */
	private ICommunicatorModel model;
	
	/** The controller. */
	private final ICommunicatorController controller = context.mock(ICommunicatorController.class);
	
	/** The message provider. */
	private final IModelMessageProvider messageProvider = context.mock(IModelMessageProvider.class);
	
	/** The connections container. */
	private final IConnectionsContainer connectionsContainer = context.mock(IConnectionsContainer.class);
	
	/** The username container. */
	private final IOtherUsersDataContainer usernameContainer = context.mock(IOtherUsersDataContainer.class);
	
	/** The actor username container. */
	private final IActorUsernameContainer actorUsernameContainer = context.mock(IActorUsernameContainer.class);
	
	/** The letter container. */
	//private final IRecievedMessagesHandler recievedHandler = context.mock(IRecievedMessagesHandler.class);
	private final ILetterContainer letterContainer = context.mock(ILetterContainer.class);
	
	/** The pending letters container. */
	private final IPendingLettersContainer pendingLettersContainer = context.mock(IPendingLettersContainer.class);
	
	/** The messages sender. */
	private final IModelMessagesSender messagesSender = context.mock(IModelMessagesSender.class);
	
	/** The talk state data factory. */
	private final ITalkStateDataFactory talkStateDataFactory = context.mock(ITalkStateDataFactory.class);
	
	/** The letter factory. */
	private final ILetterFactory letterFactory = context.mock(ILetterFactory.class);
	
	/**
	 * Sets the up.
	 *
	 * @throws MalformedURLException the malformed url exception
	 */
	@Before
	public void setUp() throws MalformedURLException{
		clientUrl = new URL("http://localhost:5555");
		model = new CommunicatorModel(messager, clientUrl, messageProvider, 
				connectionsContainer, usernameContainer, messagesSender,
				actorUsernameContainer, controller, talkStateDataFactory, 
				letterFactory, letterContainer, pendingLettersContainer );
	}
	
	/**
	 * Model passes connection to server request to internet messager.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void modelPassesConnectionToServerRequestToInternetMessager() throws Exception{
		final URL serverAddress = new URL("http://localhost:9090");
		context.checking(new Expectations(){{
			oneOf(messager).connectToServer(serverAddress);
		}});
		
		model.connectToServer(serverAddress);
		context.assertIsSatisfied();
	}
	
	
	/**
	 * After username is submitted it is passed to username container.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * After username is submitted and connection to server was established introductory message is sent.
	 *
	 * @throws Exception the exception
	 */
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

	
	/**
	 * After begin asked for talk state data model fetches that data and passes it to controller.
	 *
	 * @throws ParseException the parse exception
	 */
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
	
	/**
	 * After letter is written and connection with user is ok letter talk is sent.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * If letter was written but user is not connected this info is passed to controller.
	 *
	 * @throws Exception the exception
	 */
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
