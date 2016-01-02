package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.MessageField;

public interface IMessageFieldValue {
	public MessageField getCorrespondingField();
	public IMessageFieldValue createFromString( String text) throws Exception;
}
