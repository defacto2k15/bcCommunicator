package Controller;

import java.util.List;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Views.UserConnectionState;

public class TalkStateData {
	public Username username;
	public List<Letter> letters;
	public UserConnectionState state;
	
	public TalkStateData(Username username, List<Letter> letters, UserConnectionState state) {
		this.username = username;
		this.letters = letters;
		this.state = state;
	}
	
	
}
