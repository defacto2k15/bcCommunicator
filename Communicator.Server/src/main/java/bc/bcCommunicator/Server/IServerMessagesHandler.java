package bc.bcCommunicator.Server;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Request.IAllUsersAddressesRequest;
import bc.bcCommunicator.Model.Messages.Request.IIntroductoryRequest;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Interface IServerMessagesHandler.
 */
public interface IServerMessagesHandler extends IRecievedMessagesHandler{
	
	/**
	 * Handle.
	 *
	 * @param introductoryRequest the introductory request
	 * @param id the id
	 * @throws Exception the exception
	 */
	public void handle(IIntroductoryRequest introductoryRequest, ConnectionId id) throws Exception;
	
	/**
	 * Handle.
	 *
	 * @param request the request
	 * @param id the id
	 */
	public void handle( IAllUsersAddressesRequest request, ConnectionId id );
}
