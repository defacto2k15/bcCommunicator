package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.IMessage;

public interface IMessageInitializedFromFields {
	IMessage getMessage( IFieldsContainer container ) throws Exception;

}
