package bc.bcCommunicator.Model.Messages.Talk;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IFieldsContainer;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterSendingType;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.LetterDateMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.LetterTextMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.RecipientFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UrlMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;
import bc.internetMessageProxy.ConnectionId;

public class LetterTalk extends AbstractTalk implements ILetterTalk {
	public LetterTalk(LetterDate date, LetterText letterText, Username sender, Username reciever) throws Exception {
		addField(TalkMessageType.LetterTalkType);
		addField(date);
		addField(letterText);
		addField(sender);
		addField(reciever, true);
	}
	
	public LetterTalk( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(LetterDateMessageFieldValue.class).getDate(),
				container.getFieldValue(LetterTextMessageFieldValue.class).getLetterText(),
					container.getFieldValue(UsernameMessageFieldValue.class).getUsername(),
					container.getFieldValue(RecipientFieldValue.class).getUsername());
	}
	

	@Override
	public Letter getLetter() throws ParseException {
		return new Letter(
				new LetterText( fieldsInMessage.get(MessageField.LETTER_TEXT_FIELD)),
				new LetterDate( fieldsInMessage.get(MessageField.DATE_FIELD)),
				new Username( fieldsInMessage.get(MessageField.USERNAME_FIELD)),
				new Username( fieldsInMessage.get(MessageField.RECIPIENT_FIELD)),
				LetterSendingType.Recieved);
	}
	
	
	@Override
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}
	
}
