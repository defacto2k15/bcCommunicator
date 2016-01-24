package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.IMessage;

/**
 * The Interface IMessageInitializedFromFields. It gets FieldsContainer, analyzes it and returns concrete message
 */
public interface IMessageInitializedFromFields {
	IMessage getMessage( IFieldsContainer container ) throws Exception;

}
