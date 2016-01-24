package bc.bcCommunicator.Client.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

public class LetterContainer implements ILetterContainer {
	Map<Username, List<Letter>> talksMap = new HashMap<>();
	Username nn;
	
	@Override
	public void addLetterOfTalkToUser(Username user, Letter letter) {
		if( talksMap.containsKey(user) == false){
			talksMap.put(user,  new ArrayList<Letter>());
		}
		talksMap.get(user).add(letter);
		nn = user;
	}

	@Override
	public List<Letter> getLettersOfTalkToUser(Username username) {
		if( talksMap.containsKey(username) == false){
			talksMap.put(username,  new ArrayList<Letter>());
		}
		return talksMap.get(username);
	}

}
