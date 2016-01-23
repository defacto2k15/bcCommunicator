package bc.bcCommunicator.Model.Internet;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import bc.bcCommunicator.Model.ICommunicatorModel;
import bc.bcCommunicator.Model.ICommunicatorModelCommandsProvider;
import bc.bcCommunicator.Model.IConnectivityHandler;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IRecievedMessageCreator;
import bc.internetMessageProxy.ConnectionId;
import bc.internetMessageProxy.IInternetMessageProxy;
import bc.internetMessageProxy.InternetMessageProxy;
import bc.internetMessageProxy.RecievedMessage;

public class InternetMessager implements IInternetMessager {
	private final BlockingQueue<IInternetMessagerCommand> commands = new ArrayBlockingQueue<IInternetMessagerCommand>(20); 
	private final IInternetMessageProxy proxy;
	private IConnectivityHandler connectivityHandler;
	
	public InternetMessager(final IInternetMessagerCommandProvider messagerCommandsProvider, 
			IRecievedMessageCreator recievedMessageCreator, IConnectivityHandler connectivityHandler) {
		this.connectivityHandler = connectivityHandler;
		proxy=new InternetMessageProxy((ConnectionId id)->{
			this.addCommand(messagerCommandsProvider.getConnectionLostCommand(id));
		});
		
		Thread newThread = new Thread(()->{
								while(true){
									try {
										commands.take().execute(this);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
		newThread.setDaemon(true);
		newThread.start();
		
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
						connectivityHandler.messageWasRecieved(recievedMessage, message.getConnectionId());
					} catch (Exception e) {
						System.err.println("E102");
						e.printStackTrace();
					}
					
				}
		});
		listeningThread.start();
	}

	public void addCommand(IInternetMessagerCommand command) {
		commands.add(command);
	}

	@Override
	public void connectToServer(URL serverAddress) throws Exception {
		System.out.println("X644 "+serverAddress);
		Optional<ConnectionId> result = null;
		try {
			result = proxy.startConnection(serverAddress);
		} catch (IOException e) {
			connectivityHandler.serverConnectionFailed();
			return;
		}
		
		if( result.isPresent() ){
			connectivityHandler.serverConnectionWasSuccesfull(result.get());
		} else {
			connectivityHandler.serverConnectionFailed();
		}
		//System.err.println("X374 connect to server at model ended");
	}

	@Override
	public void connectionLost(ConnectionId id) {
		System.out.println("M102: Lost connection t messagger "+id);
		connectivityHandler.connectionLost(id);
	}
	
	@Override
	public void sendMessage(ConnectionId id, String messageText){
		boolean sendWasSuccesfull = proxy.sendMessage(id, messageText);
		if( sendWasSuccesfull){
			try {
				connectivityHandler.messageSentSuccesfully(id);
			} catch (Exception e) {
				System.err.println("E402");
				e.printStackTrace();
			}
		}else{
			connectivityHandler.messageSendingFailed(id);
		}
	}

	@Override
	public void connectToUser(URL userAddress) {
		Optional<ConnectionId> result = null;
		try {
			result = proxy.startConnection(userAddress);
		} catch (IOException e) {
			connectivityHandler.userConnectionFailed(userAddress);
			return;
		}
		
		if( result.isPresent() ){
			try {
				connectivityHandler.userConnectionWasSuccesfull(userAddress, result.get());
			} catch (Exception e) {
				System.err.println("E402");
				e.printStackTrace();
			}
		} else {
			connectivityHandler.userConnectionFailed(userAddress);
		}		
	}

	@Override
	public void listenOnPort(int clientPort) {
		proxy.startListening(clientPort);
	}

}
