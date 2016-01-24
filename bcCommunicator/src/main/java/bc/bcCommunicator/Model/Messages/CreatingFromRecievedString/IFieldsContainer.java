package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

// TODO: Auto-generated Javadoc
/**
 * The Interface IFieldsContainer. Contains all fields in message
 */
public interface IFieldsContainer {
	
	/**
	 * Gets the field value.
	 *
	 * @param <T> the generic type
	 * @param type the type
	 * @return the field value
	 * @throws Exception the exception
	 */
	<T extends IMessageFieldValue> T getFieldValue(Class<T> type) throws Exception;
	
	/**
	 * Contains field.
	 *
	 * @param <T> the generic type
	 * @param type the type
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	<T extends IMessageFieldValue> boolean containsField(Class<T> type) throws Exception;
}
