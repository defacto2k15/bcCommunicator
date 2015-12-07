package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageTypeFieldValue;
import bc.bcCommunicator.Model.Messages.Response.ResponseMessageType;

public interface IMessageFromTypeCreator {

	IMessageInitializedFromFields get(IMessageTypeFieldValue responseType);

}
