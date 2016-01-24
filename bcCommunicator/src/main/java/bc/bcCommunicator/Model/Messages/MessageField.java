package bc.bcCommunicator.Model.Messages;

import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageFieldValueConstructor;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.AllUsersAddressesFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.LetterDateMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.LetterTextMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.NullMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.RecipientFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.RequestMessageTypeFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.ResponseMessageTypeFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.TalkMessageTypeFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UrlMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;

// TODO: Auto-generated Javadoc
/**
 * The Enum MessageField.
 */
public enum MessageField {
	
	/** The username field. */
	USERNAME_FIELD("USERNAME_FIELD", UsernameMessageFieldValue::new),
	
	/** The client url field. */
	CLIENT_URL_FIELD("CLIENT_URL_FIELD", UrlMessageFieldValue::new),
	
	/** The message type field. */
	MESSAGE_TYPE_FIELD("MESSAGE_TYPE", NullMessageFieldValue::new),
	
	/** The request type field. */
	REQUEST_TYPE_FIELD("REQUEST_TYPE", RequestMessageTypeFieldValue::new),
	
	/** The response type field. */
	RESPONSE_TYPE_FIELD("RESPONSE_TYPE", ResponseMessageTypeFieldValue::new),
	
	/** The All users addresses. */
	AllUsersAddresses("ALL_USERS_ADDRESSES", AllUsersAddressesFieldValue::new),
	
	/** The talk type field. */
	TALK_TYPE_FIELD("TALK_TYPE", TalkMessageTypeFieldValue::new),
	
	/** The date field. */
	DATE_FIELD("DATE_FIELD", LetterDateMessageFieldValue::new), 
	
	/** The letter text field. */
	LETTER_TEXT_FIELD("LETTER_TEXT_FIELD", LetterTextMessageFieldValue::new),
	
	/** The recipient field. */
	RECIPIENT_FIELD("RECIPIENT_FIELD", RecipientFieldValue::new);
	
	/** The text. */
	private String text;
	
	/** The field value constructor. */
	private IMessageFieldValueConstructor fieldValueConstructor;

	/**
	 * Instantiates a new message field.
	 *
	 * @param text the text
	 * @param constructor the constructor
	 */
	private MessageField(String text, IMessageFieldValueConstructor constructor){
		this.text = text;
		this.fieldValueConstructor = constructor;
	}
	
	/**
	 * Gets the field name.
	 *
	 * @return the field name
	 */
	public String getFieldName(){
		return text;
	}
	
	/**
	 * Gets the field value constructor.
	 *
	 * @return the field value constructor
	 */
	public IMessageFieldValueConstructor getFieldValueConstructor(){
		return fieldValueConstructor;
	}
}
