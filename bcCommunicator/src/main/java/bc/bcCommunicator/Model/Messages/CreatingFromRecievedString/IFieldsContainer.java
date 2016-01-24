package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

public interface IFieldsContainer {
	<T extends IMessageFieldValue> T getFieldValue(Class<T> type) throws Exception;
	<T extends IMessageFieldValue> boolean containsField(Class<T> type) throws Exception;
}
