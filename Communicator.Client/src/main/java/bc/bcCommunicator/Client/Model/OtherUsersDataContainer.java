package bc.bcCommunicator.Client.Model;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;

// TODO: Auto-generated Javadoc
/**
 * The Class OtherUsersDataContainer.
 */
public class OtherUsersDataContainer implements IOtherUsersDataContainer {
	
	/** The username with address. */
	Map<Username, URL> usernameWithAddress = new HashMap<>();
	

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IOtherUsersDataContainer#getUsernamesWithAddresses()
	 */
	@Override
	public AllUsersAddresses getUsernamesWithAddresses() {
		return new AllUsersAddresses(usernameWithAddress);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IOtherUsersDataContainer#addUserWithAddress(bc.bcCommunicator.Model.BasicTypes.Username, java.net.URL)
	 */
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

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IOtherUsersDataContainer#getUsernameForAddress(java.net.URL)
	 */
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

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IOtherUsersDataContainer#isUsernameInDatabase(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public boolean isUsernameInDatabase(Username username) {
		return usernameWithAddress.containsKey(username);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IOtherUsersDataContainer#updateUrlOfUser(bc.bcCommunicator.Model.BasicTypes.Username, java.net.URL)
	 */
	@Override
	public void updateUrlOfUser(Username username, URL address) {
		if( usernameWithAddress.containsKey(username) == false ){
			throw new IllegalArgumentException("There is no data about user "+username.getName()+" set");
		}
		usernameWithAddress.put(username, address);
	}
	

}
