package bc.bcCommunicator.Server;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Request.IAllUsersAddressesRequest;
import bc.bcCommunicator.Model.Messages.Request.IIntroductoryRequest;
import bc.internetMessageProxy.ConnectionId;

public interface IServerMessagesHandler extends IRecievedMessagesHandler{
	
	public void handle(IIntroductoryRequest introductoryRequest, ConnectionId id) throws Exception;
	
	public void handle( IAllUsersAddressesRequest request, ConnectionId id );
}
