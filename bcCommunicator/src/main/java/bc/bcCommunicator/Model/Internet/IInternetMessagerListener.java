package bc.bcCommunicator.Model.Internet;

import java.net.URL;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

public interface IInternetMessagerListener {

	void messageWasRecieved(IMessage recievedMessage, ConnectionId connectionId);

	void serverConnectionFailed();

	void serverConnectionWasSuccesfull(ConnectionId connectionId) throws Exception;

	void connectionLost(ConnectionId id);

	void messageSentSuccesfully(ConnectionId id) throws Exception;

	void messageSendingFailed(ConnectionId id);

	void userConnectionFailed(URL userAddress);

	void userConnectionWasSuccesfull(URL userAddress, ConnectionId connectionId) throws Exception;

}
