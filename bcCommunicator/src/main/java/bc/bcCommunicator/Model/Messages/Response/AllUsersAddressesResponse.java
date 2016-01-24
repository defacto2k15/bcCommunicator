package bc.bcCommunicator.Model.Messages.Response;

import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.AllUsersAddressesFieldValue;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class AllUsersAddressesResponse.
 */
public class AllUsersAddressesResponse extends AbstractResponse implements IAllUsersAddressesResponse  {
	
	/** The all users addresses. */
	AllUsersAddresses allUsersAddresses;
	
	/**
	 * Instantiates a new all users addresses response.
	 *
	 * @param allUsersAddresses the all users addresses
	 * @throws Exception the exception
	 */
	public AllUsersAddressesResponse( AllUsersAddresses allUsersAddresses ) throws Exception{
		this.allUsersAddresses = allUsersAddresses;
		addField(allUsersAddresses);
		addField(ResponseMessageType.AllUsersAddressesResponseType);
	}
	
	/**
	 * Instantiates a new all users addresses response.
	 *
	 * @param container the container
	 * @throws Exception the exception
	 */
	public AllUsersAddressesResponse( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(AllUsersAddressesFieldValue.class).getUsersAddresses() );
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.AbstractMessage#chooseHandler(bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse#getAllUsersAddresses()
	 */
	@Override
	public AllUsersAddresses getAllUsersAddresses() {
		return allUsersAddresses;
	}
}
