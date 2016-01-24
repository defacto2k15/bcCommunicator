package bc.bcCommunicator.Client.Model.Messages.Handling;

import java.util.Date;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.Model.ILetterContainer;
import bc.bcCommunicator.Client.Model.Messages.Handling.LetterTalkMessageHandler;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterSendingType;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.Talk.ILetterTalk;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterTalkMessageHandlerTest.
 */
public class LetterTalkMessageHandlerTest {
	
	/** The context. */
	private final Mockery context = new JUnit4Mockery();
	
	/** The container. */
	private final ILetterContainer container = context.mock(ILetterContainer.class);
	
	/** The controller. */
	private final ICommunicatorController controller = context.mock(ICommunicatorController.class);
	
	/** The handler. */
	private LetterTalkMessageHandler handler = new LetterTalkMessageHandler(container, controller);

	/** The connection id. */
	private final ConnectionId connectionId = new ConnectionId(99);

	/** The talk. */
	private final ILetterTalk talk = context.mock(ILetterTalk.class);
	
	/** The username. */
	Username username = new Username("SomeName");
	
	/** The letter. */
	private final Letter letter = new Letter(new LetterText("SomeText"), 
			new LetterDate(new Date()), username, new Username("reciever"),   LetterSendingType.Recieved);;
	
	/**
	 * Handler notifies controller of new letter.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void handlerNotifiesControllerOfNewLetter() throws Exception{
		context.checking( new Expectations(){{
			ignoring(container);
			
			oneOf(talk).getLetter(); will(returnValue(letter));
			oneOf(controller).recievedNewLetter(letter);
		}});
		
		handler.handle(talk, connectionId);
		context.assertIsSatisfied();
	}
	
	/**
	 * Handler puts letter to appripiate container.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void handlerPutsLetterToAppripiateContainer() throws Exception{
		context.checking( new Expectations(){{
			ignoring(controller);
			
			oneOf(talk).getLetter(); will(returnValue(letter));
			oneOf(container).addLetterOfTalkToUser(username, letter);
		}});
		
		handler.handle(talk, connectionId);
		context.assertIsSatisfied();
	}
}
