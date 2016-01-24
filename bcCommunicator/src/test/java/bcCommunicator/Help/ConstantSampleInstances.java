package bcCommunicator.Help;

import java.util.Date;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterSendingType;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;

public class ConstantSampleInstances {
	public static Letter getSampleLetter(){
		return new Letter( new LetterText("SomeText"), new LetterDate(new Date()),
				new Username("Some sender username"), new Username("Some recipient username"), LetterSendingType.Sent);
	}
}
