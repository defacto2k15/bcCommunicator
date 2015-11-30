package bc.bcCommunicator.Model.Messages;

import java.net.URL;
import java.util.Map;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.AllUsersAddressesFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UrlMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;

public class AllUsersAddressesRequest extends AbstractRequest {
	public AllUsersAddressesRequest( AllUsersAddresses allUsersAddresses ) throws Exception{
		addField(RequestMessageType.AllUsersAddressesRequestType);
		addField(allUsersAddresses);
	}
	
	public AllUsersAddressesRequest( IFieldsContainer container) throws Exception{
		this( container.getFieldValue(AllUsersAddressesFieldValue.class).getUsersAddresses() );
	}
}
