package bc.bcCommunicator.Model;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;

public interface IUsernameContainer {
	public void setUsername( Username username);
	public Username getUsername();
	public boolean isUsernameSet();
	public AllUsersAddresses getUsernamesWithAddresses();
}
