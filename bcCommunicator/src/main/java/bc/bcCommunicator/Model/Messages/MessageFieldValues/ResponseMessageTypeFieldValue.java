package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.Response.ResponseMessageType;

// TODO: Auto-generated Javadoc
/**
 * The Class ResponseMessageTypeFieldValue.
 */
public class ResponseMessageTypeFieldValue implements  IMessageFieldValue, IMessageTypeFieldValue{
	
	/** The type. */
	public ResponseMessageType type = ResponseMessageType.UsernameOk;
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#getCorrespondingField()
	 */
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.RESPONSE_TYPE_FIELD;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#createFromString(java.lang.String)
	 */
	@Override
	public IMessageFieldValue createFromString(String text) {
		ResponseMessageTypeFieldValue newValue = new ResponseMessageTypeFieldValue();
		newValue.type = ResponseMessageType.valueOf(text);
		return newValue;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageTypeFieldValue#getMessageType()
	 */
	@Override
	public IMessageType getMessageType() {
		return type;
	}

}
