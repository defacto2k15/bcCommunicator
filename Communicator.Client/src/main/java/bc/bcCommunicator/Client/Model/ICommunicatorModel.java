package bc.bcCommunicator.Client.Model;

import java.net.URL;
import java.text.ParseException;

import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Interface ICommunicatorModel.
 */
public interface ICommunicatorModel {
	
	/**
	 * Connect to server.
	 *
	 * @param serverAddress the server address
	 */
	void connectToServer( URL serverAddress );
	
	/**
	 * Username submitted.
	 *
	 * @param username the username
	 * @throws Exception the exception
	 */
	void usernameSubmitted(Username username) throws Exception;
//	void userConnectionFailed(URL failedUrl);
/**
 * Gets the talk state data.
 *
 * @param username the username
 * @return the talk state data
 * @throws ParseException the parse exception
 */
//	void userConnectionWasSuccesfull(URL sucessfullUrl, ConnectionId result) throws Exception;
	void getTalkStateData(Username username) throws ParseException;
	
	/**
	 * Letter was written.
	 *
	 * @param letterText the letter text
	 * @param recipient the recipient
	 * @throws Exception the exception
	 */
	void letterWasWritten(String letterText, Username recipient) throws Exception;
}
