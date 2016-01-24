package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageTypeFieldValue;

public class MessageFromTypeCreator implements IMessageFromTypeCreator {

	@Override
	public IMessageInitializedFromFields get(IMessageTypeFieldValue messageType) {
		return messageType.getMessageType().getFromFieldsConstructor();
	}

}
