package bc.bcCommunicator.Model.Internet;

import java.net.URL;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Interface IInternetMessagerCommandProvider.
 */
public interface IInternetMessagerCommandProvider {

	/**
	 * Gets the connect to server command.
	 *
	 * @param serverAddress the server address
	 * @return the connect to server command
	 */
	IInternetMessagerCommand getConnectToServerCommand(URL serverAddress);

	/**
	 * Gets the connection lost command.
	 *
	 * @param id the id
	 * @return the connection lost command
	 */
	IInternetMessagerCommand getConnectionLostCommand(ConnectionId id);

	/**
	 * Gets the send message command.
	 *
	 * @param id the id
	 * @param message the message
	 * @return the send message command
	 */
	IInternetMessagerCommand getSendMessageCommand(ConnectionId id, IMessage message);

	/**
	 * Gets the connect to user command.
	 *
	 * @param oneAddress the one address
	 * @return the connect to user command
	 */
	IInternetMessagerCommand getConnectToUserCommand(URL oneAddress);

	/**
	 * Gets the listen on port command.
	 *
	 * @param clientPort the client port
	 * @return the listen on port command
	 */
	IInternetMessagerCommand getListenOnPortCommand(int clientPort);

}
