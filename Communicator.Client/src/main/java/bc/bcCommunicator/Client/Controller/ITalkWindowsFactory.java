package bc.bcCommunicator.Client.Controller;

import bc.bcCommunicator.Client.Views.ITalkWindow;
import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating ITalkWindows objects.
 */
public interface ITalkWindowsFactory {

	/**
	 * Creates a new ITalkWindows object.
	 *
	 * @param username the username
	 * @param controller the controller
	 * @return the i talk window
	 */
	ITalkWindow createTalkWindow(Username username, ICommunicatorController controller);
}
