package bc.bcCommunicator.Model;

import bc.internetMessageProxy.ConnectionId;

public interface IConnectionsContainer {

	ConnectionId getServerConnectionId();

	void setServerConnectionId(ConnectionId id);

	void removeServerConnectionIdIfExists();

	boolean isServerConnected();

}
