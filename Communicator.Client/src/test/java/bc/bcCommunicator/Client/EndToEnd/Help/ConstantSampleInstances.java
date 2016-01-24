package bc.bcCommunicator.Client.EndToEnd.Help;

import java.util.Date;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterSendingType;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;

// TODO: Auto-generated Javadoc
/**
 * The Class ConstantSampleInstances.
 */
public class ConstantSampleInstances {
	
	/**
	 * Gets the sample letter.
	 *
	 * @return the sample letter
	 */
	public static Letter getSampleLetter(){
		return new Letter( new LetterText("SomeText"), new LetterDate(new Date()),
				new Username("Some sender username"), new Username("Some recipient username"), LetterSendingType.Sent);
	}
}
