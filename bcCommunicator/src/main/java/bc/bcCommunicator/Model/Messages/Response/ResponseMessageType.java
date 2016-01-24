package bc.bcCommunicator.Model.Messages.Response;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

public enum ResponseMessageType implements IMessageType {
	UsernameOk("UsernameOk", UsernameOkResponse::new),
	AllUsersAddressesResponseType("AllUsersAddressesResponseType", AllUsersAddressesResponse::new), 
	UsernameBad("UsernameBadResponseType", UsernameBadResponse::new);
	
	private String name;
	private IMessageInitializedFromFields fromFieldsConstructor;
	
	private  ResponseMessageType(String name, IMessageInitializedFromFields fromFieldsConstructor) {
		this.name = name;
		this.fromFieldsConstructor = fromFieldsConstructor;
	}
	
	@Override
	public String getTypeName() {
		return name;
	}

	@Override
	public IMessageInitializedFromFields getFromFieldsConstructor() {
		return fromFieldsConstructor;
	}

}
