package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

public interface IMessageFieldsValuesCreator {
	IMessageFieldValue create(MessageField field, String value ) throws Exception;
}
