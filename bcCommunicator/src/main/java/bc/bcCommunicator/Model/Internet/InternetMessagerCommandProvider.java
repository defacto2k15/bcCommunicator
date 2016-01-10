package bc.bcCommunicator.Model.Internet;

import java.net.URL;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

public class InternetMessagerCommandProvider implements IInternetMessagerCommandProvider {

	@Override
	public IInternetMessagerCommand getConnectToServerCommand(URL serverAddress) {
		return (IInternetMessager messager) -> {
			try {
				messager.connectToServer(serverAddress);
			} catch (Exception e) {
				System.err.println("E360");
				e.printStackTrace();
			}
		};
	}

	@Override
	public IInternetMessagerCommand getConnectionLostCommand(ConnectionId id) {
		return (IInternetMessager messager) -> {
			messager.connectionLost(id);
		};
	}

	@Override
	public IInternetMessagerCommand getSendMessageCommand(ConnectionId id, IMessage message) {
		return (IInternetMessager messager) -> {
			try {
				messager.sendMessage(id, message.getMessageText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

	@Override
	public IInternetMessagerCommand getConnectToUserCommand(URL oneAddress) {
		return (IInternetMessager messager) -> {
			messager.connectToUser( oneAddress );
		};
	}

	@Override
	public IInternetMessagerCommand getListenOnPortCommand(int clientPort) {
		return (IInternetMessager messager) -> { messager.listenOnPort(clientPort); };
	}

}
