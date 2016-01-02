package bc.bcCommunicator.Model;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;

public class UsernameContainer implements IUsernameContainer {
	Username username;
	Map<Username, URL> usernameWithAddress = new HashMap<>();
	
	@Override
	public void setUsername(Username username) {
		this.username = username;
	}

	@Override
	public Username getUsername() {
		if(username == null){
			throw new IllegalStateException("Username was not set");
		}
		return username;
	}

	@Override
	public boolean isUsernameSet() {
		return !(username==null);
	}

	@Override
	public AllUsersAddresses getUsernamesWithAddresses() {
		return new AllUsersAddresses(usernameWithAddress);
	}

	@Override
	public void addUserWithAddress(Username username, URL address) {
		if( usernameWithAddress.containsKey(username)){
			throw new IllegalArgumentException("There arleady is username: "+username.getName());
		}
		usernameWithAddress.put(username, address);
	}

}
