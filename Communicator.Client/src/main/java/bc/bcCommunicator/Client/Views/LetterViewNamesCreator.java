package bc.bcCommunicator.Client.Views;

import bc.bcCommunicator.Client.WindowNames;
import bc.bcCommunicator.Model.BasicTypes.Username;

public class LetterViewNamesCreator {
	public static String createLetterVievName( Username username, int letterNo){
		return WindowNames.LETTER_VIEV_PANEL_NAME + username.getName()+letterNo;
	}
	
	public static String createLetterViewLetterTextName(String inLetterText) {
		String hash = WindowNames.LETTER_TEXT_NAME+(inLetterText).hashCode();
		System.out.println("Creted hash "+hash+ "from text "+inLetterText);
		return hash;
	}
}
