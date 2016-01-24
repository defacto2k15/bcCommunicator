package bc.bcCommunicator.Model.Messages.Letter;

import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating ILetter objects.
 */
public interface ILetterFactory {
	
	/**
	 * Creates the.
	 *
	 * @param text the text
	 * @param date the date
	 * @param sender the sender
	 * @param recipient the recipient
	 * @param type the type
	 * @return the letter
	 */
	Letter create(LetterText text, LetterDate date, Username sender, Username recipient, LetterSendingType type);
}
