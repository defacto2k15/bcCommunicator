package bc.bcCommunicator.Client.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionsContainer.
 */
public class ConnectionsContainer implements IConnectionsContainer {
	
	/** The server connection id. */
	ConnectionId serverConnectionId;
	
	/** The usernames. */
	Map<Username, ConnectionId> usernames = new HashMap<>();
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IConnectionsContainer#getServerConnectionId()
	 */
	@Override
	public ConnectionId getServerConnectionId() {
		if(serverConnectionId == null){
			throw new IllegalStateException("Server connection id is not set");
		}
		return serverConnectionId;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IConnectionsContainer#setServerConnectionId(bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void setServerConnectionId(ConnectionId id) {
		serverConnectionId = id;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IConnectionsContainer#removeServerConnectionIdIfExists()
	 */
	@Override
	public void removeServerConnectionIdIfExists() {
		serverConnectionId = null;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IConnectionsContainer#isServerConnected()
	 */
	@Override
	public boolean isServerConnected() {
		return serverConnectionId!=null;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IConnectionsContainer#getConnectionIdOfUser(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public ConnectionId getConnectionIdOfUser(Username key) {
		if( usernames.containsKey(key) == false){
			throw new IllegalStateException("There is no connection id set for username "+key.getName());
		}
		return usernames.get(key);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IConnectionsContainer#setIdForUser(bc.bcCommunicator.Model.BasicTypes.Username, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void setIdForUser(Username name, ConnectionId id) {
		System.out.println("M465 settng id name: "+name.getName()+" id "+id.getId());
		if( usernames.values().contains(id)){
			throw new IllegalStateException("There is arleady one user with associated conenction id "+id);
		}
		usernames.put(name, id);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IConnectionsContainer#getUsernameForConnectionId(bc.internetMessageProxy.ConnectionId)
	 */
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

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IConnectionsContainer#isUserConnected(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public boolean isUserConnected(Username username) {
		return usernames.containsKey(username);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IConnectionsContainer#isThereUserWithThisConnectionId(bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public boolean isThereUserWithThisConnectionId(ConnectionId id) {
		return usernames.containsValue(id);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IConnectionsContainer#connectionLost(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void connectionLost(Username username) {
		if( usernames.containsKey(username) == false){
			throw new IllegalArgumentException("There is no connection of user "+username.getName());
		}
		
		usernames.remove(username);
	}

}
