package bc.bcCommunicator.Model.Messages.Talk;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UrlMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;
import bc.bcCommunicator.Model.Messages.Request.RequestMessageType;

public class IntroductoryTalk extends AbstractTalk {
	public IntroductoryTalk(Username username, URL clientUrl ) throws Exception{
		addField(TalkMessageType.IntroductoryTalkType);
		addField(username);
		addField(clientUrl);
	}
	
	public IntroductoryTalk( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(UsernameMessageFieldValue.class).getUsername(),
				container.getFieldValue(UrlMessageFieldValue.class).getUrl());
	}
}
