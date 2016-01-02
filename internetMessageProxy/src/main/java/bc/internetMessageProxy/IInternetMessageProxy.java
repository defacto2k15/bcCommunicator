package bc.internetMessageProxy;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface IInternetMessageProxy {

	boolean sendMessage(ConnectionId id, String message);

	void closeConnections();

	void startListening(int portNumber);

	Optional<ConnectionId> startConnection(URL url) throws UnknownHostException, IOException;

	RecievedMessage getMessageBlocking() throws Exception;

	RecievedMessage getMessageBlockingWithTimeout() throws Exception;

}