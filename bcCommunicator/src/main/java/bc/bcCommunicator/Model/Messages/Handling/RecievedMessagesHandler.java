package bc.bcCommunicator.Model.Messages.Handling;

import org.w3c.dom.views.AbstractView;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.UsernameOkResponse;
import bc.internetMessageProxy.ConnectionId;

public class RecievedMessagesHandler extends AbstractMessageHandler implements IRecievedMessagesHandler {
	UsernameOkResponseHandler usernameOkResponseHandler;
	
	public RecievedMessagesHandler(UsernameOkResponseHandler usernameOkResponseHandler){
		this.usernameOkResponseHandler = usernameOkResponseHandler;
	}
	
	@Override
	public void handle(IMessage message, ConnectionId id){
		try {
			message.chooseHandler(usernameOkResponseHandler, id);
		} catch (Exception e) {
			String messageText = null;
			try {
				messageText = message.getMessageText();
			} catch (Exception e1) {
				e1.printStackTrace();
			}		
			System.err.println("Exception during handling of message "+messageText);
			e.printStackTrace();
		}
	}

}
