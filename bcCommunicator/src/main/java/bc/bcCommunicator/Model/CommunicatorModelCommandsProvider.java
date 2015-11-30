package bc.bcCommunicator.Model;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

public class CommunicatorModelCommandsProvider implements ICommunicatorModelCommandsProvider {

	@Override
	public ICommunicatorModelCommand getConnectServerCommand(URL urlToConnect) {
		return (ICommunicatorModel model)->{model.connectToServer(urlToConnect);};
	}

	@Override
	public ICommunicatorModelCommand getServerConnectionWasSuccesfullCommand(ConnectionId connectionId) throws Exception {
		return (ICommunicatorModel model)->{ try {
			model.serverConnectionWasSuccesfull(connectionId);
		} catch (Exception e) {
			e.printStackTrace();
		}};
	}

	@Override
	public ICommunicatorModelCommand getServerConnectionFailedCommand() {
		return (ICommunicatorModel model)->{ model.serverConnectionFailed(); };
	}

	@Override
	public ICommunicatorModelCommand getConnectionLostCommand(ConnectionId id) {
		return (ICommunicatorModel model)->{ model.connectionLost(id); };
	}

	@Override
	public ICommunicatorModelCommand getUsernameSubmittedCommand(Username username) {
		return (ICommunicatorModel model)->{ try {
			model.usernameSubmitted(username);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} };
	}

	@Override
	public ICommunicatorModelCommand getMessageRecievedCommand(IMessage recievedMessage, ConnectionId connectionId) {
		return( ICommunicatorModel model)->{ model.messageWasRecieved(recievedMessage, connectionId ); };
	}

}
