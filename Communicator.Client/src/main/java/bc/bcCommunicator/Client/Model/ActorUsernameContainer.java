
package bc.bcCommunicator.Client.Model;

import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Class ActorUsernameContainer.
 */
public class ActorUsernameContainer implements IActorUsernameContainer {
	
	/** The username. */
	Username username;
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IActorUsernameContainer#setUsername(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void setUsername(Username username) {
		this.username = username;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IActorUsernameContainer#getUsername()
	 */
	@Override
	public Username getUsername() {
		if(username == null){
			throw new IllegalStateException("Username was not set");
		}
		return username;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Model.IActorUsernameContainer#isUsernameSet()
	 */
	@Override
	public boolean isUsernameSet() {
		return !(username==null);
	}

}
