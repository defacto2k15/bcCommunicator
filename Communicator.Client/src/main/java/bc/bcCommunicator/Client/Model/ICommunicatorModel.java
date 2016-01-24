package bc.bcCommunicator.Client.Model;

import java.net.URL;
import java.text.ParseException;

import bc.bcCommunicator.Model.BasicTypes.Username;

public interface ICommunicatorModel {
	void connectToServer( URL serverAddress );
	void usernameSubmitted(Username username) throws Exception;
//	void userConnectionFailed(URL failedUrl);
//	void userConnectionWasSuccesfull(URL sucessfullUrl, ConnectionId result) throws Exception;
	void getTalkStateData(Username username) throws ParseException;
	void letterWasWritten(String letterText, Username recipient) throws Exception;
}
