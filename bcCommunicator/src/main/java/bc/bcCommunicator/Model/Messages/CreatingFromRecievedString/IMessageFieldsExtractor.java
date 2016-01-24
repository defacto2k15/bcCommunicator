package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

/**
 * The Interface IMessageFieldsExtractor. Takes message and fetches all fields from it, packing them into IFieldsCOntainer
 */
public interface IMessageFieldsExtractor {
	IFieldsContainer getFields(String text) throws Exception;
}
