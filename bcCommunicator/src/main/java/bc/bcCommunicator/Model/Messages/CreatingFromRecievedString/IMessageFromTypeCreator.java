package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageTypeFieldValue;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMessageFromTypeCreator. Generates Encapsulated message from messageTypeFieldValue, that encapsulated MessageType
 */
public interface IMessageFromTypeCreator {

	/**
	 * Gets the.
	 *
	 * @param responseType the response type
	 * @return the i message initialized from fields
	 */
	IMessageInitializedFromFields get(IMessageTypeFieldValue responseType);

}
