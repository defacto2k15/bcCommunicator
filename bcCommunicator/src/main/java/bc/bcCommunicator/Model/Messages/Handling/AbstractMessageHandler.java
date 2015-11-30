package bc.bcCommunicator.Model.Messages.Handling;

import bc.bcCommunicator.Model.Messages.IUsernameOkResponse;
import bc.bcCommunicator.Model.Messages.UsernameOkResponse;
import bc.internetMessageProxy.ConnectionId;

public abstract class AbstractMessageHandler {
	public void handle( IUsernameOkResponse usernameOkResponse, ConnectionId id) throws Exception{
		throw new Exception("A handling method for UsernameOkResponse Was caller in Abstract Message handler and not subclass");
	}
}
