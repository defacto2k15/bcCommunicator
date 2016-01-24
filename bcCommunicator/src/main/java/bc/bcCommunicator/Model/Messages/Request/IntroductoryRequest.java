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

public class IntroductoryRequest extends AbstractRequest implements IIntroductoryRequest{
	public IntroductoryRequest(Username username, URL clientUrl ) throws Exception{
		addField(RequestMessageType.IntroductoryRequestType);
		addField(username);
		addField(clientUrl);
	}
	
	public IntroductoryRequest( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(UsernameMessageFieldValue.class).getUsername(),
				container.getFieldValue(UrlMessageFieldValue.class).getUrl());
	}
	
	public Username getUsername(){
		return new Username( fieldsInMessage.get(MessageField.USERNAME_FIELD));
	}
	
	public URL getClientUrl(){
		try {
			return new URL(fieldsInMessage.get(MessageField.CLIENT_URL_FIELD));
		} catch (MalformedURLException e) {
			System.err.println("E002 malformed recieved url is "+fieldsInMessage.get(MessageField.CLIENT_URL_FIELD) );
			e.printStackTrace();
		}
		return null;
	}
	
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}

}
