package bc.bcCommunicator.Model;

import java.net.URL;
import java.text.ParseException;
import java.util.function.Consumer;

import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

public interface ICommunicatorModel {
	void addCommand( ICommunicatorModelCommand command);
	void doConnectivityCommand( IConnectivityCommand command) throws Exception;
	void connectToServer( URL serverAddress );
	/*void serverConnectionWasSuccesfull(ConnectionId serverConnection) throws Exception; todo delete
	void serverConnectionFailed();
	void connectionLost(ConnectionId id);*/
	void usernameSubmitted(Username username) throws Exception;
	void messageWasRecieved(IMessage recievedMessage, ConnectionId connectionId);
//	void userConnectionFailed(URL failedUrl);
//	void userConnectionWasSuccesfull(URL sucessfullUrl, ConnectionId result) throws Exception;
	void getTalkStateData(Username username) throws ParseException;
	void letterWasWritten(String letterText, Username recipient) throws Exception;
}
