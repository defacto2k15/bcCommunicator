package bc.bcCommunicator.Model.Messages.Letter;

import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Class Letter.
 */
public class Letter {
	
	/** The text. */
	public LetterText text;
	
	/** The date. */
	public LetterDate date;
	
	/** The sender. */
	public Username sender;
	
	/** The recipient. */
	public Username recipient;
	
	/** The type. */
	public LetterSendingType type;
	
	/**
	 * Instantiates a new letter.
	 *
	 * @param text the text
	 * @param date the date
	 * @param sender the sender
	 * @param recipient the recipient
	 * @param type the type
	 */
	public Letter(LetterText text, LetterDate date, Username sender, Username recipient, LetterSendingType type) {
		this.text = text;
		this.date = date;
		this.sender = sender;
		this.recipient = recipient;
		this.type = type;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override public boolean equals(Object other) {
	    boolean result = false;
	    if (other instanceof Letter) {
	    	Letter that = (Letter) other;
	        result = (this.text == that.text && this.sender == that.sender && this.recipient == that.recipient && this.type == that.type);
	    }
	    return result;
	}
	
	/**
	 * Gets the other user in talk.
	 *
	 * @return the other user in talk
	 */
	public Username getOtherUserInTalk(){
		if( type == LetterSendingType.Sent ){
			return recipient;
		} else {
			return sender;
		}
	}
	
	
	
}
