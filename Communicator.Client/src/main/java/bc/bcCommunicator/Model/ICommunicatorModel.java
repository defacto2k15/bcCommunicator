package bc.bcCommunicator.Model;

import java.net.URL;
import java.text.ParseException;
import java.util.function.Consumer;

import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

public interface ICommunicatorModel {
	void connectToServer( URL serverAddress );
	void usernameSubmitted(Username username) throws Exception;
//	void userConnectionFailed(URL failedUrl);
//	void userConnectionWasSuccesfull(URL sucessfullUrl, ConnectionId result) throws Exception;
	void getTalkStateData(Username username) throws ParseException;
	void letterWasWritten(String letterText, Username recipient) throws Exception;
}
