package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.Request.RequestMessageType;

// TODO: Auto-generated Javadoc
/**
 * The Class RequestMessageTypeFieldValue.
 */
public class RequestMessageTypeFieldValue implements IMessageFieldValue, IMessageTypeFieldValue {
	
	/** The type. */
	RequestMessageType type = RequestMessageType.IntroductoryRequestType;
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#getCorrespondingField()
	 */
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.REQUEST_TYPE_FIELD;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#createFromString(java.lang.String)
	 */
	@Override
	public IMessageFieldValue createFromString(String text) {
		RequestMessageTypeFieldValue newValue = new RequestMessageTypeFieldValue();
		newValue.type = RequestMessageType.valueOf(text);
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
