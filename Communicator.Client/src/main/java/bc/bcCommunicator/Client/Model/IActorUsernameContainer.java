package bc.bcCommunicator.Client.Model;

import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Interface IActorUsernameContainer.
 */
public interface IActorUsernameContainer {
	
	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername( Username username);
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public Username getUsername();
	
	/**
	 * Checks if is username set.
	 *
	 * @return true, if is username set
	 */
	public boolean isUsernameSet();
}
