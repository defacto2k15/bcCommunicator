package bc.bcCommunicator.Model.Messages.Talk;

import java.text.ParseException;

import bc.bcCommunicator.Model.Messages.Letter.Letter;

// TODO: Auto-generated Javadoc
/**
 * The Interface ILetterTalk.
 */
public interface ILetterTalk {
	
	/**
	 * Gets the letter.
	 *
	 * @return the letter
	 * @throws ParseException the parse exception
	 */
	Letter getLetter() throws ParseException;
}
