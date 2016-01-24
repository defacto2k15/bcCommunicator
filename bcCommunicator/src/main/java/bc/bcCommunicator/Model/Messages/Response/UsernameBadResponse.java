package bc.bcCommunicator.Model.Messages.Response;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class UsernameBadResponse.
 */
public class UsernameBadResponse extends AbstractResponse implements IUsernameBadResponse{
	
	/**
	 * Instantiates a new username bad response.
	 *
	 * @param username the username
	 * @throws Exception the exception
	 */
	public UsernameBadResponse( Username username ) throws Exception{
		addField(username);
		addField(ResponseMessageType.UsernameBadResponseType);
	}
	
	/**
	 * Instantiates a new username bad response.
	 *
	 * @param container the container
	 * @throws Exception the exception
	 */
	public UsernameBadResponse( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(UsernameMessageFieldValue.class).getUsername());
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.AbstractMessage#chooseHandler(bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public Username getUsername(){
		return new Username( fieldsInMessage.get(MessageField.USERNAME_FIELD) );
	}
}
