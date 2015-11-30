package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.RequestMessageType;
import bc.bcCommunicator.Model.Messages.ResponseMessageType;

public class ResponseMessageTypeFieldValue implements  IMessageFieldValue, IMessageTypeFieldValue{
	public ResponseMessageType type = ResponseMessageType.UsernameOk;
	
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.RESPONSE_TYPE_FIELD;
	}

	@Override
	public IMessageFieldValue createFromString(String text) {
		ResponseMessageTypeFieldValue newValue = new ResponseMessageTypeFieldValue();
		newValue.type = ResponseMessageType.valueOf(text);
		return newValue;
	}

	@Override
	public IMessageType getMessageType() {
		return type;
	}

}
