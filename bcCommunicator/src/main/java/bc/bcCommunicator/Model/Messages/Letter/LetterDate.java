package bc.bcCommunicator.Model.Messages.Letter;

import java.text.ParseException;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterDate.
 */
public class LetterDate {
	
	/** The date. */
	Date date;
	
	/**
	 * Instantiates a new letter date.
	 *
	 * @param date the date
	 */
	public LetterDate( Date date){
		this.date = date;
	}
	
	/**
	 * Instantiates a new letter date.
	 *
	 * @param text the text
	 * @throws ParseException the parse exception
	 */
	public LetterDate(String text) throws ParseException{
		this.date = LetterDateParser.parseFromString(text);
	}
	
	/**
	 * Gets the date as string.
	 *
	 * @return the date as string
	 * @throws ParseException the parse exception
	 */
	public String getDateAsString() throws ParseException{
		return LetterDateParser.parseFromDate(date);
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate(){
		return date;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override public boolean equals(Object other) {
	    boolean result = false;
	    if (other instanceof LetterDate) {
	        LetterDate that = (LetterDate) other;
	        result = (this.getDate()== that.getDate());
	    }
	    return result;
	}
}
