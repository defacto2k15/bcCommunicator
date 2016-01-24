package bc.bcCommunicator.Model.Messages.MessageFieldValues;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.MessageField;
import bc.bcCommunicator.Model.Messages.Talk.TalkMessageType;

// TODO: Auto-generated Javadoc
/**
 * The Class TalkMessageTypeFieldValue.
 */
public class TalkMessageTypeFieldValue implements  IMessageFieldValue, IMessageTypeFieldValue {

	/** The type. */
	public TalkMessageType type = TalkMessageType.IntroductoryTalkType;
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#getCorrespondingField()
	 */
	@Override
	public MessageField getCorrespondingField() {
		return MessageField.TALK_TYPE_FIELD;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageFieldValue#createFromString(java.lang.String)
	 */
	@Override
	public IMessageFieldValue createFromString(String text) {
		TalkMessageTypeFieldValue newValue = new TalkMessageTypeFieldValue();
		newValue.type = TalkMessageType.valueOf(text);
		return newValue;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.MessageFieldValues.IMessageTypeFieldValue#getMessageType()
	 */
	@Override
	public IMessageType getMessageType() {
		return type;
	}
}
