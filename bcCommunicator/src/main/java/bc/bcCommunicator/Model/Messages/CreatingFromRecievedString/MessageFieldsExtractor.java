package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import bc.bcCommunicator.Model.Messages.MessageConstants;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

public class MessageFieldsExtractor implements IMessageFieldsExtractor{

	private IMessageFieldsValuesCreator creator;

	public MessageFieldsExtractor( IMessageFieldsValuesCreator creator ){
		this.creator = creator;
	}
	
	@Override
	public IFieldsContainer getFields(String text) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(text));
		Document doc = dBuilder.parse(is);
				
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
		
		FieldsContainer container = new FieldsContainer();
		
		for( MessageField field : MessageField.values() ){
			NodeList list = doc.getElementsByTagName(field.getFieldName());
			if( list.getLength() > 1){
				throw new Exception("There is more than one field of type: "+field.getFieldName()+" in message "+text);
			} else if ( list.getLength() == 1 ){
				String value = list.item(0).getAttributes().getNamedItem(MessageConstants.VALUE_ATTRIBUTE_NAME).getNodeValue();
				if(value == null){
					throw new Exception("Error: while taking value attribute of node "+field+" the value was null");
				}
				IMessageFieldValue fieldValue = creator.create(field, value);
				container.addField(fieldValue);
			}
		}
		return container;
	}

}
