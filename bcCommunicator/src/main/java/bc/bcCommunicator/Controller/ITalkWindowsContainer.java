package bc.bcCommunicator.Controller;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Views.ITalkWindow;

public interface ITalkWindowsContainer {

	boolean isWindowOpenForUser(Username username);

	void addWindowForUser(Username username, ITalkWindow window);

	ITalkWindow getUserWindow(Username username);

}
