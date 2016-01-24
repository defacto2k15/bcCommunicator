package bc.bcCommunicator.Client.Model.Messages;

import java.util.HashMap;
import java.util.Map;

import bc.bcCommunicator.Client.Model.IPendingLettersContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

// TODO: Auto-generated Javadoc
/**
 * The Class PendingLettersContainer.
 */
public class PendingLettersContainer implements IPendingLettersContainer {
	
	/** The pending letters map. */
	Map<Username, Letter> pendingLettersMap= new HashMap<>();
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IPendingLettersContainer#addPendingLetter(bc.bcCommunicator.Model.BasicTypes.Username, bc.bcCommunicator.Model.Messages.Letter.Letter)
	 */
	@Override
	public void addPendingLetter(Username username, Letter pendingLetter) {
		pendingLettersMap.put(username, pendingLetter);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IPendingLettersContainer#isPendingLetterAvalible(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public boolean isPendingLetterAvalible(Username username) {
		return pendingLettersMap.containsKey(username);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IPendingLettersContainer#getPendingLetter(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public Letter getPendingLetter(Username username) {
		if( isPendingLetterAvalible(username) == false ){
			throw new IllegalArgumentException("There is no pending letter for user "+username.getName());
		}
		return pendingLettersMap.get(username);
	}

}
