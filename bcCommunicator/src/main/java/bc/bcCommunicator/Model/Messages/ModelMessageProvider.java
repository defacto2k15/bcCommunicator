package bc.bcCommunicator.Model.Messages;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;

public class ModelMessageProvider implements IModelMessageProvider{

	@Override
	public IRequest getIntroductoryRequest(Username username, URL clientUrl) throws Exception {
		return new IntroductoryRequest(username, clientUrl);
	}

	@Override
	public IRequest getAllUsersAddressesRequest(AllUsersAddresses usersAddresses) throws Exception {
	
		return new AllUsersAddressesRequest(usersAddresses);
	}

}
