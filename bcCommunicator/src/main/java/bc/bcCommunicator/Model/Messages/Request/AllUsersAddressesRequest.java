package bc.bcCommunicator.Model.Messages.Request;

import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class AllUsersAddressesRequest.
 */
public class AllUsersAddressesRequest extends AbstractRequest implements IAllUsersAddressesRequest{
	
	/**
	 * Instantiates a new all users addresses request.
	 *
	 * @throws Exception the exception
	 */
	public AllUsersAddressesRequest( /*AllUsersAddresses allUsersAddresses*/ ) throws Exception{
		addField(RequestMessageType.AllUsersAddressesRequestType);
		//addField(allUsersAddresses);
	}
	
	/**
	 * Instantiates a new all users addresses request.
	 *
	 * @param container the container
	 * @throws Exception the exception
	 */
	public AllUsersAddressesRequest( IFieldsContainer container) throws Exception{
		//this( container.getFieldValue(AllUsersAddressesFieldValue.class).getUsersAddresses() );
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.AbstractMessage#chooseHandler(bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler, bc.internetMessageProxy.ConnectionId)
	 */
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}
}
