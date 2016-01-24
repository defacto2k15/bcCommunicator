package bc.bcCommunicator.Model.Messages.Request;

import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.internetMessageProxy.ConnectionId;

public class AllUsersAddressesRequest extends AbstractRequest implements IAllUsersAddressesRequest{
	public AllUsersAddressesRequest( /*AllUsersAddresses allUsersAddresses*/ ) throws Exception{
		addField(RequestMessageType.AllUsersAddressesRequestType);
		//addField(allUsersAddresses);
	}
	
	public AllUsersAddressesRequest( IFieldsContainer container) throws Exception{
		//this( container.getFieldValue(AllUsersAddressesFieldValue.class).getUsersAddresses() );
	}
	
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}
}
