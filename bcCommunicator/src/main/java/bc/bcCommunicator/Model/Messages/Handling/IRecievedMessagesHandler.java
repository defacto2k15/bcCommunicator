package bc.bcCommunicator.Model.Messages.Handling;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Interface IRecievedMessagesHandler.
 */
public interface IRecievedMessagesHandler {
	
	/**
	 * Handle.
	 *
	 * @param message the message
	 * @param id the id
	 */
	void handle(IMessage message, ConnectionId id) ;

}
