package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.MessageField;

public class UsernameMessageFieldValue implements IMessageFieldValue{
	Username username;
	
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.USERNAME_FIELD;
	}

	@Override
	public IMessageFieldValue createFromString(String text) {
		UsernameMessageFieldValue newValue = new UsernameMessageFieldValue();
		newValue.username = new Username(text);
		return newValue;
	}
	
	public Username getUsername(){
		return username;
	}
	
}