package bc.bcCommunicator.Model.Messages.Request;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;

// TODO: Auto-generated Javadoc
/**
 * The Enum RequestMessageType.
 */
public enum RequestMessageType implements IMessageType {
	
	/** The Introductory request type. */
	IntroductoryRequestType("IntroductoryRequestType", IntroductoryRequest::new),
	
	/** The All users addresses request type. */
	AllUsersAddressesRequestType( "AllUsersAddressesRequestType", AllUsersAddressesRequest::new);
	
	/** The type name. */
	private String typeName;
	
	/** The from fields constructor. */
	private IMessageInitializedFromFields fromFieldsConstructor;

	/**
	 * Instantiates a new request message type.
	 *
	 * @param typeName the type name
	 * @param fromFieldsConstructor the from fields constructor
	 */
	RequestMessageType(String typeName, IMessageInitializedFromFields fromFieldsConstructor){
		this.typeName = typeName;
		this.fromFieldsConstructor = fromFieldsConstructor;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.IMessageType#getTypeName()
	 */
	@Override
	public String getTypeName() {
		return typeName;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.IMessageType#getFromFieldsConstructor()
	 */
	@Override
	public IMessageInitializedFromFields getFromFieldsConstructor() {
		return fromFieldsConstructor;
	}

}
