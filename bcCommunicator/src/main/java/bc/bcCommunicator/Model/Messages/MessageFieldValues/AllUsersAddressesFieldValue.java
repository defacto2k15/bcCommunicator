package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.MessageConstants;
import bc.bcCommunicator.Model.Messages.MessageField;

// TODO: Auto-generated Javadoc
/**
 * The Class AllUsersAddressesFieldValue.
 */
public class AllUsersAddressesFieldValue implements IMessageFieldValue {
	
	/** The users addresses. */
	AllUsersAddresses usersAddresses = null;
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#getCorrespondingField()
	 */
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.AllUsersAddresses;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#createFromString(java.lang.String)
	 */
	@Override
	public IMessageFieldValue createFromString(String text) throws Exception {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(text));
		Document doc = dBuilder.parse(is);
				
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
		
		Map<Username, URL> map = new HashMap<>();
		
		NodeList list = doc.getElementsByTagName(MessageConstants.USERNAME_ADDRESS_NODE);
		if( list.getLength() == 0){
			System.out.println("W003 No Username-Addresses pairs in recieved response");
		} 
		for( int i = 0; i < list.getLength(); i++){
			Node node = list.item(i);
			
			NamedNodeMap attributes = node.getAttributes();
			Node usernameNode = attributes.getNamedItem(MessageConstants.USERNAME_NODE);
			if( usernameNode == null){
				throw new Exception("There is no username attribute in node");
			}
			
			Node addressNode = attributes.getNamedItem(MessageConstants.ADDRESS_NODE);
			if( addressNode == null ){
				throw new Exception("There is no address attribute in node");
			}
	

			map.put(new Username(usernameNode.getNodeValue()), new URL( addressNode.getNodeValue()));
		}
		usersAddresses = new AllUsersAddresses(map);
		AllUsersAddressesFieldValue newValue = new AllUsersAddressesFieldValue();
		newValue.usersAddresses = usersAddresses;
		return newValue;
	}
	
	/**
	 * Gets the users addresses.
	 *
	 * @return the users addresses
	 */
	public AllUsersAddresses getUsersAddresses(){
		return usersAddresses;
	}
}