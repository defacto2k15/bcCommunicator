package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.RequestMessageType;

public class RequestMessageTypeFieldValue implements IMessageFieldValue, IMessageTypeFieldValue {
	RequestMessageType type = RequestMessageType.IntroductoryRequestType;
	
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.REQUEST_TYPE_FIELD;
	}

	@Override
	public IMessageFieldValue createFromString(String text) {
		RequestMessageTypeFieldValue newValue = new RequestMessageTypeFieldValue();
		newValue.type = RequestMessageType.valueOf(text);
		return newValue;
	}

	@Override
	public IMessageType getMessageType() {
		return type;
	}
}
