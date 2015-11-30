package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.MessageField;

public class NullMessageFieldValue implements IMessageFieldValue {

	public NullMessageFieldValue() {
		System.err.println("This is constructor of placeholder NullMessageFieldValue. This should NOT be called");
		assert false;
	}
	
	@Override
	public MessageField getCorrespondingField() {
		assert false;
		return null; // As object cannot be constructed, this method should not be called
	}

	@Override
	public IMessageFieldValue createFromString(String text) throws Exception {
		assert false;
		return null;
	}

}
