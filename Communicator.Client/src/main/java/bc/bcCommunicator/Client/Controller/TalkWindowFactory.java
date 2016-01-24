package bc.bcCommunicator.Client.Controller;

import bc.bcCommunicator.Client.Views.ITalkWindow;
import bc.bcCommunicator.Client.Views.TalkWindowView;
import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating TalkWindow objects.
 */
public class TalkWindowFactory implements ITalkWindowsFactory{

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ITalkWindowsFactory#createTalkWindow(bc.bcCommunicator.Model.BasicTypes.Username, bc.bcCommunicator.Client.Controller.ICommunicatorController)
	 */
	@Override
	public ITalkWindow createTalkWindow(Username username, ICommunicatorController controller) {
		return new TalkWindowView( username, controller);
	}

}
