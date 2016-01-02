package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.IMessage;

public interface IRecievedMessageCreator {

	IMessage createMessage(String message) throws Exception;

}
