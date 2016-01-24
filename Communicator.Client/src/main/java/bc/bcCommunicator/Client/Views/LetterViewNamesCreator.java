package bc.bcCommunicator.Client.Views;

import bc.bcCommunicator.Client.WindowNames;
import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterViewNamesCreator.
 */
public class LetterViewNamesCreator {
	
	/**
	 * Creates the letter viev name.
	 *
	 * @param username the username
	 * @param letterNo the letter no
	 * @return the string
	 */
	public static String createLetterVievName( Username username, int letterNo){
		return WindowNames.LETTER_VIEV_PANEL_NAME + username.getName()+letterNo;
	}
	
	/**
	 * Creates the letter view letter text name.
	 *
	 * @param inLetterText the in letter text
	 * @return the string
	 */
	public static String createLetterViewLetterTextName(String inLetterText) {
		String hash = WindowNames.LETTER_TEXT_NAME+(inLetterText).hashCode();
		System.out.println("Creted hash "+hash+ "from text "+inLetterText);
		return hash;
	}
}
