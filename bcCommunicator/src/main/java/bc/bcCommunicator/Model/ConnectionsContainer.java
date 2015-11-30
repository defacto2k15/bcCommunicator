package bc.bcCommunicator.Model;

import bc.internetMessageProxy.ConnectionId;

public class ConnectionsContainer implements IConnectionsContainer {
	ConnectionId serverConnectionId;
	
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

}
