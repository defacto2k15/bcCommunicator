package bc.bcCommunicator.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
		if( usernames.values().contains(id)){
			throw new IllegalStateException("There is arleady one user with associated conenction id "+id);
		}
		usernames.put(name, id);
	}

	@Override
	public Username getUsernameForConnectionId(ConnectionId id) {
		List<Username> oneElementList = usernames.entrySet().stream()
				.filter( (entry) ->{ return entry.getValue() == id;} )
				.map( (entry)-> entry.getKey())
				.collect( Collectors.toList());
		if( oneElementList.isEmpty()){
			throw new IllegalArgumentException("There is no user with connectionId "+id);
		}
		if( oneElementList.size() > 1){
			throw new IllegalStateException("There is more than 1 users with connection id of "+id+". Strange That should be checked");
		}
		return oneElementList.get(0);
	}

}
