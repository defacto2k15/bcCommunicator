package bc.bcCommunicator.Model.Messages;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.bcCommunicator.Model.Messages.Talk.ITalk;

// TODO: Auto-generated Javadoc
/**
 * The Interface IModelMessageProvider.
 */
public interface IModelMessageProvider {
	
	/**
	 * Gets the introductory request.
	 *
	 * @param username the username
	 * @param clientUrl the client url
	 * @return the introductory request
	 * @throws Exception the exception
	 */
	IRequest getIntroductoryRequest(Username username, URL clientUrl) throws Exception;

	/**
	 * Gets the all users addresses request.
	 *
	 * @return the all users addresses request
	 * @throws Exception the exception
	 */
	IRequest getAllUsersAddressesRequest() throws Exception;

	/**
	 * Gets the introductory talk.
	 *
	 * @param key the key
	 * @param value the value
	 * @return the introductory talk
	 * @throws Exception the exception
	 */
	ITalk getIntroductoryTalk(Username key, URL value) throws Exception;
	
	/**
	 * Gets the letter talk.
	 *
	 * @param date the date
	 * @param text the text
	 * @param sender the sender
	 * @param recipient the recipient
	 * @return the letter talk
	 * @throws Exception the exception
	 */
	ITalk getLetterTalk(LetterDate date, LetterText text, Username sender, Username recipient) throws Exception;
}
