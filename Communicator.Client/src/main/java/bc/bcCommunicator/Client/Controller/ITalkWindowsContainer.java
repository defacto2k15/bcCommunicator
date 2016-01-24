package bc.bcCommunicator.Client.Controller;

import bc.bcCommunicator.Client.Views.ITalkWindow;
import bc.bcCommunicator.Model.BasicTypes.Username;

public interface ITalkWindowsContainer {

	boolean isWindowOpenForUser(Username username);

	void addWindowForUser(Username username, ITalkWindow window);

	ITalkWindow getUserWindow(Username username);

}
