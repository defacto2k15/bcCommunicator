package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.IMessage;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMessageInitializedFromFields. It gets FieldsContainer, analyzes it and returns concrete message
 */
public interface IMessageInitializedFromFields {
	
	/**
	 * Gets the message.
	 *
	 * @param container the container
	 * @return the message
	 * @throws Exception the exception
	 */
	IMessage getMessage( IFieldsContainer container ) throws Exception;

}
