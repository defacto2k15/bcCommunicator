package bc.bcCommunicator.Model.Internet;

import java.net.URL;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

public class InternetMessagerCommandProvider implements IInternetMessagerCommandProvider{

	@Override
	public IInternetMessagerCommand getConnectToServerCommand(URL serverAddress) {
		return (IInternetMessager messager)->{ try {
			messager.connectToServer(serverAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} };
	}

	@Override
	public IInternetMessagerCommand getConnectionLostCommand(ConnectionId id) {
		return (IInternetMessager messager)->{ messager.connectionLost(id); };
	}

	@Override
	public IInternetMessagerCommand getSendMessageCommand(ConnectionId id, IMessage message) {
		return (IInternetMessager messager)->{ 
				try {
					messager.sendMessage(id, message.getMessageText());
				} catch (Exception e) {
					e.printStackTrace();
				} 
			};
	}

}
