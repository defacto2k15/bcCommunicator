package bc.bcCommunicator.Model.Messages.Request;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Interface IIntroductoryRequest.
 */
public interface IIntroductoryRequest {
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public Username getUsername();
	
	/**
	 * Gets the client url.
	 *
	 * @return the client url
	 */
	public URL getClientUrl();
}
