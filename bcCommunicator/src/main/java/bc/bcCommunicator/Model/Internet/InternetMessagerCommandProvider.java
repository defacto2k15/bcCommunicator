package bc.bcCommunicator.Model.Internet;

import java.net.URL;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class InternetMessagerCommandProvider.
 */
public class InternetMessagerCommandProvider implements IInternetMessagerCommandProvider {

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider#getConnectToServerCommand(java.net.URL)
	 */
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

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider#getConnectionLostCommand(bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public IInternetMessagerCommand getConnectionLostCommand(ConnectionId id) {
		return (IInternetMessager messager) -> {
			messager.connectionLost(id);
		};
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider#getSendMessageCommand(bc.internetMessageProxy.ConnectionId, bc.bcCommunicator.Model.Messages.IMessage)
	 */
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

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider#getConnectToUserCommand(java.net.URL)
	 */
	@Override
	public IInternetMessagerCommand getConnectToUserCommand(URL oneAddress) {
		return (IInternetMessager messager) -> {
			messager.connectToUser( oneAddress );
		};
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider#getListenOnPortCommand(int)
	 */
	@Override
	public IInternetMessagerCommand getListenOnPortCommand(int clientPort) {
		return (IInternetMessager messager) -> { messager.listenOnPort(clientPort); };
	}

}
