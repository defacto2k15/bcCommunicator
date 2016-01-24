package bc.bcCommunicator.Client.Views;

// TODO: Auto-generated Javadoc
/**
 * The Enum TalkState.
 */
public enum TalkState {
	
	/** The No new messages. */
	NoNewMessages("No new"),
	
	/** The New message. */
	NewMessage("New!!");
	
	/** The text. */
	private String text;
	
	/**
	 * Instantiates a new talk state.
	 *
	 * @param text the text
	 */
	private TalkState(String text){
		this.text = text;
	}
	
	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText(){
		return text;
	}
}
