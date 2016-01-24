package bc.bcCommunicator.Client.Views;

// TODO: Auto-generated Javadoc
/**
 * The Enum UsernameInputStatus.
 */
public enum UsernameInputStatus{
	
	/** The Username not inserted. */
	UsernameNotInserted("Insert username"),
	
	/** The Username inserted. */
	UsernameInserted("Username ok"),
	
	/** The Username empty. */
	UsernameEmpty("Username can't be empty"), 
	
	/** The Username ok. */
	UsernameOk("Username Ok"),
	
	/** The Username bad. */
	UsernameBad("Username is not ok, choose another");
	
	/** The text to display. */
	private String textToDisplay;
	
	/**
	 * Instantiates a new username input status.
	 *
	 * @param textToDisplay the text to display
	 */
	private UsernameInputStatus(String textToDisplay ){
		this.textToDisplay = textToDisplay;
	}
	
	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText(){
		return textToDisplay;
	}
}