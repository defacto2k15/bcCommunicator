package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageTypeFieldValue;

/**
 * The Interface IMessageFromTypeCreator. Generates Encapsulated message from messageTypeFieldValue, that encapsulated MessageType
 */
public interface IMessageFromTypeCreator {

	IMessageInitializedFromFields get(IMessageTypeFieldValue responseType);

}
