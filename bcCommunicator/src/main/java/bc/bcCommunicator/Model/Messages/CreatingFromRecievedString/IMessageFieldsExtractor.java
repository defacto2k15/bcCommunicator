package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

// TODO: Auto-generated Javadoc
/**
 * The Interface IMessageFieldsExtractor. Takes message and fetches all fields from it, packing them into IFieldsCOntainer
 */
public interface IMessageFieldsExtractor {
	
	/**
	 * Gets the fields.
	 *
	 * @param text the text
	 * @return the fields
	 * @throws Exception the exception
	 */
	IFieldsContainer getFields(String text) throws Exception;
}
