import java.net.URL;
import java.util.Map;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Request.IAllUsersAddressesRequest;
import bc.bcCommunicator.Model.Messages.Request.IIntroductoryRequest;
import bc.bcCommunicator.Model.Messages.Response.AllUsersAddressesResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameBadResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameOkResponse;
import bc.internetMessageProxy.ConnectionId;

public interface IServerMessagesHandler extends IRecievedMessagesHandler{
	
	public void handle(IIntroductoryRequest introductoryRequest, ConnectionId id) throws Exception;
	
	public void handle( IAllUsersAddressesRequest request, ConnectionId id );
}
