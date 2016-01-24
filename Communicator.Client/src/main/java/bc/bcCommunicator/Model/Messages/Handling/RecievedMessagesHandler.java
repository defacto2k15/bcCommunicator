package bc.bcCommunicator.Model.Messages.Handling;

import org.w3c.dom.views.AbstractView;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameOkResponse;
import bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk;
import bc.bcCommunicator.Model.Messages.Talk.ILetterTalk;
import bc.bcCommunicator.Model.Messages.Talk.LetterTalk;
import bc.internetMessageProxy.ConnectionId;

public class RecievedMessagesHandler extends AbstractMessageHandler implements IRecievedMessagesHandler {
	UsernameOkResponseHandler usernameOkResponseHandler;
	AllUsersAddressesResponseHandler allUsersAddressesResponseHandler;
	private IntroductoryTalkHandler introductoryTalkHandler;
	private LetterTalkMessageHandler letterTalkHandler;
	
	public RecievedMessagesHandler(UsernameOkResponseHandler usernameOkResponseHandler,
			AllUsersAddressesResponseHandler allUsersAddressesResponseHandler, 
			IntroductoryTalkHandler introductoryTalkHandler,
			LetterTalkMessageHandler letterTalkHandler){
		this.usernameOkResponseHandler = usernameOkResponseHandler;
		this.allUsersAddressesResponseHandler = allUsersAddressesResponseHandler;
		this.introductoryTalkHandler = introductoryTalkHandler;
		this.letterTalkHandler = letterTalkHandler;
	}
	
	@Override
	public void handle(IMessage message, ConnectionId id){
		try {
			message.chooseHandler(this, id);
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
	
	public void handle( IUsernameOkResponse usernameOkResponse, ConnectionId id) throws Exception{
		usernameOkResponseHandler.handle(usernameOkResponse, id);
	}
	
	public void handle( IAllUsersAddressesResponse usernameOkResponse, ConnectionId id) throws Exception{
		allUsersAddressesResponseHandler.handle(usernameOkResponse, id);
	}
	
	public void handle( IIntroductoryTalk introductoryTalk, ConnectionId id) throws Exception{
		introductoryTalkHandler.handle(introductoryTalk, id);
	}
	
	public void handle( ILetterTalk talk, ConnectionId id) throws Exception{
		letterTalkHandler.handle(talk, id);
	}
	// TODO username bad response
}
