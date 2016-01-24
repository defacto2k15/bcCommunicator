package bc.bcCommunicator.Model.Messages;

import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.internetMessageProxy.ConnectionId;

public interface IMessage {
	public String getMessageText() throws Exception; 
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception;
}
