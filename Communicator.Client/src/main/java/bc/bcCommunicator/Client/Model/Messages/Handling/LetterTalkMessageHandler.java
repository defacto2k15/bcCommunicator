package bc.bcCommunicator.Client.Model.Messages.Handling;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.Model.ILetterContainer;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Talk.ILetterTalk;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterTalkMessageHandler.
 */
public class LetterTalkMessageHandler extends AbstractMessageHandler{

	/** The container. */
	private ILetterContainer container;
	
	/** The controller. */
	private ICommunicatorController controller;

	/**
	 * Instantiates a new letter talk message handler.
	 *
	 * @param container the container
	 * @param controller the controller
	 */
	public LetterTalkMessageHandler(ILetterContainer container, ICommunicatorController controller) {
		this.container = container;
		this.controller = controller;
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Talk.ILetterTalk, bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void handle( ILetterTalk letterTalk, ConnectionId id) throws Exception{
		Letter letter = letterTalk.getLetter();
		controller.recievedNewLetter(letter);
		container.addLetterOfTalkToUser(letter.sender, letter);
	}
}
