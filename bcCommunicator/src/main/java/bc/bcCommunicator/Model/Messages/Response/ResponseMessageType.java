package bc.bcCommunicator.Model.Messages.Response;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;

// TODO: Auto-generated Javadoc
/**
 * The Enum ResponseMessageType.
 */
public enum ResponseMessageType implements IMessageType {
	
	/** The Username ok. */
	UsernameOk("UsernameOk", UsernameOkResponse::new),
	
	/** The All users addresses response type. */
	AllUsersAddressesResponseType("AllUsersAddressesResponseType", AllUsersAddressesResponse::new), 
	
	/** The Username bad. */
	UsernameBadResponseType("UsernameBadResponseType", UsernameBadResponse::new);
	
	/** The name. */
	private String name;
	
	/** The from fields constructor. */
	private IMessageInitializedFromFields fromFieldsConstructor;
	
	/**
	 * Instantiates a new response message type.
	 *
	 * @param name the name
	 * @param fromFieldsConstructor the from fields constructor
	 */
	private  ResponseMessageType(String name, IMessageInitializedFromFields fromFieldsConstructor) {
		this.name = name;
		this.fromFieldsConstructor = fromFieldsConstructor;
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.IMessageType#getTypeName()
	 */
	@Override
	public String getTypeName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.IMessageType#getFromFieldsConstructor()
	 */
	@Override
	public IMessageInitializedFromFields getFromFieldsConstructor() {
		return fromFieldsConstructor;
	}

}
