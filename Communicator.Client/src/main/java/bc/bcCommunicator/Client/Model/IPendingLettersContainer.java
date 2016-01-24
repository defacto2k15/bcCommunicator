package bc.bcCommunicator.Client.Model;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

public interface IPendingLettersContainer {
	void addPendingLetter( Username username, Letter pendingLetter);
	boolean isPendingLetterAvalible( Username username );
	Letter getPendingLetter( Username username);
}
