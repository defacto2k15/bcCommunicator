package bc.internetMessageProxy;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Interface IInternetMessageProxy.
 */
public interface IInternetMessageProxy {

	/**
	 * Send message.
	 *
	 * @param id the id
	 * @param message the message
	 * @return true, if successful
	 */
	boolean sendMessage(ConnectionId id, String message);

	/**
	 * Close connections.
	 */
	void closeConnections();

	/**
	 * Start listening.
	 *
	 * @param portNumber the port number
	 */
	void startListening(int portNumber);

	/**
	 * Start connection.
	 *
	 * @param url the url
	 * @return the optional
	 * @throws UnknownHostException the unknown host exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	Optional<ConnectionId> startConnection(URL url) throws UnknownHostException, IOException;

	/**
	 * Gets the message blocking.
	 *
	 * @return the message blocking
	 * @throws Exception the exception
	 */
	RecievedMessage getMessageBlocking() throws Exception;

	/**
	 * Gets the message blocking with timeout.
	 *
	 * @return the message blocking with timeout
	 * @throws Exception the exception
	 */
	RecievedMessage getMessageBlockingWithTimeout() throws Exception;

}