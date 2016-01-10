package bc.bcCommunicator.Model.Messages.Letter;

import bc.bcCommunicator.Model.BasicTypes.Username;

public class Letter {
	public LetterText text;
	public LetterDate date;
	public Username sender;
	public LetterSendingType type;
	
	public Letter(LetterText text, LetterDate date, Username sender, LetterSendingType type) {
		this.text = text;
		this.date = date;
		this.sender = sender;
		this.type = type;
	}
	
	@Override public boolean equals(Object other) {
	    boolean result = false;
	    if (other instanceof Letter) {
	    	Letter that = (Letter) other;
	        result = (this.text == that.text && this.sender == that.sender && this.type == that.type);
	    }
	    return result;
	}
	
	
}
