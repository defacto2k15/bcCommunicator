package bc.bcCommunicator.Model.Messages.Handling;

import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.ILetterContainer;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Talk.ILetterTalk;
import bc.internetMessageProxy.ConnectionId;

public class LetterTalkMessageHandler extends AbstractMessageHandler{

	private ILetterContainer container;
	private ICommunicatorController controller;

	public LetterTalkMessageHandler(ILetterContainer container, ICommunicatorController controller) {
		this.container = container;
		this.controller = controller;
	}

	@Override
	public void handle( ILetterTalk letterTalk, ConnectionId id) throws Exception{
		Letter letter = letterTalk.getLetter();
		controller.recievedNewLetter(letter);
		container.addLetterOfTalkToUser(letter.sender, letter);
	}
}
