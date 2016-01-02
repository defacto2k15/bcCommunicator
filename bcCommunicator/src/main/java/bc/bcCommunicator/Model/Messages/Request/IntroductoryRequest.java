package bc.bcCommunicator.Model.Messages.Request;

import java.awt.TrayIcon.MessageType;
import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UrlMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;

public class IntroductoryRequest extends AbstractRequest{
	public IntroductoryRequest(Username username, URL clientUrl ) throws Exception{
		addField(RequestMessageType.IntroductoryRequestType);
		addField(username);
		addField(clientUrl);
	}
	
	public IntroductoryRequest( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(UsernameMessageFieldValue.class).getUsername(),
				container.getFieldValue(UrlMessageFieldValue.class).getUrl());
	}

}
