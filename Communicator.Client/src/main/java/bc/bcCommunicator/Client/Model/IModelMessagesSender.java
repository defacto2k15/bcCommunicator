package bc.bcCommunicator.Client.Model;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Interface IModelMessagesSender.
 */
public interface IModelMessagesSender {

	/**
	 * Send introductory request.
	 *
	 * @throws Exception the exception
	 */
	void sendIntroductoryRequest() throws Exception;

	/**
	 * Send all users addresses request.
	 *
	 * @throws Exception the exception
	 */
	void sendAllUsersAddressesRequest() throws Exception;

	/**
	 * Send introductory talk to user.
	 *
	 * @param connection the connection
	 * @param actorUsername the actor username
	 * @param ourUrl the our url
	 * @throws Exception the exception
	 */
	void sendIntroductoryTalkToUser(ConnectionId connection, Username actorUsername, URL ourUrl) throws Exception;

	/**
	 * Send letter talk.
	 *
	 * @param createdLetter the created letter
	 * @param recipientConnectionId the recipient connection id
	 * @throws Exception the exception
	 */
	void sendLetterTalk(Letter createdLetter, ConnectionId recipientConnectionId) throws Exception;

}
