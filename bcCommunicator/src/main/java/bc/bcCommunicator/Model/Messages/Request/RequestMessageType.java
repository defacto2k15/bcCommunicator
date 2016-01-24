package bc.bcCommunicator.Model.Messages.Request;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;

public enum RequestMessageType implements IMessageType {
	IntroductoryRequestType("IntroductoryRequestType", IntroductoryRequest::new),
	AllUsersAddressesRequestType( "AllUsersAddressesRequestType", AllUsersAddressesRequest::new);
	
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
