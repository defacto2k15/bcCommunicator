package bc.bcCommunicator.Client.Model.Messages.Handling;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.Model.IConnectionsContainer;
import bc.bcCommunicator.Client.Model.IOtherUsersDataContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class IntroductoryTalkHandler.
 */
public class IntroductoryTalkHandler extends AbstractMessageHandler {
	
	/** The controller. */
	private ICommunicatorController controller;
	
	/** The users data container. */
	private IOtherUsersDataContainer usersDataContainer;
	
	/** The connections container. */
	private IConnectionsContainer connectionsContainer;

	/**
	 * Instantiates a new introductory talk handler.
	 *
	 * @param controller the controller
	 * @param usersDataContainer the users data container
	 * @param connectionsContainer the connections container
	 */
	public IntroductoryTalkHandler(ICommunicatorController controller, IOtherUsersDataContainer usersDataContainer,
			IConnectionsContainer connectionsContainer) {
				this.usersDataContainer = usersDataContainer;
				this.connectionsContainer = connectionsContainer;
				this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void handle( IIntroductoryTalk talk, ConnectionId id) throws Exception{
		Username username = talk.getUsername();
		connectionsContainer.setIdForUser(username, id);
		if( usersDataContainer.isUsernameInDatabase(username)){
			usersDataContainer.updateUrlOfUser(username, talk.getUrl());
			controller.userWasConnected(username);
		} else {
			usersDataContainer.addUserWithAddress(username, talk.getUrl());
			controller.newUserConnected(username);
		}
	}

}
