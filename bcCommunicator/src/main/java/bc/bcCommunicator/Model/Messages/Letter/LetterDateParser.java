package bc.bcCommunicator.Model.Messages.Letter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterDateParser.
 */
public class LetterDateParser {
	
	/** The date format. */
	private static String dateFormat = "dd-MM-yyyy HH:mm:ss";
	
	/**
	 * Parses the from string.
	 *
	 * @param text the text
	 * @return the date
	 * @throws ParseException the parse exception
	 */
	public static Date parseFromString(String text) throws ParseException{
	    DateFormat df = new SimpleDateFormat(dateFormat);
	    return df.parse(text);
	}
	
	/**
	 * Parses the from date.
	 *
	 * @param date the date
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	public static String parseFromDate(Date date ) throws ParseException{
		DateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(date);
	}
}
