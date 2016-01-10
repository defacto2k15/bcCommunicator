package bc.bcCommunicator.Model.Messages;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.Request.AllUsersAddressesRequest;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.bcCommunicator.Model.Messages.Request.IntroductoryRequest;
import bc.bcCommunicator.Model.Messages.Talk.ITalk;
import bc.bcCommunicator.Model.Messages.Talk.IntroductoryTalk;
import bc.bcCommunicator.Model.Messages.Talk.LetterTalk;

public class ModelMessageProvider implements IModelMessageProvider{

	@Override
	public IRequest getIntroductoryRequest(Username username, URL clientUrl) throws Exception {
		return new IntroductoryRequest(username, clientUrl);
	}

	@Override
	public IRequest getAllUsersAddressesRequest() throws Exception {
		return new AllUsersAddressesRequest();
	}

	@Override
	public ITalk getIntroductoryTalk(Username username, URL url) throws Exception {
		return new IntroductoryTalk(username, url);
	}

	@Override
	public ITalk getLetterTalk(LetterDate date, LetterText text, Username recipient) throws Exception {
		return new LetterTalk(date, text, recipient);
	}

}
