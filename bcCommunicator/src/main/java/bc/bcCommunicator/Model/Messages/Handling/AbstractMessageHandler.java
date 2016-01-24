package bc.bcCommunicator.Model.Messages.Handling;

import bc.bcCommunicator.Model.Messages.Request.IAllUsersAddressesRequest;
import bc.bcCommunicator.Model.Messages.Request.IIntroductoryRequest;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.bcCommunicator.Model.Messages.Response.IUsernameBadResponse;
import bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameOkResponse;
import bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk;
import bc.bcCommunicator.Model.Messages.Talk.ILetterTalk;
import bc.bcCommunicator.Model.Messages.Talk.LetterTalk;
import bc.internetMessageProxy.ConnectionId;

public abstract class AbstractMessageHandler {
	public void handle(IUsernameOkResponse usernameOkResponse, ConnectionId id) throws Exception {
		throw new Exception(
				"A handling method for UsernameOkResponse Was caller in Abstract Message handler and not subclass");
	}

	public void handle(IAllUsersAddressesResponse usernameOkResponse, ConnectionId id) throws Exception {
		throw new Exception(
				"A handling method for IAllUsersAddressesResponse Was caller in Abstract Message handler and not subclass");
	}

	public void handle(IIntroductoryTalk talk, ConnectionId id) throws Exception {
		throw new Exception(
				"A handling method for IIntroductoryTalk Was caller in Abstract Message handler and not subclass");
	}

	public void handle(ILetterTalk talk, ConnectionId id) throws Exception {
		throw new Exception("A handling method for LetterTalk Was caller in Abstract Message handler and not subclass");
	}
	
	public void handle(IIntroductoryRequest introductoryRequest, ConnectionId id) throws Exception{
		throw new Exception("A handling method for introductoryRequest Was caller in Abstract Message handler and not subclass");
	}
	
	public void handle(IUsernameBadResponse usernameBadResponse, ConnectionId id) throws Exception{
		throw new Exception("A handling method for usernameBadResponse Was caller in Abstract Message handler and not subclass");
	}
	
	public void handle(IAllUsersAddressesRequest allUsersAddressesRequest, ConnectionId id) throws Exception{
		throw new Exception("A handling method for allUsersAddressesRequest Was caller in Abstract Message handler and not subclass");
	}
}
