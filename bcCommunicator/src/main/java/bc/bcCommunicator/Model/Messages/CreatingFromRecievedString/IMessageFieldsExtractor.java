package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

public interface IMessageFieldsExtractor {
	IFieldsContainer getFields(String text) throws Exception;
}
