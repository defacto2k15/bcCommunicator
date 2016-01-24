package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

/**
 * The Interface IFieldsContainer. Contains all fields in message
 */
public interface IFieldsContainer {
	<T extends IMessageFieldValue> T getFieldValue(Class<T> type) throws Exception;
	<T extends IMessageFieldValue> boolean containsField(Class<T> type) throws Exception;
}
