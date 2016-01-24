package bc.bcCommunicator.Client.Views;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating ILetterView objects.
 */
public interface ILetterViewFactory {
	
	/**
	 * Gets the letter view.
	 *
	 * @param username the username
	 * @param inLetterText the in letter text
	 * @param dateText the date text
	 * @param alignLeft the align left
	 * @return the letter view
	 */
	ILetterView getLetterView(String username, String inLetterText, String dateText, boolean alignLeft);
}
