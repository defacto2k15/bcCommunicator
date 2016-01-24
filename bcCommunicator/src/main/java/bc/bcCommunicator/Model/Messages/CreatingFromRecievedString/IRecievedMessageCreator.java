package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.IMessage;

/**
 * The Interface IRecievedMessageCreator.Creates message from string
 */
public interface IRecievedMessageCreator {

	IMessage createMessage(String message) throws Exception;

}
