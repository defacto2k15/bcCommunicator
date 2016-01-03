package bc.bcCommunicator.Model;

import java.net.URL;
import java.util.logging.Handler;

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
			model.doConnectivityCommand( (IConnectivityHandler handler)->{ handler.serverConnectionWasSuccesfull(connectionId);});
		} catch (Exception e) {
			System.err.println("E301");
			e.printStackTrace();
		}};
	}

	@Override
	public ICommunicatorModelCommand getServerConnectionFailedCommand() {
		return (ICommunicatorModel model)->{
			try {
				model.doConnectivityCommand((IConnectivityHandler handler)->{ handler.serverConnectionFailed();});
			} catch (Exception e) {
				System.err.println("E302");
				e.printStackTrace();
			}
		};
	}

	@Override
	public ICommunicatorModelCommand getConnectionLostCommand(ConnectionId id) {
		return (ICommunicatorModel model)->{ 
			try {
				model.doConnectivityCommand( (IConnectivityHandler handler) -> {handler.connectionLost(id);});
			} catch (Exception e) {
				System.err.println("E303");
				e.printStackTrace();
			}
		};
	}

	@Override
	public ICommunicatorModelCommand getUsernameSubmittedCommand(Username username) {
		return (ICommunicatorModel model)->{ try {
			model.usernameSubmitted(username);
		} catch (Exception e) {
			System.err.println("E304");
			e.printStackTrace();
		} };
	}

	@Override
	public ICommunicatorModelCommand getMessageRecievedCommand(IMessage recievedMessage, ConnectionId connectionId) {
		return( ICommunicatorModel model)->{ model.messageWasRecieved(recievedMessage, connectionId ); };
	}

	@Override
	public ICommunicatorModelCommand getUserConnectionFailed(URL failedUrl) {
		return ( ICommunicatorModel model)-> {
			try {
				model.doConnectivityCommand( (IConnectivityHandler handler)->{ handler.userConnectionFailed(failedUrl);});
			} catch (Exception e) {
				System.err.println("E305");
				e.printStackTrace();
			}
		};
	}

	@Override
	public ICommunicatorModelCommand getUserConectionWasSuccesfullCommand( URL sucessfullUrl, ConnectionId result) {
		return (  ICommunicatorModel model )->{ try {
			model.doConnectivityCommand((IConnectivityHandler handler)->{ 
					handler.userConnectionWasSuccesfull(sucessfullUrl, result);
				});
		} catch (Exception e) {
			System.err.println("E306");
			e.printStackTrace();
		} };
	}

}
