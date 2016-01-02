package bc.bcCommunicator.Model;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;

public interface IOtherUsersDataContainer {

	public AllUsersAddresses getUsernamesWithAddresses();

	void addUserWithAddress(Username username, URL address);
	
	Username getUsernameForAddress( URL address);
}
