package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import java.util.HashMap;
import java.util.Map;

import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;

// TODO: Auto-generated Javadoc
/**
 * The Class MessageFieldsValuesCreator.
 */
public class MessageFieldsValuesCreator implements IMessageFieldsValuesCreator {
	
	/** The map. */
	Map< MessageField, IMessageFieldValue> map = new HashMap< >();
	
	/**
	 * Instantiates a new message fields values creator.
	 */
	public MessageFieldsValuesCreator(){
		map.put(MessageField.USERNAME_FIELD, new UsernameMessageFieldValue());
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageFieldsValuesCreator#create(bc.bcCommunicator.Model.Messages.MessageField, java.lang.String)
	 */
	@Override
	public IMessageFieldValue create(MessageField field, String value) throws Exception {
		return field.getFieldValueConstructor().create().createFromString(value);
	}

}
