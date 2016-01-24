package bc.bcCommunicator.Client.Model.Messages.Handling;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.Model.IActorUsernameContainer;
import bc.bcCommunicator.Client.Model.IModelMessagesSender;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class UsernameOkResponseHandler.
 */
public class UsernameOkResponseHandler extends AbstractMessageHandler {
	
	/** The container. */
	private IActorUsernameContainer container;
	
	/** The messages sender. */
	private IModelMessagesSender messagesSender;
	
	/** The controller. */
	private ICommunicatorController controller;

	/**
	 * Instantiates a new username ok response handler.
	 *
	 * @param container the container
	 * @param messagesSender the messages sender
	 * @param controller the controller
	 */
	public UsernameOkResponseHandler( IActorUsernameContainer container,
			IModelMessagesSender messagesSender, ICommunicatorController controller){
		this.container = container;
		this.messagesSender = messagesSender;
		this.controller = controller;
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void handle( IUsernameOkResponse response, ConnectionId id) throws Exception{
		container.setUsername(response.getUsername());
		controller.usernameWasOk();
		messagesSender.sendAllUsersAddressesRequest();
	}
}
