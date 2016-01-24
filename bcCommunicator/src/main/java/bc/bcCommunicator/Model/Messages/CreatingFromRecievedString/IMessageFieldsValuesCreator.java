package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

/**
 * The Interface IMessageFieldsValuesCreator. Creates IMessageFieldValue from value as string and messageField which is enum
 */
public interface IMessageFieldsValuesCreator {
	IMessageFieldValue create(MessageField field, String value ) throws Exception;
}
