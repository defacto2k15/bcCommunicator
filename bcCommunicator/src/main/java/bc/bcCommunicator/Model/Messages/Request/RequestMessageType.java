package bc.bcCommunicator.Model.Messages.Request;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

public enum RequestMessageType implements IMessageType {
	IntroductoryRequestType("IntroductoryRequest", IntroductoryRequest::new),
	AllUsersAddressesRequestType( "AllUsersAddressesRequest", AllUsersAddressesRequest::new);
	
	private String typeName;
	private IMessageInitializedFromFields fromFieldsConstructor;

	RequestMessageType(String typeName, IMessageInitializedFromFields fromFieldsConstructor){
		this.typeName = typeName;
		this.fromFieldsConstructor = fromFieldsConstructor;
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public IMessageInitializedFromFields getFromFieldsConstructor() {
		return fromFieldsConstructor;
	}

}
