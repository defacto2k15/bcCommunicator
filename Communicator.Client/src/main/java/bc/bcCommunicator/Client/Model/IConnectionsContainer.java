package bc.bcCommunicator.Client.Model;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Interface IConnectionsContainer.
 */
public interface IConnectionsContainer {

	/**
	 * Gets the server connection id.
	 *
	 * @return the server connection id
	 */
	ConnectionId getServerConnectionId();

	/**
	 * Sets the server connection id.
	 *
	 * @param id the new server connection id
	 */
	void setServerConnectionId(ConnectionId id);

	/**
	 * Removes the server connection id if exists.
	 */
	void removeServerConnectionIdIfExists();

	/**
	 * Checks if is server connected.
	 *
	 * @return true, if is server connected
	 */
	boolean isServerConnected();

	/**
	 * Gets the connection id of user.
	 *
	 * @param key the key
	 * @return the connection id of user
	 */
	ConnectionId getConnectionIdOfUser(Username key);
	
	/**
	 * Gets the username for connection id.
	 *
	 * @param id the id
	 * @return the username for connection id
	 */
	Username getUsernameForConnectionId( ConnectionId id);

	/**
	 * Sets the id for user.
	 *
	 * @param name the name
	 * @param id the id
	 */
	void setIdForUser(Username name, ConnectionId id);
	
	/**
	 * Checks if is user connected.
	 *
	 * @param username the username
	 * @return true, if is user connected
	 */
	boolean isUserConnected( Username username);
	
	/**
	 * Checks if is there user with this connection id.
	 *
	 * @param id the id
	 * @return true, if is there user with this connection id
	 */
	boolean isThereUserWithThisConnectionId( ConnectionId id);

	/**
	 * Connection lost.
	 *
	 * @param someUserUsername the some user username
	 */
	void connectionLost(Username someUserUsername);

}
