package bc.bcCommunicator.Model;

import java.net.URL;
import java.nio.channels.IllegalSelectorException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;

public class OtherUsersDataContainer implements IOtherUsersDataContainer {
	Map<Username, URL> usernameWithAddress = new HashMap<>();
	

	@Override
	public AllUsersAddresses getUsernamesWithAddresses() {
		return new AllUsersAddresses(usernameWithAddress);
	}

	@Override
	public void addUserWithAddress(Username username, URL address) {
		if( usernameWithAddress.containsKey(username)){
			throw new IllegalArgumentException("There arleady is username: "+username.getName());
		}
		if( usernameWithAddress.containsValue(address) ){
			throw new IllegalStateException("There is arleady one username conencted with address "+address);
		}
		usernameWithAddress.put(username, address);
	}

	@Override
	public Username getUsernameForAddress(URL address) {
		List<Username> outList = usernameWithAddress.keySet().stream().filter( (oneKey)->{ return usernameWithAddress.get(oneKey).equals(address); }).collect( Collectors.toList());
		if( outList.isEmpty()){
			throw new IllegalStateException("There is not username for address "+address);
		}
		if( outList.size() > 1){
			throw new IllegalStateException("There was more than one user with the same address: "+address+" ??. It should have been checked when adding");
		}	
		return outList.get(0);
	}

	@Override
	public boolean isUsernameInDatabase(Username username) {
		return usernameWithAddress.containsKey(username);
	}

	@Override
	public void updateUrlOfUser(Username username, URL address) {
		if( usernameWithAddress.containsKey(username) == false ){
			throw new IllegalArgumentException("There is no data about user "+username.getName()+" set");
		}
		usernameWithAddress.put(username, address);
	}
	

}
