package bc.bcCommunicator.Model.Messages;

import java.io.StringWriter;
import java.net.URL;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Class AllUsersAddresses.
 */
public class AllUsersAddresses {
	
	/** The all users addresses. */
	private Map<Username, URL> allUsersAddresses;

	/**
	 * Instantiates a new all users addresses.
	 *
	 * @param allUsersAddresses the all users addresses
	 */
	public AllUsersAddresses( Map<Username, URL> allUsersAddresses ){
		this.allUsersAddresses = allUsersAddresses;
	}
	
	/**
	 * Gets the all users addresses.
	 *
	 * @return the all users addresses
	 */
	public Map<Username, URL> getAllUsersAddresses(){
		return allUsersAddresses;
	}
	
	/**
	 * Parses the to xml.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String parseToXml() throws Exception{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement(MessageConstants.USERNAME_ADDRESS_ROOT);
		doc.appendChild(rootElement);
		
		for( Username username : allUsersAddresses.keySet() ){
			Element newElement = doc.createElement(MessageConstants.USERNAME_ADDRESS_NODE);
			newElement.setAttribute(MessageConstants.USERNAME_NODE, username.getName());
			newElement.setAttribute(MessageConstants.ADDRESS_NODE, allUsersAddresses.get(username).toString());
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
}
