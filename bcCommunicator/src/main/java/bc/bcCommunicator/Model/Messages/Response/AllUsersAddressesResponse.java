package bc.bcCommunicator.Model.Messages.Response;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.AllUsersAddressesFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;
import bc.internetMessageProxy.ConnectionId;

public class AllUsersAddressesResponse extends AbstractResponse implements IAllUsersAddressesResponse  {
	AllUsersAddresses allUsersAddresses;
	
	public AllUsersAddressesResponse( AllUsersAddresses allUsersAddresses ) throws Exception{
		this.allUsersAddresses = allUsersAddresses;
		addField(allUsersAddresses);
		addField(ResponseMessageType.AllUsersAddressesResponseType);
	}
	
	public AllUsersAddressesResponse( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(AllUsersAddressesFieldValue.class).getUsersAddresses() );
	}
	
	@Override
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}
	
	@Override
	public AllUsersAddresses getAllUsersAddresses() {
		return allUsersAddresses;
	}
}
