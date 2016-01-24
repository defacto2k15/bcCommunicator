package bc.bcCommunicator.Client.Model;

import bc.bcCommunicator.Model.BasicTypes.Username;

public interface IActorUsernameContainer {
	public void setUsername( Username username);
	public Username getUsername();
	public boolean isUsernameSet();
}
