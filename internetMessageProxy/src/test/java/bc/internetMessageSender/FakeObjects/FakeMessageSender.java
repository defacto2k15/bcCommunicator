package bc.internetMessageSender.FakeObjects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import bc.internetMessageProxy.ConstantMessageParts;

public class FakeMessageSender {

	private URL url;
	private BlockingQueue<String> messagesToSend = new ArrayBlockingQueue<String>(99);
	private OneFakeSendingConnection connection;
	
	public FakeMessageSender(URL url) {
		this.url = url;
	}

	public void connect() {
		connection = new OneFakeSendingConnection();
		new Thread( connection ).start();
	}
	
	class OneFakeSendingConnection implements Runnable {
		private Socket socket;
		@Override
		public void run() {
			try (
	            Socket echoSocket = new Socket(url.getHost(), url.getPort());
	            PrintWriter out =
	                new PrintWriter(echoSocket.getOutputStream(), true);
	            BufferedReader in =
	                new BufferedReader(
	                    new InputStreamReader(echoSocket.getInputStream()));
	            BufferedReader stdIn =
	                new BufferedReader(
	                    new InputStreamReader(System.in))
	        ) {
				socket = echoSocket;
				String messageToSend = null;
				while( true ){
					messageToSend = messagesToSend.poll(33, TimeUnit.SECONDS);								
					assert(messageToSend != null);
					out.print(messageToSend);
					out.println(ConstantMessageParts.MESSAGE_ENDING_ELEMENT);
				}
	        } catch (UnknownHostException e) {
	            System.err.println("Don't know about host " + url.toString());
	            System.exit(1);
	        } catch (IOException e) {
	            System.err.println("Couldn't get I/O for the connection to " +
	                url.toString());
	            System.exit(1);
	        } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
	}

	public void sendMessage(String message) {
		messagesToSend.add(message);
	}

	public void close() throws IOException {
		connection.socket.close();
		
	}

}
