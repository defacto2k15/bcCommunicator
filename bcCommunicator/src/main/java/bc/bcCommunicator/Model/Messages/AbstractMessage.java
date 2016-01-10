package bc.bcCommunicator.Model.Messages;

import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.Request.RequestMessageType;
import bc.bcCommunicator.Model.Messages.Response.ResponseMessageType;
import bc.bcCommunicator.Model.Messages.Talk.TalkMessageType;
import bc.internetMessageProxy.ConnectionId;

public abstract class AbstractMessage< ConcreteMessageType extends IMessageType > implements IMessage {

	protected HashMap<MessageField, String> fieldsInMessage = new HashMap<MessageField, String>();
	
	protected void addField(Username name) throws Exception{
		fieldsInMessage.put(MessageField.USERNAME_FIELD, name.getName());
	}
	
	protected void addField(URL clientUrl) throws Exception{
		fieldsInMessage.put(MessageField.CLIENT_URL_FIELD, clientUrl.toString());
	}
	
	/*void addField(ConcreteMessageType messageType){
		fieldsInMessage.put(MessageField.MESSAGE_TYPE_FIELD, messageType.getTypeName());
	}*/
	
	protected void addField(RequestMessageType messageType) throws Exception{
		fieldsInMessage.put(MessageField.REQUEST_TYPE_FIELD, messageType.getTypeName());
	}
	
	protected void addField(ResponseMessageType messageType) throws Exception{
		fieldsInMessage.put(MessageField.RESPONSE_TYPE_FIELD, messageType.getTypeName());
	}
	
	protected void addField(TalkMessageType messageType ) throws Exception {
		fieldsInMessage.put(MessageField.TALK_TYPE_FIELD, messageType.getTypeName());
	}
	
	protected void addField( AllUsersAddresses allUsersAddresses ) throws Exception{
		fieldsInMessage.put(MessageField.AllUsersAddresses, allUsersAddresses.parseToXml());
	}
	
	protected void addField( LetterDate date ) throws Exception{
		fieldsInMessage.put(MessageField.DATE_FIELD, date.getDateAsString());
	}	
	
	protected void addField( LetterText text ) throws Exception{
		fieldsInMessage.put(MessageField.LETTER_TEXT_FIELD , text.getTextValue());
	}	
	
	
	@Override
	public String getMessageText() throws Exception {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(MessageConstants.MESSAGE_ROOT);
		doc.appendChild(rootElement);
		
		for( MessageField field : fieldsInMessage.keySet() ){
			Element newElement = doc.createElement(field.getFieldName());
			newElement.setAttribute(MessageConstants.VALUE_ATTRIBUTE_NAME, fieldsInMessage.get(field));
			rootElement.appendChild(newElement);
		}
		DOMSource domSource = new DOMSource(doc);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(domSource, result);
		return writer.toString();
	}

	@Override
	public void chooseHandler(AbstractMessageHandler handler, ConnectionId id) throws Exception {
		System.err.println("Message was asked to chose handler, but it doesnt select any handler. Method from abstract class is called");
		assert false;
	}
		
}
