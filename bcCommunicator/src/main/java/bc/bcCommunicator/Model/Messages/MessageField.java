package bc.bcCommunicator.Model.Messages;

import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageFieldValueConstructor;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.AllUsersAddressesFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.NullMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.RequestMessageTypeFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.ResponseMessageTypeFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UrlMessageFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.UsernameMessageFieldValue;

public enum MessageField {
	USERNAME_FIELD("USERNAME_FIELD", UsernameMessageFieldValue::new),
	CLIENT_URL_FIELD("CLIENT_URL_FIELD", UrlMessageFieldValue::new),
	MESSAGE_TYPE_FIELD("MESSAGE_TYPE", NullMessageFieldValue::new),
	REQUEST_TYPE_FIELD("REQUEST_TYPE", RequestMessageTypeFieldValue::new),
	RESPONSE_TYPE_FIELD("RESPONSE_TYPE", ResponseMessageTypeFieldValue::new),
	AllUsersAddresses("ALL_USERS_ADDRESSES", AllUsersAddressesFieldValue::new);
	
	private String text;
	private IMessageFieldValueConstructor fieldValueConstructor;

	private MessageField(String text, IMessageFieldValueConstructor constructor){
		this.text = text;
		this.fieldValueConstructor = constructor;
	}
	
	public String getFieldName(){
		return text;
	}
	
	public IMessageFieldValueConstructor getFieldValueConstructor(){
		return fieldValueConstructor;
	}
}
