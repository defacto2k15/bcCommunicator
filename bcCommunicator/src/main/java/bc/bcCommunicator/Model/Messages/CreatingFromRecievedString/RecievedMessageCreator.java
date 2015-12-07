package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.ResponseMessageTypeFieldValue;
import bc.bcCommunicator.Model.Messages.Response.ResponseMessageType;

public class RecievedMessageCreator implements IRecievedMessageCreator {

	private IMessageFieldsExtractor extractor;
	private IMessageFromTypeCreator fromTypeCreator;

	public RecievedMessageCreator(IMessageFieldsExtractor extractor, IMessageFromTypeCreator fromTypeCreator) {
		this.extractor = extractor;
		this.fromTypeCreator = fromTypeCreator;
	}

	@Override
	public IMessage createMessage(String message) throws Exception {
		IFieldsContainer container =  extractor.getFields(message); // TODO other type than response!
		ResponseMessageTypeFieldValue responseType = container.getFieldValue(ResponseMessageTypeFieldValue.class);
		IMessageInitializedFromFields messageToInitialize = fromTypeCreator.get(responseType);
		return messageToInitialize.getMessage( container );
	}

}
