package bc.bcCommunicator.Server;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.internetMessageProxy.ConnectionId;

public class UsersContainer {
	private List<UserData> usersList = new ArrayList<>();

	public void removeUserWithConnectionId(ConnectionId id) {
		List<UserData> usersWithThisId = usersList.stream().filter( u -> u.getId().equals( id)).collect(Collectors.toList());
		if( usersWithThisId.size() != 1){
			throw new IllegalStateException("There should be one user with id "+id+" but there is "+usersWithThisId.size());
		}
		usersList.removeAll(usersWithThisId);
	}

	public boolean containsUserWithUsername(Username username) {
		return usersList.stream().filter( u -> u.getUsername().equals(username)).count() != 0;
	}

	public void addUser(Username username, URL clientUrl, ConnectionId id) {
		if( containsUserWithUsername(username) ){
			throw new IllegalArgumentException("There arleady is user with username "+username.getName()+" in container");
		}
		usersList.add( new UserData(id, username, clientUrl));
	}

	public Username getUserWithConnectionId(ConnectionId id) {
		List<UserData> usersWithThisId = usersList.stream().filter( u -> u.getId().equals(id)).collect(Collectors.toList());
		if( usersWithThisId.size() != 1){
			throw new IllegalStateException("There should be one user with id "+id+" but there is "+usersWithThisId.size());
		}	
		return usersWithThisId.get(0).getUsername();
	}

	public Map<Username, URL> getAllUsersAddressesMap() {
		return usersList.stream().collect(Collectors.toMap(UserData::getUsername, UserData::getUrl));
	}
	
	class UserData{
		private ConnectionId id;
		private Username username;
		private URL url;
		
		public UserData(ConnectionId id, Username username, URL url) {
			this.id = id;
			this.username = username;
			this.url = url;
		}

		public Username getUsername() {
			return username;
		}

		public URL getUrl() {
			return url;
		}

		public ConnectionId getId() {
			return id;
		}
	}

}
