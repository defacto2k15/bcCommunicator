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

// TODO: Auto-generated Javadoc
/**
 * The Class ModelMessageProvider.
 */
public class ModelMessageProvider implements IModelMessageProvider{

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.IModelMessageProvider#getIntroductoryRequest(bc.bcCommunicator.Model.BasicTypes.Username, java.net.URL)
	 */
	@Override
	public IRequest getIntroductoryRequest(Username username, URL clientUrl) throws Exception {
		return new IntroductoryRequest(username, clientUrl);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.IModelMessageProvider#getAllUsersAddressesRequest()
	 */
	@Override
	public IRequest getAllUsersAddressesRequest() throws Exception {
		return new AllUsersAddressesRequest();
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.IModelMessageProvider#getIntroductoryTalk(bc.bcCommunicator.Model.BasicTypes.Username, java.net.URL)
	 */
	@Override
	public ITalk getIntroductoryTalk(Username username, URL url) throws Exception {
		return new IntroductoryTalk(username, url);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.IModelMessageProvider#getLetterTalk(bc.bcCommunicator.Model.Messages.Letter.LetterDate, bc.bcCommunicator.Model.Messages.Letter.LetterText, bc.bcCommunicator.Model.BasicTypes.Username, bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public ITalk getLetterTalk(LetterDate date, LetterText text, Username sender, Username recipient) throws Exception {
		return new LetterTalk(date, text, sender, recipient);
	}

}
