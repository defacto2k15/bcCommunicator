package bc.bcCommunicator.Model.Messages.Letter;

import java.text.ParseException;
import java.util.Date;

public class LetterDate {
	Date date;
	
	public LetterDate( Date date){
		this.date = date;
	}
	
	public LetterDate(String text) throws ParseException{
		this.date = LetterDateParser.parseFromString(text);
	}
	
	public String getDateAsString() throws ParseException{
		return LetterDateParser.parseFromDate(date);
	}
	
	public Date getDate(){
		return date;
	}
	
	@Override public boolean equals(Object other) {
	    boolean result = false;
	    if (other instanceof LetterDate) {
	        LetterDate that = (LetterDate) other;
	        result = (this.getDate()== that.getDate());
	    }
	    return result;
	}
}
