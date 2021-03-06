package bc.commonTestUtilities;

import java.net.URL;
import java.util.Map;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.Request.AllUsersAddressesRequest;
import bc.bcCommunicator.Model.Messages.Request.IntroductoryRequest;
import bc.bcCommunicator.Model.Messages.Response.AllUsersAddressesResponse;
import bc.bcCommunicator.Model.Messages.Response.IResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameBadResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameOkResponse;

public class FakeServerRunner extends FakeInternetEntity {

	public FakeServerRunner(int port) {
		super(port);
	}
	
	public void assertHasRecievedIntrodutionRequestWith(Username username, URL clientUrl) throws Exception {
		assertRecievedMessageWithExactText( new IntroductoryRequest(username, clientUrl).getMessageText() );
	}
	
	public void sendUsernameOkResponseWith(Username username, URL clientUrl) throws Exception {
		IResponse response = new UsernameOkResponse( username );
		assert( lastConnectionId != null);
		sendMessageToLastConnectionSocket(response);

	}
	
	public void assertHasRecievedRequestForUsersAdresses() throws Exception {
		assertRecievedMessageWithExactText( new AllUsersAddressesRequest().getMessageText() );
	}

	public void sendAllUsersAddressesResponse(Map<Username, URL>  allUsersAddressesMap) throws Exception {
		IResponse response = new AllUsersAddressesResponse( new AllUsersAddresses(allUsersAddressesMap) );
		sendMessageToLastConnectionSocket(response);		
	}

	public void sendUsernameBadResponse(Username username) throws Exception {
		sendMessageToLastConnectionSocket(new UsernameBadResponse(username));
	}

}
