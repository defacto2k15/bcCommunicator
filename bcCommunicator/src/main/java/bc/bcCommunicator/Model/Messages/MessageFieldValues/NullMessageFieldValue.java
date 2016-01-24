package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.MessageField;

// TODO: Auto-generated Javadoc
/**
 * The Class NullMessageFieldValue.
 */
public class NullMessageFieldValue implements IMessageFieldValue {

	/**
	 * Instantiates a new null message field value.
	 */
	public NullMessageFieldValue() {
		System.err.println("This is constructor of placeholder NullMessageFieldValue. This should NOT be called");
		assert false;
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#getCorrespondingField()
	 */
	@Override
	public MessageField getCorrespondingField() {
		assert false;
		return null; // As object cannot be constructed, this method should not be called
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#createFromString(java.lang.String)
	 */
	@Override
	public IMessageFieldValue createFromString(String text) throws Exception {
		assert false;
		return null;
	}

}
