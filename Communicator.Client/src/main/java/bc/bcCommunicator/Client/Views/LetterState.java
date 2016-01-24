package bc.bcCommunicator.Client.Views;

// TODO: Auto-generated Javadoc
/**
 * The Enum LetterState.
 */
public enum LetterState {
	
	/** The No_ letter. */
	No_Letter(""),
	
	/** The Letter_ sent. */
	Letter_Sent("Letter sent"),
	
	/** The Letter_ sending. */
	Letter_Sending("Sending letter..."),
	
	/** The Letter_ failed. */
	Letter_Failed("Failed sending letter");
	
	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new letter state.
	 *
	 * @param message the message
	 */
	private LetterState( String message){
		this.message = message;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage(){
		return message;
	}
}
