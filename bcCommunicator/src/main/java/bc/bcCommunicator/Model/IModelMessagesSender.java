package bc.bcCommunicator.Model;

public interface IModelMessagesSender {

	void sendIntroductoryRequest() throws Exception;

	void sendAllUsersAddressesRequest() throws Exception;

}
