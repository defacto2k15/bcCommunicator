package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterTextMessageFieldValue.
 */
public class LetterTextMessageFieldValue  implements IMessageFieldValue{
	
	/** The letter text. */
	private LetterText letterText;

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#getCorrespondingField()
	 */
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.LETTER_TEXT_FIELD;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#createFromString(java.lang.String)
	 */
	@Override
	public IMessageFieldValue createFromString(String text) throws Exception {
		LetterTextMessageFieldValue newField = new LetterTextMessageFieldValue();
		newField.letterText = new LetterText(text);
		return newField;
	}
	
	/**
	 * Gets the letter text.
	 *
	 * @return the letter text
	 */
	public LetterText getLetterText(){
		return letterText;
	}
}
