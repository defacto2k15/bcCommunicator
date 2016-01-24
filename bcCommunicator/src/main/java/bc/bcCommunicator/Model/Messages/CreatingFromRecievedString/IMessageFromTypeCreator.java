package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageTypeFieldValue;

public interface IMessageFromTypeCreator {

	IMessageInitializedFromFields get(IMessageTypeFieldValue responseType);

}
