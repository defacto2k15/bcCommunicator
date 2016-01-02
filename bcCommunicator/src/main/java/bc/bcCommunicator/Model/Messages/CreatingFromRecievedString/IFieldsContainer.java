package bc.bcCommunicator.Model.Messages.CreatingFromRecievedString;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.Optional;

import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue;

public interface IFieldsContainer {
	<T extends IMessageFieldValue> T getFieldValue(Class<T> type) throws Exception;
}
