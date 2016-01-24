package bc.bcCommunicator.Model.Internet;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IRecievedMessageCreator;
import bc.internetMessageProxy.ConnectionId;
import bc.internetMessageProxy.IInternetMessageProxy;
import bc.internetMessageProxy.InternetMessageProxy;
import bc.internetMessageProxy.RecievedMessage;

/**
 * The Class InternetMessager, translates between model and InternedMessageProxy
 */
public class InternetMessager implements IInternetMessager {
	private final IInternetMessageProxy proxy;
	private IInternetMessagerListener listener;
	
	/**
	 * Instantiates a new internet messager.
	 *
	 * @param recievedMessageCreator the recieved message creator
	 * @param listener the listener
	 */
	public InternetMessager(IRecievedMessageCreator recievedMessageCreator, IInternetMessagerListener listener) {
		this.listener = listener;
		this.proxy=new InternetMessageProxy((ConnectionId id)->{this.connectionLost(id);});
		
		Thread listeningThread = new Thread( ()->{
				while(true){
					RecievedMessage message = null;
					try {
						message = proxy.getMessageBlocking();
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						IMessage recievedMessage = null;
						recievedMessage = recievedMessageCreator.createMessage( message.getMessage());
						listener.messageWasRecieved(recievedMessage, message.getConnectionId());
					} catch (Exception e) {
						System.err.println("E102");
						e.printStackTrace();
					}
					
				}
		});
		listeningThread.setDaemon(true);
		listeningThread.start();
	}
	
	/**
	 * Instantiates a new internet messager.
	 *
	 * @param port the port that we will start listening to
	 * @param recievedMessageCreator the recieved message creator
	 * @param listener the listener
	 */
	public InternetMessager(int port, IRecievedMessageCreator recievedMessageCreator, IInternetMessagerListener listener) {
		this(recievedMessageCreator, listener);
		proxy.startListening(port);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessager#connectToServer(java.net.URL)
	 */
	@Override
	public void connectToServer(URL serverAddress) throws Exception {
		System.out.println("X644 "+serverAddress);
		Optional<ConnectionId> result = null;
		try {
			result = proxy.startConnection(serverAddress);
		} catch (IOException e) {
			listener.serverConnectionFailed();
			return;
		}
		
		if( result.isPresent() ){
			listener.serverConnectionWasSuccesfull(result.get());
		} else {
			listener.serverConnectionFailed();
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessager#connectionLost(bc.internetMessageProxy.ConnectionId)
	 */
	@Override
	public void connectionLost(ConnectionId id) {
		System.out.println("M102: Lost connection t messagger "+id);
		listener.connectionLost(id);
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessager#sendMessage(bc.internetMessageProxy.ConnectionId, java.lang.String)
	 */
	@Override
	public void sendMessage(ConnectionId id, String messageText){
		boolean sendWasSuccesfull = proxy.sendMessage(id, messageText);
		if( sendWasSuccesfull){
			try {
				listener.messageSentSuccesfully(id);
			} catch (Exception e) {
				System.err.println("E402");
				e.printStackTrace();
			}
		}else{
			listener.messageSendingFailed(id);
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessager#connectToUser(java.net.URL)
	 */
	@Override
	public void connectToUser(URL userAddress) {
		Optional<ConnectionId> result = null;
		try {
			result = proxy.startConnection(userAddress);
		} catch (IOException e) {
			listener.userConnectionFailed(userAddress);
			return;
		}
		
		if( result.isPresent() ){
			try {
				listener.userConnectionWasSuccesfull(userAddress, result.get());
			} catch (Exception e) {
				System.err.println("E402");
				e.printStackTrace();
			}
		} else {
			listener.userConnectionFailed(userAddress);
		}		
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Model.Internet.IInternetMessager#listenOnPort(int)
	 */
	@Override
	public void listenOnPort(int clientPort) {
		proxy.startListening(clientPort);
	}

}
