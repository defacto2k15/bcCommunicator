package bc.bcCommunicator.Model;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.internetMessageProxy.ConnectionId;

public interface IModelMessagesSender {

	void sendIntroductoryRequest() throws Exception;

	void sendAllUsersAddressesRequest() throws Exception;

	void sendIntroductoryTalkToUser(ConnectionId connection, Username actorUsername, URL ourUrl) throws Exception;

}
