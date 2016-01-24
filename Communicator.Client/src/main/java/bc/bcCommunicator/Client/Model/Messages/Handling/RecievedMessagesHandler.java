package bc.bcCommunicator.Client.Model.Messages.Handling;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.bcCommunicator.Model.Messages.Response.IUsernameBadResponse;
import bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse;
import bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk;
import bc.bcCommunicator.Model.Messages.Talk.ILetterTalk;
import bc.internetMessageProxy.ConnectionId;

// TODO: Auto-generated Javadoc
/**
 * The Class RecievedMessagesHandler.
 */
public class RecievedMessagesHandler extends AbstractMessageHandler implements IRecievedMessagesHandler {
	
	/** The username ok response handler. */
	UsernameOkResponseHandler usernameOkResponseHandler;
	
	/** The all users addresses response handler. */
	AllUsersAddressesResponseHandler allUsersAddressesResponseHandler;
	
	/** The introductory talk handler. */
	private IntroductoryTalkHandler introductoryTalkHandler;
	
	/** The letter talk handler. */
	private LetterTalkMessageHandler letterTalkHandler;
	
	/** The username bad handler. */
	private UsernameBadMessageHandler usernameBadHandler;
	
	/**
	 * Instantiates a new recieved messages handler.
	 *
	 * @param usernameOkResponseHandler the username ok response handler
	 * @param allUsersAddressesResponseHandler the all users addresses response handler
	 * @param introductoryTalkHandler the introductory talk handler
	 * @param letterTalkHandler the letter talk handler
	 * @param usernameBadHandler the username bad handler
	 */
	public RecievedMessagesHandler(UsernameOkResponseHandler usernameOkResponseHandler,
			AllUsersAddressesResponseHandler allUsersAddressesResponseHandler, 
			IntroductoryTalkHandler introductoryTalkHandler,
			LetterTalkMessageHandler letterTalkHandler, UsernameBadMessageHandler usernameBadHandler){
		this.usernameOkResponseHandler = usernameOkResponseHandler;
		this.allUsersAddressesResponseHandler = allUsersAddressesResponseHandler;
		this.introductoryTalkHandler = introductoryTalkHandler;
		this.letterTalkHandler = letterTalkHandler;
		this.usernameBadHandler = usernameBadHandler;
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler#handle(bc.bcCommunicator.Model.Messages.IMessage, bc.internetMessageProxy.ConnectionId)
	 */
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
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse, bc.internetMessageProxy.ConnectionId)
	 */
	public void handle( IUsernameOkResponse usernameOkResponse, ConnectionId id) throws Exception{
		usernameOkResponseHandler.handle(usernameOkResponse, id);
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse, bc.internetMessageProxy.ConnectionId)
	 */
	public void handle( IAllUsersAddressesResponse usernameOkResponse, ConnectionId id) throws Exception{
		allUsersAddressesResponseHandler.handle(usernameOkResponse, id);
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Talk.IIntroductoryTalk, bc.internetMessageProxy.ConnectionId)
	 */
	public void handle( IIntroductoryTalk introductoryTalk, ConnectionId id) throws Exception{
		introductoryTalkHandler.handle(introductoryTalk, id);
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Talk.ILetterTalk, bc.internetMessageProxy.ConnectionId)
	 */
	public void handle( ILetterTalk talk, ConnectionId id) throws Exception{
		letterTalkHandler.handle(talk, id);
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Messages.Handling.AbstractMessageHandler#handle(bc.bcCommunicator.Model.Messages.Response.IUsernameBadResponse, bc.internetMessageProxy.ConnectionId)
	 */
	public void handle( IUsernameBadResponse response, ConnectionId id) throws Exception{
		usernameBadHandler.handle(response, id);
	}
}
