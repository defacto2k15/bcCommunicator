package bc.bcCommunicator.Model.Internet;

import java.net.URL;
import java.util.Optional;

import bc.bcCommunicator.Model.ICommunicatorModel;
import bc.internetMessageProxy.ConnectionId;

public interface IInternetMessager {

	void addCommand(IInternetMessagerCommand command);

	void connectToServer(URL serverAddress) throws Exception;

	void connectionLost(ConnectionId id);

	void setModel(ICommunicatorModel model);

	void sendMessage(ConnectionId id, String messageText);

	void connectToUser(URL oneAddress);

	void listenOnPort(int clientPort);
	
}