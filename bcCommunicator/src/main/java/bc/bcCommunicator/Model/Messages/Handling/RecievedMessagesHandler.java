package bc.bcCommunicator.Model.Messages.Handling;

import org.w3c.dom.views.AbstractView;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Response.IAllUsersAddressesResponse;
import bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameOkResponse;
import bc.internetMessageProxy.ConnectionId;

public class RecievedMessagesHandler extends AbstractMessageHandler implements IRecievedMessagesHandler {
	UsernameOkResponseHandler usernameOkResponseHandler;
	AllUsersAddressesResponseHandler allUsersAddressesResponseHandler;
	
	public RecievedMessagesHandler(UsernameOkResponseHandler usernameOkResponseHandler,
			AllUsersAddressesResponseHandler allUsersAddressesResponseHandler){
		this.usernameOkResponseHandler = usernameOkResponseHandler;
		this.allUsersAddressesResponseHandler = allUsersAddressesResponseHandler;
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

}