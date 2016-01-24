package bc.bcCommunicator.Client.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterContainer.
 */
public class LetterContainer implements ILetterContainer {
	
	/** The talks map. */
	Map<Username, List<Letter>> talksMap = new HashMap<>();
	
	/** The nn. */
	Username nn;
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.ILetterContainer#addLetterOfTalkToUser(bc.bcCommunicator.Model.BasicTypes.Username, bc.bcCommunicator.Model.Messages.Letter.Letter)
	 */
	@Override
	public void addLetterOfTalkToUser(Username user, Letter letter) {
		if( talksMap.containsKey(user) == false){
			talksMap.put(user,  new ArrayList<Letter>());
		}
		talksMap.get(user).add(letter);
		nn = user;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.ILetterContainer#getLettersOfTalkToUser(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public List<Letter> getLettersOfTalkToUser(Username username) {
		if( talksMap.containsKey(username) == false){
			talksMap.put(username,  new ArrayList<Letter>());
		}
		return talksMap.get(username);
	}

}
