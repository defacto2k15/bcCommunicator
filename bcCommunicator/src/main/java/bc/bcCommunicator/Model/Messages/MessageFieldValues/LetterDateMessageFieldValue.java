package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;

public class LetterDateMessageFieldValue implements IMessageFieldValue {

	LetterDate date;
	
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.DATE_FIELD;
	}

	@Override
	public IMessageFieldValue createFromString(String text) throws Exception {
	    LetterDateMessageFieldValue fieldValue = new LetterDateMessageFieldValue();
	    fieldValue.date = new LetterDate(text);
	    return fieldValue;
	}
	
	public LetterDate getDate(){
		return date;
	}

}
