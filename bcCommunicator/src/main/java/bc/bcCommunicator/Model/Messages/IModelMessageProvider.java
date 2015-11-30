package bc.bcCommunicator.Model.Messages;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;

public interface IModelMessageProvider {
	IRequest getIntroductoryRequest(Username username, URL clientUrl) throws Exception;

	IRequest getAllUsersAddressesRequest(AllUsersAddresses usersAddresses) throws Exception;
}
