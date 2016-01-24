package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

/**
 * The Interface IMessageFieldValueConstructor. Interface with factory method
 */
public interface IMessageFieldValueConstructor {
	IMessageFieldValue create();
}
