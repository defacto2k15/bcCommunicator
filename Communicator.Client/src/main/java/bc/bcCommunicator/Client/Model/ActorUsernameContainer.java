
package bc.bcCommunicator.Client.Model;

import bc.bcCommunicator.Model.BasicTypes.Username;

public class ActorUsernameContainer implements IActorUsernameContainer {
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

}
