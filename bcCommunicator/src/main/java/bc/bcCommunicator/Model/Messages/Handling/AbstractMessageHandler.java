package bc.bcCommunicator.Model.Messages.Handling;

import bc.bcCommunicator.Model.Messages.Request.IAllUsersAddressesRequest;
import bc.bcCommunicator.Model.Messages.Request.IIntroductoryRequest;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.bcCommunicator.Model.Messages.Response.IUsernameBadResponse;
import bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse;
import bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk;
import bc.bcCommunicator.Model.Messages.Talk.ILetterTalk;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractMessageHandler.
 */
public abstract class AbstractMessageHandler {
	
	/**
	 * Handle.
	 *
	 * @param usernameOkResponse the username ok response
	 * @param id the id
	 * @throws Exception the exception
	 */
	public void handle(IUsernameOkResponse usernameOkResponse, ConnectionId id) throws Exception {
		throw new Exception(
				"A handling method for UsernameOkResponse Was caller in Abstract Message handler and not subclass");
	}

	/**
	 * Handle.
	 *
	 * @param usernameOkResponse the username ok response
	 * @param id the id
	 * @throws Exception the exception
	 */
	public void handle(IAllUsersAddressesResponse usernameOkResponse, ConnectionId id) throws Exception {
		throw new Exception(
				"A handling method for IAllUsersAddressesResponse Was caller in Abstract Message handler and not subclass");
	}

	/**
	 * Handle.
	 *
	 * @param talk the talk
	 * @param id the id
	 * @throws Exception the exception
	 */
	public void handle(IIntroductoryTalk talk, ConnectionId id) throws Exception {
		throw new Exception(
				"A handling method for IIntroductoryTalk Was caller in Abstract Message handler and not subclass");
	}

	/**
	 * Handle.
	 *
	 * @param talk the talk
	 * @param id the id
	 * @throws Exception the exception
	 */
	public void handle(ILetterTalk talk, ConnectionId id) throws Exception {
		throw new Exception("A handling method for LetterTalk Was caller in Abstract Message handler and not subclass");
	}
	
	/**
	 * Handle.
	 *
	 * @param introductoryRequest the introductory request
	 * @param id the id
	 * @throws Exception the exception
	 */
	public void handle(IIntroductoryRequest introductoryRequest, ConnectionId id) throws Exception{
		throw new Exception("A handling method for introductoryRequest Was caller in Abstract Message handler and not subclass");
	}
	
	/**
	 * Handle.
	 *
	 * @param usernameBadResponse the username bad response
	 * @param id the id
	 * @throws Exception the exception
	 */
	public void handle(IUsernameBadResponse usernameBadResponse, ConnectionId id) throws Exception{
		throw new Exception("A handling method for usernameBadResponse Was caller in Abstract Message handler and not subclass");
	}
	
	/**
	 * Handle.
	 *
	 * @param allUsersAddressesRequest the all users addresses request
	 * @param id the id
	 * @throws Exception the exception
	 */
	public void handle(IAllUsersAddressesRequest allUsersAddressesRequest, ConnectionId id) throws Exception{
		throw new Exception("A handling method for allUsersAddressesRequest Was caller in Abstract Message handler and not subclass");
	}
}
