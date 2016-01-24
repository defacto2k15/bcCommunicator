package bc.bcCommunicator.Model.Internet;

import java.net.URL;

import bc.internetMessageProxy.ConnectionId;

/**
 * The Interface IInternetMessager. it sends messages ( but as strings ), connects and disconects, and generally talk with internetProxy
 */
public interface IInternetMessager {

	/**
	 * Connect to server.
	 *
	 * @param serverAddress the server address
	 */
	void connectToServer(URL serverAddress) throws Exception;

	/**
	 * Connection lost, this method is usually called by internetProxy
	 *
	 * @param id the id of connection that was lost
	 */
	void connectionLost(ConnectionId id);

	/**
	 * Send message.
	 *
	 * @param id the id of connection message will be sent through.
	 * @param messageText the message text
	 */
	void sendMessage(ConnectionId id, String messageText);

	/**
	 * Connect to user.
	 *
	 * @param oneAddress the address of user
	 */
	void connectToUser(URL oneAddress);

	/**
	 * Start listening on port.
	 *
	 * @param clientPort the client port
	 */
	void listenOnPort(int clientPort);
	
}