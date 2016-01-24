package bc.bcCommunicator.Model.Internet;

import java.net.URL;
import java.util.Optional;

import bc.internetMessageProxy.ConnectionId;

public interface IInternetMessager {

	void connectToServer(URL serverAddress) throws Exception;

	void connectionLost(ConnectionId id);

	void sendMessage(ConnectionId id, String messageText);

	void connectToUser(URL oneAddress);

	void listenOnPort(int clientPort);
	
}