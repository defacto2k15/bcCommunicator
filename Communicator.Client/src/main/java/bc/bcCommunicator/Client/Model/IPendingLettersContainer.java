package bc.bcCommunicator.Client.Model;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

// TODO: Auto-generated Javadoc
/**
 * The Interface IPendingLettersContainer.
 */
public interface IPendingLettersContainer {
	
	/**
	 * Adds the pending letter.
	 *
	 * @param username the username
	 * @param pendingLetter the pending letter
	 */
	void addPendingLetter( Username username, Letter pendingLetter);
	
	/**
	 * Checks if is pending letter avalible.
	 *
	 * @param username the username
	 * @return true, if is pending letter avalible
	 */
	boolean isPendingLetterAvalible( Username username );
	
	/**
	 * Gets the pending letter.
	 *
	 * @param username the username
	 * @return the pending letter
	 */
	Letter getPendingLetter( Username username);
}
