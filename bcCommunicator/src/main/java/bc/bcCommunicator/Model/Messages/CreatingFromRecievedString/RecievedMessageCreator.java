package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.RequestMessageTypeFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.ResponseMessageTypeFieldValue;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.TalkMessageTypeFieldValue;
import bc.bcCommunicator.Model.Messages.Request.RequestMessageType;
import bc.bcCommunicator.Model.Messages.Response.ResponseMessageType;
import bc.bcCommunicator.Model.Messages.Talk.TalkMessageType;

public class RecievedMessageCreator implements IRecievedMessageCreator {

	private IMessageFieldsExtractor extractor;
	private IMessageFromTypeCreator fromTypeCreator;

	public RecievedMessageCreator(IMessageFieldsExtractor extractor, IMessageFromTypeCreator fromTypeCreator) {
		this.extractor = extractor;
		this.fromTypeCreator = fromTypeCreator;
	}

	@Override
	public IMessage createMessage(String message) throws Exception {
		IMessageInitializedFromFields messageToInitialize = null;
		IFieldsContainer container =  extractor.getFields(message); // TODO other type than response!
		if( container.containsField(ResponseMessageTypeFieldValue.class)){
			ResponseMessageTypeFieldValue responseType = container.getFieldValue(ResponseMessageTypeFieldValue.class);
			messageToInitialize = fromTypeCreator.get(responseType);
		} else if( container.containsField(TalkMessageTypeFieldValue.class)){
			TalkMessageTypeFieldValue responseType = container.getFieldValue(TalkMessageTypeFieldValue.class);
			messageToInitialize = fromTypeCreator.get(responseType);			
		} else if (container.containsField(RequestMessageTypeFieldValue.class)){
			RequestMessageTypeFieldValue requestType = container.getFieldValue(RequestMessageTypeFieldValue.class);
			messageToInitialize = fromTypeCreator.get(requestType);
		}else {
			throw new IllegalArgumentException("Problem: message do not have field value of responseType nor TalkType");
		}
		return messageToInitialize.getMessage( container );
	}

}
