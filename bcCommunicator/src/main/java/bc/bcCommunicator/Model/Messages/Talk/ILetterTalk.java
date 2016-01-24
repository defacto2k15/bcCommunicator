package bc.bcCommunicator.Model.Messages.Talk;

import java.text.ParseException;

import bc.bcCommunicator.Model.Messages.Letter.Letter;

public interface ILetterTalk {
	Letter getLetter() throws ParseException;
}
