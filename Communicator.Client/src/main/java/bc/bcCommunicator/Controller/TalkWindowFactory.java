package bc.bcCommunicator.Controller;

import bc.bcCommunicator.WindowNames;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Views.ITalkWindow;
import bc.bcCommunicator.Views.TalkWindowView;

public class TalkWindowFactory implements ITalkWindowsFactory{

	@Override
	public ITalkWindow createTalkWindow(Username username, ICommunicatorController controller) {
		return new TalkWindowView( username, controller);
	}

}
