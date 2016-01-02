package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.Response.ResponseMessageType;
import bc.bcCommunicator.Model.Messages.Talk.TalkMessageType;

public class TalkMessageTypeFieldValue implements  IMessageFieldValue, IMessageTypeFieldValue {

	public TalkMessageType type = TalkMessageType.IntroductoryTalkType;
	
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.TALK_TYPE_FIELD;
	}

	@Override
	public IMessageFieldValue createFromString(String text) {
		TalkMessageTypeFieldValue newValue = new TalkMessageTypeFieldValue();
		newValue.type = TalkMessageType.valueOf(text);
		return newValue;
	}

	@Override
	public IMessageType getMessageType() {
		return type;
	}
}
