package bc.bcCommunicator.Model.Messages;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.bcCommunicator.Model.Messages.Talk.ITalk;

public interface IModelMessageProvider {
	IRequest getIntroductoryRequest(Username username, URL clientUrl) throws Exception;

	IRequest getAllUsersAddressesRequest() throws Exception;

	ITalk getIntroductoryTalk(Username key, URL value) throws Exception;
	
	ITalk getLetterTalk(LetterDate date, LetterText text, Username sender, Username recipient) throws Exception;
}
