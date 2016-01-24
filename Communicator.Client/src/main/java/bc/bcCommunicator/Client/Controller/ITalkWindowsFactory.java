package bc.bcCommunicator.Client.Controller;

import bc.bcCommunicator.Client.Views.ITalkWindow;
import bc.bcCommunicator.Model.BasicTypes.Username;

public interface ITalkWindowsFactory {

	ITalkWindow createTalkWindow(Username username, ICommunicatorController controller);
}
