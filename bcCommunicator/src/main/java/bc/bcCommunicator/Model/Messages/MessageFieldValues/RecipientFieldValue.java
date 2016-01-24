package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.MessageField;

// TODO: Auto-generated Javadoc
/**
 * The Class RecipientFieldValue.
 */
public class RecipientFieldValue implements IMessageFieldValue{
	
	/** The username. */
	Username username;
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#getCorrespondingField()
	 */
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.RECIPIENT_FIELD;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#createFromString(java.lang.String)
	 */
	@Override
	public IMessageFieldValue createFromString(String text) {
		RecipientFieldValue newValue = new RecipientFieldValue();
		newValue.username = new Username(text);
		return newValue;
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public Username getUsername(){
		return username;
	}
	
}