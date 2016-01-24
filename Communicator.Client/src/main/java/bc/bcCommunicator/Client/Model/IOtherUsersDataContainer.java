package bc.bcCommunicator.Client.Model;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;

// TODO: Auto-generated Javadoc
/**
 * The Interface IOtherUsersDataContainer.
 */
public interface IOtherUsersDataContainer {

	/**
	 * Gets the usernames with addresses.
	 *
	 * @return the usernames with addresses
	 */
	public AllUsersAddresses getUsernamesWithAddresses();

	/**
	 * Adds the user with address.
	 *
	 * @param username the username
	 * @param address the address
	 */
	void addUserWithAddress(Username username, URL address);
	
	/**
	 * Gets the username for address.
	 *
	 * @param address the address
	 * @return the username for address
	 */
	Username getUsernameForAddress( URL address);

	/**
	 * Checks if is username in database.
	 *
	 * @param username the username
	 * @return true, if is username in database
	 */
	boolean isUsernameInDatabase(Username username);

	/**
	 * Update url of user.
	 *
	 * @param username the username
	 * @param address the address
	 */
	public void updateUrlOfUser(Username username, URL address);
}
