package bc.bcCommunicator.Client.Model.Messages;

import java.util.HashMap;
import java.util.Map;

import bc.bcCommunicator.Client.Model.IPendingLettersContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

public class PendingLettersContainer implements IPendingLettersContainer {
	Map<Username, Letter> pendingLettersMap= new HashMap<>();
	
	@Override
	public void addPendingLetter(Username username, Letter pendingLetter) {
		pendingLettersMap.put(username, pendingLetter);
	}

	@Override
	public boolean isPendingLetterAvalible(Username username) {
		return pendingLettersMap.containsKey(username);
	}

	@Override
	public Letter getPendingLetter(Username username) {
		if( isPendingLetterAvalible(username) == false ){
			throw new IllegalArgumentException("There is no pending letter for user "+username.getName());
		}
		return pendingLettersMap.get(username);
	}

}
