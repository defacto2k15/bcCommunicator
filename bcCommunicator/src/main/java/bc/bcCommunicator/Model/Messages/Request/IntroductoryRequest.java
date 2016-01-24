package bc.bcCommunicator.Model.Messages.Request;

import java.net.MalformedURLException;
import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UrlMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class IntroductoryRequest.
 */
public class IntroductoryRequest extends AbstractRequest implements IIntroductoryRequest{
	
	/**
	 * Instantiates a new introductory request.
	 *
	 * @param username the username
	 * @param clientUrl the client url
	 * @throws Exception the exception
	 */
	public IntroductoryRequest(Username username, URL clientUrl ) throws Exception{
		addField(RequestMessageType.IntroductoryRequestType);
		addField(username);
		addField(clientUrl);
	}
	
	/**
	 * Instantiates a new introductory request.
	 *
	 * @param container the container
	 * @throws Exception the exception
	 */
	public IntroductoryRequest( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(UsernameMessageFieldValue.class).getUsername(),
				container.getFieldValue(UrlMessageFieldValue.class).getUrl());
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Request.IIntroductoryRequest#getUsername()
	 */
	public Username getUsername(){
		return new Username( fieldsInMessage.get(MessageField.USERNAME_FIELD));
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Request.IIntroductoryRequest#getClientUrl()
	 */
	public URL getClientUrl(){
		try {
			return new URL(fieldsInMessage.get(MessageField.CLIENT_URL_FIELD));
		} catch (MalformedURLException e) {
			System.err.println("E002 malformed recieved url is "+fieldsInMessage.get(MessageField.CLIENT_URL_FIELD) );
			e.printStackTrace();
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.AbstractMessage#chooseHandler(bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler, bc.internetMessageProxy.ConnectionId)
	 */
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}

}
