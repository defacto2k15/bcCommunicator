package bc.internetMessageSender.FakeObjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import bc.internetMessageProxy.ConstantMessageParts;

public class FakeMessageReciever {
	boolean continueWaitingForMessages = true;
	private OneSocketListeningConnection connection;
	private BlockingQueue<String> recievedMessages = new ArrayBlockingQueue<String>(99);
	

	public void startListening(int portNumber) {
		connection = new OneSocketListeningConnection(portNumber, recievedMessages);
		new Thread( connection).start();
	}

	public void close() throws IOException {
		continueWaitingForMessages=false;
		System.out.println("EE closing");
		connection.socket.close();
		System.out.println("EE closed");
	}	
	
	class OneSocketListeningConnection implements Runnable{
		private int portNumber;
		private BlockingQueue<String> consumerOfMessages;
		Socket socket;
		
		OneSocketListeningConnection( int portNumber, BlockingQueue<String> consumerOfMessages) {
			this.portNumber = portNumber;
			this.consumerOfMessages = consumerOfMessages;
		}
		
		@Override
		public void run() {
			try (
	            ServerSocket serverSocket =
	                new ServerSocket(portNumber);
	            Socket clientSocket = serverSocket.accept();     
	            PrintWriter out =
	                new PrintWriter(clientSocket.getOutputStream(), true);                   
	            BufferedReader in = new BufferedReader(
	                new InputStreamReader(clientSocket.getInputStream()));
	        ) {
				socket = clientSocket;
				StringBuilder builder = new StringBuilder();
	            String inputLine;
	            while (((inputLine = in.readLine() ) != null) && continueWaitingForMessages) {
	                if( inputLine.endsWith(ConstantMessageParts.MESSAGE_ENDING_ELEMENT)){                	
	                	inputLine = inputLine.substring(0, inputLine.length() -
	                			ConstantMessageParts.MESSAGE_ENDING_ELEMENT.length());
	                	builder.append(inputLine);
	                	consumerOfMessages.put(builder.toString());
	                	builder.setLength(0);
	                }
	                builder.append(inputLine).append('\n');
	            }
	            System.out.println("Should not happen some line");
	        } catch (IOException e) {
	            System.out.println("Fake reciever while listening exception caught");
	            System.out.println(e.getMessage());
	            
	        } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void assertRecieved(String expectedMessage) {
		String recievedMessage = null;
		try {
			recievedMessage = recievedMessages.poll(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			fail("Exception during polling for recieved message");
		}
		assertNotNull(recievedMessage);
		assertEquals(expectedMessage, recievedMessage);
	}

}

