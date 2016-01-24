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

public class LetterTalkMessageHandlerTest {
	private final Mockery context = new JUnit4Mockery();
	private final ILetterContainer container = context.mock(ILetterContainer.class);
	private final ICommunicatorController controller = context.mock(ICommunicatorController.class);
	private LetterTalkMessageHandler handler = new LetterTalkMessageHandler(container, controller);

	private final ConnectionId connectionId = new ConnectionId(99);

	private final ILetterTalk talk = context.mock(ILetterTalk.class);
	Username username = new Username("SomeName");
	private final Letter letter = new Letter(new LetterText("SomeText"), 
			new LetterDate(new Date()), username, new Username("reciever"),   LetterSendingType.Recieved);;
	
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
