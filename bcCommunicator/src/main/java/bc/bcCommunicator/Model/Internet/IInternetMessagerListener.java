package bc.bcCommunicator.Model.Internet;

import java.net.URL;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

/**
 *  Class which methods will be called by IInternetMessager when some events occur
 */
public interface IInternetMessagerListener {

	/**
	 * Message was recieved.
	 *
	 * @param recievedMessage the recieved message
	 * @param connectionId the connection id of connection from which we recieved message
	 */
	void messageWasRecieved(IMessage recievedMessage, ConnectionId connectionId);

	/**
	 * Server connection failed, maybe server is down, maybe internet connection is down
	 */
	void serverConnectionFailed();

	/**
	 * Server connection was succesfull.
	 *
	 * @param connectionId the connection id of newly created connection
	 */
	void serverConnectionWasSuccesfull(ConnectionId connectionId) throws Exception;

	/**
	 * Connection lost.
	 *
	 * @param id the id of connection that failed
	 */
	void connectionLost(ConnectionId id);

	/**
	 * Message sent succesfully.
	 *
	 * @param id the id of connection that we sent message through
	 */
	void messageSentSuccesfully(ConnectionId id) throws Exception;

	/**
	 * Message sending failed.
	 *
	 * @param id the id of connection in which we failed to send message
	 */
	void messageSendingFailed(ConnectionId id);

	/**
	 * User connection failed.
	 *
	 * @param userAddress the  address to which we failed to connect to
	 */
	void userConnectionFailed(URL userAddress);

	/**
	 * User connection was succesfull.
	 *
	 * @param userAddress the  address  to which we succeded to connect to
	 * @param connectionId the connection id of newly created connection
	 */
	void userConnectionWasSuccesfull(URL userAddress, ConnectionId connectionId) throws Exception;

}
