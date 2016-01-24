package bc.bcCommunicator.Server;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersContainer.
 */
public class UsersContainer {
	
	/** The users list. */
	private List<UserData> usersList = new ArrayList<>();

	/**
	 * Removes the user with connection id.
	 *
	 * @param id the id
	 */
	public void removeUserWithConnectionId(ConnectionId id) {
		List<UserData> usersWithThisId = usersList.stream().filter( u -> u.getId().equals( id)).collect(Collectors.toList());
		if( usersWithThisId.size() != 1){
			throw new IllegalStateException("There should be one user with id "+id+" but there is "+usersWithThisId.size());
		}
		usersList.removeAll(usersWithThisId);
	}

	/**
	 * Contains user with username.
	 *
	 * @param username the username
	 * @return true, if successful
	 */
	public boolean containsUserWithUsername(Username username) {
		return usersList.stream().filter( u -> u.getUsername().equals(username)).count() != 0;
	}

	/**
	 * Adds the user.
	 *
	 * @param username the username
	 * @param clientUrl the client url
	 * @param id the id
	 */
	public void addUser(Username username, URL clientUrl, ConnectionId id) {
		if( containsUserWithUsername(username) ){
			throw new IllegalArgumentException("There arleady is user with username "+username.getName()+" in container");
		}
		usersList.add( new UserData(id, username, clientUrl));
	}

	/**
	 * Gets the user with connection id.
	 *
	 * @param id the id
	 * @return the user with connection id
	 */
	public Username getUserWithConnectionId(ConnectionId id) {
		List<UserData> usersWithThisId = usersList.stream().filter( u -> u.getId().equals(id)).collect(Collectors.toList());
		if( usersWithThisId.size() != 1){
			throw new IllegalStateException("There should be one user with id "+id+" but there is "+usersWithThisId.size());
		}	
		return usersWithThisId.get(0).getUsername();
	}

	/**
	 * Gets the all users addresses map.
	 *
	 * @return the all users addresses map
	 */
	public Map<Username, URL> getAllUsersAddressesMap() {
		return usersList.stream().collect(Collectors.toMap(UserData::getUsername, UserData::getUrl));
	}
	
	/**
	 * The Class UserData.
	 */
	class UserData{
		
		/** The id. */
		private ConnectionId id;
		
		/** The username. */
		private Username username;
		
		/** The url. */
		private URL url;
		
		/**
		 * Instantiates a new user data.
		 *
		 * @param id the id
		 * @param username the username
		 * @param url the url
		 */
		public UserData(ConnectionId id, Username username, URL url) {
			this.id = id;
			this.username = username;
			this.url = url;
		}

		/**
		 * Gets the username.
		 *
		 * @return the username
		 */
		public Username getUsername() {
			return username;
		}

		/**
		 * Gets the url.
		 *
		 * @return the url
		 */
		public URL getUrl() {
			return url;
		}

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public ConnectionId getId() {
			return id;
		}
	}

}
