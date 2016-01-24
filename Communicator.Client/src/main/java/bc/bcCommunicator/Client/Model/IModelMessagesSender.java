package bc.bcCommunicator.Client.Model;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.internetMessageProxy.ConnectionId;

public interface IModelMessagesSender {

	void sendIntroductoryRequest() throws Exception;

	void sendAllUsersAddressesRequest() throws Exception;

	void sendIntroductoryTalkToUser(ConnectionId connection, Username actorUsername, URL ourUrl) throws Exception;

	void sendLetterTalk(Letter createdLetter, ConnectionId recipientConnectionId) throws Exception;

}
