package bc.bcCommunicator.Model.Messages.Talk;

import bc.bcCommunicator.Model.Messages.IMessageType;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IMessageInitializedFromFields;

public enum TalkMessageType implements IMessageType{
	IntroductoryTalkType("IntroductoryTalkType", IntroductoryTalk::new), LetterTalkType("LetterTalkType", LetterTalk::new);
	
	private String typeName;
	private IMessageInitializedFromFields fromFieldsConstructor;
	
	TalkMessageType(String typeName, IMessageInitializedFromFields fromFieldsConstructor){
		this.typeName = typeName;
		this.fromFieldsConstructor = fromFieldsConstructor;
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	public IMessageInitializedFromFields getFromFieldsConstructor() {
		return fromFieldsConstructor;
	}

}
