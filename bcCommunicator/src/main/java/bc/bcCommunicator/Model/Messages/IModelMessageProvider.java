package bc.bcCommunicator.Model.Messages;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Request.IRequest;

public interface IModelMessageProvider {
	IRequest getIntroductoryRequest(Username username, URL clientUrl) throws Exception;

	IRequest getAllUsersAddressesRequest() throws Exception;
}
