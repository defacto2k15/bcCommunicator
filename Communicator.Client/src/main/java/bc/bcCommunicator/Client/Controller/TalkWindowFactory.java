package bc.bcCommunicator.Client.Controller;

import bc.bcCommunicator.Client.Views.ITalkWindow;
import bc.bcCommunicator.Client.Views.TalkWindowView;
import bc.bcCommunicator.Model.BasicTypes.Username;

public class TalkWindowFactory implements ITalkWindowsFactory{

	@Override
	public ITalkWindow createTalkWindow(Username username, ICommunicatorController controller) {
		return new TalkWindowView( username, controller);
	}

}
