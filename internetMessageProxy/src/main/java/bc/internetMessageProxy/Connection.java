package bc.internetMessageProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

// TODO: Auto-generated Javadoc
/**
 * The Class Connection.
 */
class Connection implements Runnable {
	
	/** The out. */
	private PrintWriter out;
	
	/** The in. */
	private BufferedReader in;
	
	/** The id. */
	ConnectionId id;
	
	/** The consumer of messages. */
	private BlockingQueue<RecievedMessage> consumerOfMessages;
	
	/** The socket. */
	private Socket socket; 
	
	/**
	 * Instantiates a new connection.
	 *
	 * @param openedSocket the opened socket
	 * @param consumerOfMessages the consumer of messages
	 * @param id the id
	 */
	Connection(Socket openedSocket, BlockingQueue<RecievedMessage> consumerOfMessages, ConnectionId id){
		this.consumerOfMessages = consumerOfMessages;
		this.id = id;
        try {
		    out = new PrintWriter(openedSocket.getOutputStream(), true);
            in = new BufferedReader(
                   new InputStreamReader(openedSocket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
        socket = openedSocket;
	}	
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public ConnectionId getId() {
		return id;
	}

	/**
	 * Checks if is socket up.
	 *
	 * @return true, if successful
	 */
	public boolean IsSocketUp() {
		try {
			socket.sendUrgentData(255);
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	/**
	 * Close.
	 */
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket = null;
	}

	/**
	 * Send message.
	 *
	 * @param message the message
	 * @return true, if successful
	 */
	boolean sendMessage( String message ){		
		if(socket.isClosed()){
			return false;
		}
		try{
			out.print(message);
			out.println(ConstantMessageParts.MESSAGE_ENDING_ELEMENT+'\n');
		} catch(Exception e){
			return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			StringBuilder builder = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
            	if( inputLine.equals("")){
            		builder.setLength(0);
            	}else{
            		if( inputLine.endsWith(ConstantMessageParts.MESSAGE_ENDING_ELEMENT)){                	
	                	inputLine = inputLine.substring(0, inputLine.length() -
	                			ConstantMessageParts.MESSAGE_ENDING_ELEMENT.length());
	                    	builder.append(inputLine);
	                    	
	                    	consumerOfMessages.put(new RecievedMessage(builder.toString(), this.id));  
	                    	builder.setLength(0);
	                } else {
	                	builder.append(inputLine).append('\n');
	                }

                }
                
            }
            assert(false);
        } catch (IOException e) {
        	e.printStackTrace();;
        } catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}