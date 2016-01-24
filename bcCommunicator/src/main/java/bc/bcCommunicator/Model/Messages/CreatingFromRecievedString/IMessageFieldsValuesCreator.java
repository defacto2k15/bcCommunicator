package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMessageFieldsValuesCreator. Creates IMessageFieldValue from value as string and messageField which is enum
 */
public interface IMessageFieldsValuesCreator {
	
	/**
	 * Creates the.
	 *
	 * @param field the field
	 * @param value the value
	 * @return the i message field value
	 * @throws Exception the exception
	 */
	IMessageFieldValue create(MessageField field, String value ) throws Exception;
}
