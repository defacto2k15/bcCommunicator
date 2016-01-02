package bc.internetMessageProxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class InternetMessageProxy implements IInternetMessageProxy {
	private BlockingQueue<RecievedMessage> recievedMessages = new ArrayBlockingQueue<RecievedMessage>(100);
	static int connectionsCount = 0;
	private List<Connection> connections = new ArrayList<Connection>();
	private Consumer<ConnectionId> onSocketCloseFunction;

	public InternetMessageProxy(Consumer<ConnectionId> onSocketCloseFunction) {
		this.onSocketCloseFunction = onSocketCloseFunction;
		new Thread(() -> {
			try {
				while (true) {
					List<Connection> upConnectionsList = new ArrayList<Connection>();
					List<Connection> connectionsCopy = new ArrayList<Connection>(connections);
					connectionsCopy.stream().filter(c -> {
						return c.IsSocketUp();
					});
					for (Connection oneConnection : connectionsCopy) {
						if (oneConnection.IsSocketUp() == false) {

							onSocketCloseFunction.accept(oneConnection.getId());
						} else {
							upConnectionsList.add(oneConnection);
						}
					}
					connections = upConnectionsList;
					Thread.sleep(100);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	public InternetMessageProxy() {
		this((new Consumer<ConnectionId>() {
			@Override
			public void accept(ConnectionId UNUSED) {
				// do nothing
			}
		}));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bc.internetMessageProxy.IInternetMessageProxy#sendMessage(bc.
	 * internetMessageProxy.ConnectionId, java.lang.String)
	 */
	@Override
	public boolean sendMessage(ConnectionId id, String message) {
		System.out.println(message + "message");
		Optional<Connection> connectionToFind = connections.stream().filter(c -> {
			return c.id == id;
		}).findFirst();

		if (connectionToFind.isPresent() == false) {
			throw new IllegalArgumentException("There is no connection with id: " + id);
		}
		return connectionToFind.get().sendMessage(message);
	}

	@Override
	public RecievedMessage getMessageBlocking() throws Exception {
		RecievedMessage message = null;
		message = recievedMessages.poll(1000, TimeUnit.SECONDS); // TODO this poll time is without sense, why 100
		if (message == null) {
			throw new Exception("Message polling timeouted");
		}
		return message;
	}

	@Override
	public RecievedMessage getMessageBlockingWithTimeout() throws Exception {
		RecievedMessage message = null;
		message = recievedMessages.poll(10, TimeUnit.SECONDS);

		if (message == null) {
			throw new Exception("Message polling timeouted");
		}
		return message;
	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see bc.internetMessageProxy.IInternetMessageProxy#closeConnections()
	 */
	@Override
	public void closeConnections() {
		for (Connection connection : connections) {
			connection.close();
		}
		connections.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bc.internetMessageProxy.IInternetMessageProxy#startListening(int)
	 */
	@Override
	public void startListening(int portNumber) {
		final ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(portNumber);
			new Thread(() -> {
				while (true) {
					try {
						Socket clientSocket = serverSocket.accept();
						ConnectionId id = new ConnectionId(connectionsCount++);
						Connection newConnection = new Connection(clientSocket, recievedMessages, id);
						connections.add(newConnection);
						new Thread(newConnection).start();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * bc.internetMessageProxy.IInternetMessageProxy#startConnection(java.net.
	 * URL)
	 */
	@Override
	public Optional<ConnectionId> startConnection(URL url) throws UnknownHostException, IOException {
		ConnectionId newId = new ConnectionId(connectionsCount++);
		Socket socket;
		try {
			socket = new Socket(url.getHost(), url.getPort());
		} catch (Exception e) {
			System.err.println("E891 while connecting to "+url);
			return Optional.empty();
		}
		Connection newConnection = new Connection(socket, recievedMessages, newId);
		connections.add(newConnection);
		try {
			new Thread(newConnection).start();
		} catch (Exception e) {
			System.err.println("E399");
			e.printStackTrace();
		}
		return Optional.of(newId);
	}

}
