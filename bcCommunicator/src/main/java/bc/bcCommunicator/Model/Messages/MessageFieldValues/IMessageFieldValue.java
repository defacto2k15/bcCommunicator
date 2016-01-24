package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.MessageField;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMessageFieldValue.
 */
public interface IMessageFieldValue {
	
	/**
	 * Gets the corresponding field.
	 *
	 * @return the corresponding field
	 */
	public MessageField getCorrespondingField();
	
	/**
	 * Creates the from string.
	 *
	 * @param text the text
	 * @return the i message field value
	 * @throws Exception the exception
	 */
	public IMessageFieldValue createFromString( String text) throws Exception;
}
