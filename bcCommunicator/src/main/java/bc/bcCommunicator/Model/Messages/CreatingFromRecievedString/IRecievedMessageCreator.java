package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.IMessage;

// TODO: Auto-generated Javadoc
/**
 * The Interface IRecievedMessageCreator.Creates message from string
 */
public interface IRecievedMessageCreator {

	/**
	 * Creates the message.
	 *
	 * @param message the message
	 * @return the i message
	 * @throws Exception the exception
	 */
	IMessage createMessage(String message) throws Exception;

}
