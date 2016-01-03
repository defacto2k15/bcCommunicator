package bc.bcCommunicator.Model.Internet;

import java.net.URL;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

public interface IInternetMessagerCommandProvider {

	IInternetMessagerCommand getConnectToServerCommand(URL serverAddress);

	IInternetMessagerCommand getConnectionLostCommand(ConnectionId id);

	IInternetMessagerCommand getSendMessageCommand(ConnectionId id, IMessage message);

	IInternetMessagerCommand getConnectToUserCommand(URL oneAddress);

	IInternetMessagerCommand getListenOnPortCommand(int clientPort);

}
