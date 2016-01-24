package bc.bcCommunicator.Model.Messages.Talk;

import java.text.ParseException;

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
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterTalk.
 */
public class LetterTalk extends AbstractTalk implements ILetterTalk {
	
	/**
	 * Instantiates a new letter talk.
	 *
	 * @param date the date
	 * @param letterText the letter text
	 * @param sender the sender
	 * @param reciever the reciever
	 * @throws Exception the exception
	 */
	public LetterTalk(LetterDate date, LetterText letterText, Username sender, Username reciever) throws Exception {
		addField(TalkMessageType.LetterTalkType);
		addField(date);
		addField(letterText);
		addField(sender);
		addField(reciever, true);
	}
	
	/**
	 * Instantiates a new letter talk.
	 *
	 * @param container the container
	 * @throws Exception the exception
	 */
	public LetterTalk( IFieldsContainer container) throws Exception{
		this(container.getFieldValue(LetterDateMessageFieldValue.class).getDate(),
				container.getFieldValue(LetterTextMessageFieldValue.class).getLetterText(),
					container.getFieldValue(UsernameMessageFieldValue.class).getUsername(),
					container.getFieldValue(RecipientFieldValue.class).getUsername());
	}
	

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Talk.ILetterTalk#getLetter()
	 */
	@Override
	public Letter getLetter() throws ParseException {
		return new Letter(
				new LetterText( fieldsInMessage.get(MessageField.LETTER_TEXT_FIELD)),
				new LetterDate( fieldsInMessage.get(MessageField.DATE_FIELD)),
				new Username( fieldsInMessage.get(MessageField.USERNAME_FIELD)),
				new Username( fieldsInMessage.get(MessageField.RECIPIENT_FIELD)),
				LetterSendingType.Recieved);
	}
	
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.AbstractMessage#chooseHandler(bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void chooseHandler( AbstractMessageHandler handler, ConnectionId id) throws Exception{
		handler.handle(this, id);
	}
	
}
