package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.MessageField;

public class RecipientFieldValue implements IMessageFieldValue{
	Username username;
	
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.RECIPIENT_FIELD;
	}

	@Override
	public IMessageFieldValue createFromString(String text) {
		RecipientFieldValue newValue = new RecipientFieldValue();
		newValue.username = new Username(text);
		return newValue;
	}
	
	public Username getUsername(){
		return username;
	}
	
}