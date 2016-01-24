package bc.bcCommunicator.Client.Model.Messages.Handling;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.Response.IUsernameBadResponse;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class UsernameBadMessageHandler.
 */
public class UsernameBadMessageHandler extends AbstractMessageHandler {
	
	/** The controller. */
	private ICommunicatorController controller;

	/**
	 * Instantiates a new username bad message handler.
	 *
	 * @param controller the controller
	 */
	public UsernameBadMessageHandler(ICommunicatorController controller) {
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Response.IUsernameBadResponse, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void handle( IUsernameBadResponse response, ConnectionId id) throws Exception{
		controller.usernameWasBad();
	}
	
}
