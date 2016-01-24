package bc.bcCommunicator.Client.Model;

import java.util.List;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

// TODO: Auto-generated Javadoc
/**
 * The Interface ILetterContainer.
 */
public interface ILetterContainer {
	
	/**
	 * Adds the letter of talk to user.
	 *
	 * @param user the user
	 * @param letter the letter
	 */
	public void addLetterOfTalkToUser( Username user, Letter letter );
	
	/**
	 * Gets the letters of talk to user.
	 *
	 * @param username the username
	 * @return the letters of talk to user
	 */
	public List<Letter> getLettersOfTalkToUser(Username username);
}
