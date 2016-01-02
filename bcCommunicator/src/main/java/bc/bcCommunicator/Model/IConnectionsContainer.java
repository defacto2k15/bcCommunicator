package bc.bcCommunicator.Model;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.internetMessageProxy.ConnectionId;

public interface IConnectionsContainer {

	ConnectionId getServerConnectionId();

	void setServerConnectionId(ConnectionId id);

	void removeServerConnectionIdIfExists();

	boolean isServerConnected();

	ConnectionId getConnectionIdOfUser(Username key);

	void setIdForUser(Username name, ConnectionId id);

}