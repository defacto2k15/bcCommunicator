package bc.bcCommunicator.Model.Messages.Handling;

import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.IConnectionsContainer;
import bc.bcCommunicator.Model.IOtherUsersDataContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse;
import bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk;
import bc.internetMessageProxy.ConnectionId;

public class IntroductoryTalkHandler extends AbstractMessageHandler {
	private ICommunicatorController controller;
	private IOtherUsersDataContainer usersDataContainer;
	private IConnectionsContainer connectionsContainer;

	public IntroductoryTalkHandler(ICommunicatorController controller, IOtherUsersDataContainer usersDataContainer,
			IConnectionsContainer connectionsContainer) {
				this.usersDataContainer = usersDataContainer;
				this.connectionsContainer = connectionsContainer;
				this.controller = controller;
	}
	
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
