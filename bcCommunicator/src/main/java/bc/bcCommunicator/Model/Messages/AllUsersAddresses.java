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

public class AllUsersAddresses {
	private Map<Username, URL> allUsersAddresses;

	public AllUsersAddresses( Map<Username, URL> allUsersAddresses ){
		this.allUsersAddresses = allUsersAddresses;
	}
	
	public Map<Username, URL> getAllUsersAddresses(){
		return allUsersAddresses;
	}
	
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
