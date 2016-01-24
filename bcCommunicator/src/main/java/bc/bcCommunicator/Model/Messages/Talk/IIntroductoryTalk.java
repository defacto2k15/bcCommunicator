package bc.bcCommunicator.Model.Messages.Talk;

import java.net.MalformedURLException;
import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Interface IIntroductoryTalk.
 */
public interface IIntroductoryTalk {

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	Username getUsername();

	/**
	 * Gets the url.
	 *
	 * @return the url
	 * @throws MalformedURLException the malformed url exception
	 */
	URL getUrl() throws MalformedURLException;

}