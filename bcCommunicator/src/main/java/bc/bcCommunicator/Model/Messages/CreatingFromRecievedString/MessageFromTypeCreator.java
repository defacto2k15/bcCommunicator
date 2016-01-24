package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageTypeFieldValue;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageFromTypeCreator.
 */
public class MessageFromTypeCreator implements IMessageFromTypeCreator {

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageFromTypeCreator#get(bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageTypeFieldValue)
	 */
	@Override
	public IMessageInitializedFromFields get(IMessageTypeFieldValue messageType) {
		return messageType.getMessageType().getFromFieldsConstructor();
	}

}
