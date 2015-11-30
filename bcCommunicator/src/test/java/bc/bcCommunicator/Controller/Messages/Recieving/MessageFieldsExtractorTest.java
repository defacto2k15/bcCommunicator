package bc.bcCommunicator.Controller.Messages.Recieving;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import static org.junit.Assert.*;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageFieldsValuesCreator;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFieldsExtractor;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UrlMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;

public class MessageFieldsExtractorTest {
	final JUnit4Mockery context  = new JUnit4Mockery();
	final IMessageFieldsValuesCreator valuesCreator = context.mock(IMessageFieldsValuesCreator.class);

	final MessageFieldsExtractor extractor = new MessageFieldsExtractor(valuesCreator);
	
	@Test
	public void parsingXmlWithOneFieldIsOk() throws Exception {
		UsernameMessageFieldValue value = new UsernameMessageFieldValue();
		String username = "SomeName";
		MessageField field = MessageField.USERNAME_FIELD;
		String xml =
					"<?xml version=\"1.0\"?>\n" + 
					"<Message>\n" + 
					"	<"+field.getFieldName()+" Value=\""+username+"\">\n" + 
					"	</"+field.getFieldName()+">\n"+	
					"</Message>\n";
		context.checking(new Expectations(){{
			oneOf(valuesCreator).create(MessageField.USERNAME_FIELD, username); will(returnValue(value));
		}});
		
		IFieldsContainer container = extractor.getFields(xml);
		UsernameMessageFieldValue result = container.<UsernameMessageFieldValue>getFieldValue(UsernameMessageFieldValue.class);
		assertEquals(value, result);
	}
	
	@Test
	public void parsingXmlWithUsernameAndUrlIsOk() throws Exception{
		UsernameMessageFieldValue usernameFieldValue = new UsernameMessageFieldValue();
		String username = "SomeName";
		UrlMessageFieldValue urlFieldValue = new UrlMessageFieldValue();
		String urlText = "http://Something.com";
		String xml =
				"<?xml version=\"1.0\"?>\n" + 
				"<Message>\n" + 
				"	<" +MessageField.USERNAME_FIELD.getFieldName()+" Value=\""+username+"\">\n" + 
				"	</"+MessageField.USERNAME_FIELD.getFieldName()+">\n"+	
				"	<" +MessageField.CLIENT_URL_FIELD.getFieldName()+" Value=\""+urlText+"\">\n" + 
				"	</"+MessageField.CLIENT_URL_FIELD.getFieldName()+">\n"+					
				"</Message>\n";
		context.checking(new Expectations(){{
			oneOf(valuesCreator).create(MessageField.USERNAME_FIELD, username); will(returnValue(usernameFieldValue));
			oneOf(valuesCreator).create(MessageField.CLIENT_URL_FIELD, urlText); will(returnValue(urlFieldValue));
		}});
		
		IFieldsContainer container = extractor.getFields(xml);
		Username uName = container.getFieldValue(UsernameMessageFieldValue.class).getUsername();
		assertEquals( usernameFieldValue, container.getFieldValue(UsernameMessageFieldValue.class));
		assertEquals( urlFieldValue, container.getFieldValue(UrlMessageFieldValue.class));
	}

}
