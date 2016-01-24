package bc.bcCommunicator.Model.Messages;

import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMessage.
 */
public interface IMessage {
	
	/**
	 * Gets the message text.
	 *
	 * @return the message text
	 * @throws Exception the exception
	 */
	public String getMessageText() throws Exception; 
	
	/**
	 * Choose handler.
	 *
	 * @param handler the handler
	 * @param id the id
	 * @throws Exception the exception
	 */
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception;
}
