package bc.bcCommunicator.Model.Messages.Letter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LetterDateParser {
	private static String dateFormat = "dd-MM-yyyy HH:mm:ss";
	
	public static Date parseFromString(String text) throws ParseException{
	    DateFormat df = new SimpleDateFormat(dateFormat);
	    return df.parse(text);
	}
	
	public static String parseFromDate(Date date ) throws ParseException{
		DateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(date);
	}
}
