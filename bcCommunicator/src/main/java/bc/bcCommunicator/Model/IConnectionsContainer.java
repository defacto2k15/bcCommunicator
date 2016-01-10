package bc.bcCommunicator.Model;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.internetMessageProxy.ConnectionId;

public interface IConnectionsContainer {

	ConnectionId getServerConnectionId();

	void setServerConnectionId(ConnectionId id);

	void removeServerConnectionIdIfExists();

	boolean isServerConnected();

	ConnectionId getConnectionIdOfUser(Username key);
	
	Username getUsernameForConnectionId( ConnectionId id);

	void setIdForUser(Username name, ConnectionId id);
	
	boolean isUserConnected( Username username);
	
	boolean isThereUserWithThisConnectionId( ConnectionId id);

}
