package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import java.util.HashMap;
import java.util.Map;

import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;

public class MessageFieldsValuesCreator implements IMessageFieldsValuesCreator {
	Map< MessageField, IMessageFieldValue> map = new HashMap< >();
	
	public MessageFieldsValuesCreator(){
		map.put(MessageField.USERNAME_FIELD, new UsernameMessageFieldValue());
	}
	
	@Override
	public IMessageFieldValue create(MessageField field, String value) throws Exception {
		return field.getFieldValueConstructor().create().createFromString(value);
	}

}
