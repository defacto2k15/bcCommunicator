package bc.bcCommunicator.Model.Messages.Recieving;

import java.net.Authenticator.RequestorType;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageFieldsExtractor;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageFromTypeCreator;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.RecievedMessageCreator;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.ResponseMessageTypeFieldValue;
import bc.bcCommunicator.Model.Messages.Request.RequestMessageType;
import bc.bcCommunicator.Model.Messages.Response.ResponseMessageType;

import static org.junit.Assert.*;

public class RecievedMessageCreatorTest {
	private final Mockery context = new JUnit4Mockery();
	private final IMessageFieldsExtractor extractor = context.mock(IMessageFieldsExtractor.class);
	private final IFieldsContainer fieldsContainer = context.mock(IFieldsContainer.class);
	private final String messageText = "SOME_TEXT";
	private final IMessageFromTypeCreator fromTypeCreator = context.mock(IMessageFromTypeCreator.class);
	private final IMessageInitializedFromFields notInitializedMessage = context.mock(IMessageInitializedFromFields.class);
	private final IMessage messageReturned = context.mock(IMessage.class);
	private final RecievedMessageCreator creator = new RecievedMessageCreator( extractor, fromTypeCreator );
	
	@Test
	public void weCreateAndReturnRequestMessage() throws Exception{
		final ResponseMessageTypeFieldValue typeValue = new ResponseMessageTypeFieldValue();
		typeValue.type = ResponseMessageType.UsernameOk;
		context.checking(new Expectations(){{
			oneOf(extractor).getFields(messageText); will(returnValue(fieldsContainer));
			oneOf(fieldsContainer).getFieldValue(ResponseMessageTypeFieldValue.class); will(returnValue(typeValue));
			oneOf(fromTypeCreator).get(typeValue); will(returnValue(notInitializedMessage));
			oneOf(notInitializedMessage).getMessage(fieldsContainer); will(returnValue(messageReturned));
		}});
		IMessage result = creator.createMessage(messageText);
		assertEquals(messageReturned, result );
		context.assertIsSatisfied();
	}
	

	// TODO: handling chatMessage
	// TODO: what if type of message is bad or exception is thrown when not all fields is in container when message is trying to initialize itself?
}
