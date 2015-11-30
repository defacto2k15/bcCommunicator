package bc.bcCommunicator.Model.Messages;

import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Handling.UsernameOkResponseHandler;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

public enum ResponseMessageType implements IMessageType {
	UsernameOk("UsernameOk", UsernameOkResponse::new);
	
	private String name;
	private IMessageInitializedFromFields fromFieldsConstructor;
	
	private  ResponseMessageType(String name, IMessageInitializedFromFields fromFieldsConstructor) {
		this.name = name;
		this.fromFieldsConstructor = fromFieldsConstructor;
	}
	
	@Override
	public String getTypeName() {
		return name;
	}

	@Override
	public IMessageInitializedFromFields getFromFieldsConstructor() {
		return fromFieldsConstructor;
	}

}
