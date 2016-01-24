package bc.bcCommunicator.Model.Messages.Handling;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

public interface IRecievedMessagesHandler {
	void handle(IMessage message, ConnectionId id) ;

}
