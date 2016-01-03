package bc.bcCommunicator.Model.Internet;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import bc.bcCommunicator.Model.ICommunicatorModel;
import bc.bcCommunicator.Model.ICommunicatorModelCommandsProvider;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IRecievedMessageCreator;
import bc.internetMessageProxy.ConnectionId;
import bc.internetMessageProxy.IInternetMessageProxy;
import bc.internetMessageProxy.InternetMessageProxy;
import bc.internetMessageProxy.RecievedMessage;

public class InternetMessager implements IInternetMessager {
	private final BlockingQueue<IInternetMessagerCommand> commands = new ArrayBlockingQueue<IInternetMessagerCommand>(20); 
	private final IInternetMessageProxy proxy;
	private ICommunicatorModelCommandsProvider modelCommandsProvider;
	private ICommunicatorModel model;
	private IRecievedMessageCreator recievedMessageCreator;
	
	public InternetMessager(final IInternetMessagerCommandProvider messagerCommandsProvider, 
			final ICommunicatorModelCommandsProvider modelCommandsProvider,
			IRecievedMessageCreator recievedMessageCreator) {
		proxy=new InternetMessageProxy((ConnectionId id)->{
			this.addCommand(messagerCommandsProvider.getConnectionLostCommand(id));
		});
		this.modelCommandsProvider=modelCommandsProvider;
		this.recievedMessageCreator = recievedMessageCreator;
		
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
						model.addCommand(modelCommandsProvider.getMessageRecievedCommand( recievedMessage, message.getConnectionId()));
					} catch (Exception e) {
						// TODO TUTAJ LATAJĄ EXCEPTION
						e.printStackTrace();
					}
					
				}
		});
		listeningThread.start();
	}
	
	@Override
	public void setModel(ICommunicatorModel model){
		this.model = model;
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
			model.addCommand(modelCommandsProvider.getServerConnectionFailedCommand());
			return;
		}
		
		if( result.isPresent() ){
			model.addCommand(modelCommandsProvider.getServerConnectionWasSuccesfullCommand(result.get()));
		} else {
			model.addCommand(modelCommandsProvider.getServerConnectionFailedCommand());
		}
		//System.err.println("X374 connect to server at model ended");
	}

	@Override
	public void connectionLost(ConnectionId id) {
		System.out.println("Lost connection t messagger "+id);
		model.addCommand(modelCommandsProvider.getConnectionLostCommand(id));
	}
	
	@Override
	public void sendMessage(ConnectionId id, String messageText){
		proxy.sendMessage(id, messageText);
	}

	@Override
	public void connectToUser(URL userAddress) {
		Optional<ConnectionId> result = null;
		try {
			result = proxy.startConnection(userAddress);
		} catch (IOException e) {
			model.addCommand(modelCommandsProvider.getUserConnectionFailed(userAddress));
			return;
		}
		
		if( result.isPresent() ){
			model.addCommand(modelCommandsProvider.getUserConectionWasSuccesfullCommand( userAddress, result.get()));
		} else {
			model.addCommand(modelCommandsProvider.getUserConnectionFailed(userAddress));
		}		
	}

	@Override
	public void listenOnPort(int clientPort) {
		proxy.startListening(clientPort);
	}

}
