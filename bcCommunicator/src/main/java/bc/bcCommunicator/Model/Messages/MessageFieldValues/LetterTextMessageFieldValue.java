package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;

public class LetterTextMessageFieldValue  implements IMessageFieldValue{
	private LetterText letterText;

	@Override
	public MessageField getCorrespondingField() {
		return MessageField.LETTER_TEXT_FIELD;
	}

	@Override
	public IMessageFieldValue createFromString(String text) throws Exception {
		LetterTextMessageFieldValue newField = new LetterTextMessageFieldValue();
		newField.letterText = new LetterText(text);
		return newField;
	}
	
	public LetterText getLetterText(){
		return letterText;
	}
}
