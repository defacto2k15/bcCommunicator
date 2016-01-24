package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterDateMessageFieldValue.
 */
public class LetterDateMessageFieldValue implements IMessageFieldValue {

	/** The date. */
	LetterDate date;
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#getCorrespondingField()
	 */
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.DATE_FIELD;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#createFromString(java.lang.String)
	 */
	@Override
	public IMessageFieldValue createFromString(String text) throws Exception {
	    LetterDateMessageFieldValue fieldValue = new LetterDateMessageFieldValue();
	    fieldValue.date = new LetterDate(text);
	    return fieldValue;
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public LetterDate getDate(){
		return date;
	}

}
