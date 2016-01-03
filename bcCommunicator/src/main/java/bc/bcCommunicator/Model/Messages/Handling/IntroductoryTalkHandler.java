package bc.bcCommunicator.Model.Messages.Handling;

import Controller.ICommunicatorController;
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
		controller.newUserConnected(username);
		connectionsContainer.setIdForUser(username, id);
		usersDataContainer.addUserWithAddress(username, talk.getUrl());
	}

}
