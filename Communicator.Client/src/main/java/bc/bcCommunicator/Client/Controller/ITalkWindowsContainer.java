package bc.bcCommunicator.Client.Controller;

import bc.bcCommunicator.Client.Views.ITalkWindow;
import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITalkWindowsContainer.
 */
public interface ITalkWindowsContainer {

	/**
	 * Checks if is window open for user.
	 *
	 * @param username the username
	 * @return true, if is window open for user
	 */
	boolean isWindowOpenForUser(Username username);

	/**
	 * Adds the window for user.
	 *
	 * @param username the username
	 * @param window the window
	 */
	void addWindowForUser(Username username, ITalkWindow window);

	/**
	 * Gets the user window.
	 *
	 * @param username the username
	 * @return the user window
	 */
	ITalkWindow getUserWindow(Username username);

}
