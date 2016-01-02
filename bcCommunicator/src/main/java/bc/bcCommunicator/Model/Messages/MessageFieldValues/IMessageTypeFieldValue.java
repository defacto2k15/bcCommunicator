package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import java.awt.TrayIcon.MessageType;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;

public interface IMessageTypeFieldValue {
	IMessageType getMessageType();

}
