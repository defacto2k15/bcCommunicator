package bc.bcCommunicator.Model.Messages.Letter;

import bc.bcCommunicator.Model.BasicTypes.Username;

public interface ILetterFactory {
	Letter create(LetterText text, LetterDate date, Username sender, Username recipient, LetterSendingType type);
}
