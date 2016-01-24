package bc.bcCommunicator.Model.Messages.Talk;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;

// TODO: Auto-generated Javadoc
/**
 * The Enum TalkMessageType.
 */
public enum TalkMessageType implements IMessageType{
	
	/** The Introductory talk type. */
	IntroductoryTalkType("IntroductoryTalkType", IntroductoryTalk::new), 
 /** The Letter talk type. */
 LetterTalkType("LetterTalkType", LetterTalk::new);
	
	/** The type name. */
	private String typeName;
	
	/** The from fields constructor. */
	private IMessageInitializedFromFields fromFieldsConstructor;
	
	/**
	 * Instantiates a new talk message type.
	 *
	 * @param typeName the type name
	 * @param fromFieldsConstructor the from fields constructor
	 */
	TalkMessageType(String typeName, IMessageInitializedFromFields fromFieldsConstructor){
		this.typeName = typeName;
		this.fromFieldsConstructor = fromFieldsConstructor;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.IMessageType#getTypeName()
	 */
	@Override
	public String getTypeName() {
		return typeName;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.IMessageType#getFromFieldsConstructor()
	 */
	@Override
	public IMessageInitializedFromFields getFromFieldsConstructor() {
		return fromFieldsConstructor;
	}

}
