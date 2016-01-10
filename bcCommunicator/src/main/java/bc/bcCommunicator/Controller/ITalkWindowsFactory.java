package bc.bcCommunicator.Controller;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Views.ITalkWindow;

public interface ITalkWindowsFactory {

	ITalkWindow createTalkWindow(Username username, ICommunicatorController controller);
}
