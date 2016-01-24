package bc.bcCommunicator.Model.Messages.Talk;

import java.net.MalformedURLException;
import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UrlMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;
import bc.internetMessageProxy.ConnectionId;

public class IntroductoryTalk extends AbstractTalk implements IIntroductoryTalk {
	public IntroductoryTalk(Username username, URL clientUrl ) throws Exception{
		addField(TalkMessageType.IntroductoryTalkType);
		addField(username);
		addField(clientUrl);
	}
	
	public IntroductoryTalk( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(UsernameMessageFieldValue.class).getUsername(),
				container.getFieldValue(UrlMessageFieldValue.class).getUrl());
	}
	
	@Override
	public Username getUsername(){
		return new Username( fieldsInMessage.get(MessageField.USERNAME_FIELD) );
	}

	@Override
	public URL getUrl() throws MalformedURLException{
		return new URL( fieldsInMessage.get(MessageField.CLIENT_URL_FIELD));
	}
	
	@Override
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}
	
}
