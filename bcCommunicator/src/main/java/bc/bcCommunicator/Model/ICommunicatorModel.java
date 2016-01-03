package bc.bcCommunicator.Model;

import java.net.URL;
import java.util.function.Consumer;

import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

public interface ICommunicatorModel {
	void addCommand( ICommunicatorModelCommand command);
	void doConnectivityCommand( IConnectivityCommand command) throws Exception;
	void connectToServer( URL serverAddress );
	void setController(ICommunicatorController controller);
	/*void serverConnectionWasSuccesfull(ConnectionId serverConnection) throws Exception; todo delete
	void serverConnectionFailed();
	void connectionLost(ConnectionId id);*/
	void usernameSubmitted(Username username) throws Exception;
	void messageWasRecieved(IMessage recievedMessage, ConnectionId connectionId);
//	void userConnectionFailed(URL failedUrl);
//	void userConnectionWasSuccesfull(URL sucessfullUrl, ConnectionId result) throws Exception;
}
