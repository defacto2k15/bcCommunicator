package bc.bcCommunicator.Model.Messages;

import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;

public interface IMessageType {
	public String getTypeName();

	public IMessageInitializedFromFields getFromFieldsConstructor();
}
