package bc.bcCommunicator.Model;

import java.util.HashMap;
import java.util.Map;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.internetMessageProxy.ConnectionId;

public class ConnectionsContainer implements IConnectionsContainer {
	ConnectionId serverConnectionId;
	Map<Username, ConnectionId> usernames = new HashMap<>();
	
	@Override
	public ConnectionId getServerConnectionId() {
		if(serverConnectionId == null){
			throw new IllegalStateException("Server connection id is not set");
		}
		return serverConnectionId;
	}

	@Override
	public void setServerConnectionId(ConnectionId id) {
		serverConnectionId = id;
	}

	@Override
	public void removeServerConnectionIdIfExists() {
		serverConnectionId = null;
	}

	@Override
	public boolean isServerConnected() {
		return serverConnectionId!=null;
	}

	@Override
	public ConnectionId getConnectionIdOfUser(Username key) {
		if( usernames.containsKey(key) == false){
			throw new IllegalStateException("There is no connection id set for username "+key.getName());
		}
		return usernames.get(key);
	}

	@Override
	public void setIdForUser(Username name, ConnectionId id) {
		usernames.put(name, id);
	}

}
