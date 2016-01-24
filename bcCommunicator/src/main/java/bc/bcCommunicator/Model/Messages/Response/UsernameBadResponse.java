package bc.bcCommunicator.Model.Messages.Response;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;
import bc.internetMessageProxy.ConnectionId;

public class UsernameBadResponse extends AbstractResponse implements IUsernameBadResponse{
	public UsernameBadResponse( Username username ) throws Exception{
		addField(username);
		addField(ResponseMessageType.UsernameBad);
	}
	
	public UsernameBadResponse( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(UsernameMessageFieldValue.class).getUsername());
	}
	
	@Override
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}
	
	public Username getUsername(){
		return new Username( fieldsInMessage.get(MessageField.USERNAME_FIELD) );
	}
}
