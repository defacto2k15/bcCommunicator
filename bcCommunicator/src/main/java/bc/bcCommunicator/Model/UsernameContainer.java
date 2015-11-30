package bc.bcCommunicator.Model;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;

public class UsernameContainer implements IUsernameContainer {
	Username username;
	
	@Override
	public void setUsername(Username username) {
		this.username = username;
	}

	@Override
	public Username getUsername() {
		if(username == null){
			throw new IllegalStateException("Username was not set");
		}
		return username;
	}

	@Override
	public boolean isUsernameSet() {
		return !(username==null);
	}

	@Override
	public AllUsersAddresses getUsernamesWithAddresses() {
		// TODO  REALLY IMPLEMENT IT Auto-generated method stub
		return null;
	}

}
