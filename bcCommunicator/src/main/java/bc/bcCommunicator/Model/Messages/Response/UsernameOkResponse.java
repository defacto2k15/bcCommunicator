package bc.bcCommunicator.Model.Messages.Response;


import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class UsernameOkResponse.
 */
public class UsernameOkResponse  extends AbstractResponse implements IUsernameOkResponse {
	
	/**
	 * Instantiates a new username ok response.
	 *
	 * @param username the username
	 * @throws Exception the exception
	 */
	public UsernameOkResponse( Username username ) throws Exception{
		addField(username);
		addField(ResponseMessageType.UsernameOk);
	}
	
	/**
	 * Instantiates a new username ok response.
	 *
	 * @param container the container
	 * @throws Exception the exception
	 */
	public UsernameOkResponse( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(UsernameMessageFieldValue.class).getUsername());
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.AbstractMessage#chooseHandler(bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse#getUsername()
	 */
	public Username getUsername(){
		return new Username( fieldsInMessage.get(MessageField.USERNAME_FIELD) );
	}

}
