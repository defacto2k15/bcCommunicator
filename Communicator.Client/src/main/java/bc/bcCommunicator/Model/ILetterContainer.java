package bc.bcCommunicator.Model;

import java.util.List;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

public interface ILetterContainer {
	public void addLetterOfTalkToUser( Username user, Letter letter );
	public List<Letter> getLettersOfTalkToUser(Username username);
}
