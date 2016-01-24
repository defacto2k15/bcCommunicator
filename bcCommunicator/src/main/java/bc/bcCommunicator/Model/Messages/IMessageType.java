package bc.bcCommunicator.Model.Messages;

import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMessageType.
 */
public interface IMessageType {
	
	/**
	 * Gets the type name.
	 *
	 * @return the type name
	 */
	public String getTypeName();

	/**
	 * Gets the from fields constructor.
	 *
	 * @return the from fields constructor
	 */
	public IMessageInitializedFromFields getFromFieldsConstructor();
}
