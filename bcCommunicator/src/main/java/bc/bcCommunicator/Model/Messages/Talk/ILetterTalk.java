package bc.bcCommunicator.Model.Messages.Talk;

import java.text.ParseException;
import java.util.Date;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;

public interface ILetterTalk {
	Letter getLetter() throws ParseException;
}
