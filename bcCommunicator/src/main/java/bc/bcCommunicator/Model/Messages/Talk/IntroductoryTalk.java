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

// TODO: Auto-generated Javadoc
/**
 * The Class IntroductoryTalk.
 */
public class IntroductoryTalk extends AbstractTalk implements IIntroductoryTalk {
	
	/**
	 * Instantiates a new introductory talk.
	 *
	 * @param username the username
	 * @param clientUrl the client url
	 * @throws Exception the exception
	 */
	public IntroductoryTalk(Username username, URL clientUrl ) throws Exception{
		addField(TalkMessageType.IntroductoryTalkType);
		addField(username);
		addField(clientUrl);
	}
	
	/**
	 * Instantiates a new introductory talk.
	 *
	 * @param container the container
	 * @throws Exception the exception
	 */
	public IntroductoryTalk( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(UsernameMessageFieldValue.class).getUsername(),
				container.getFieldValue(UrlMessageFieldValue.class).getUrl());
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk#getUsername()
	 */
	@Override
	public Username getUsername(){
		return new Username( fieldsInMessage.get(MessageField.USERNAME_FIELD) );
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk#getUrl()
	 */
	@Override
	public URL getUrl() throws MalformedURLException{
		return new URL( fieldsInMessage.get(MessageField.CLIENT_URL_FIELD));
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.AbstractMessage#chooseHandler(bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}
	
}
