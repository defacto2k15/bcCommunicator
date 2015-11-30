package bc.internetMessageProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

class Connection implements Runnable {
	private PrintWriter out;
	private BufferedReader in;
	ConnectionId id;
	private BlockingQueue<RecievedMessage> consumerOfMessages;
	private Socket socket; 
	
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
	
	public ConnectionId getId() {
		return id;
	}

	public boolean IsSocketUp() {
		try {
			socket.sendUrgentData(255);
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket = null;
	}

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
	
	@Override
	public void run() {
		try {
			StringBuilder builder = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
            	System.out.println("M542 recieved line " + inputLine);
            	if( inputLine.equals("")){
            		builder.setLength(0);
            		System.out.println("F101 Empty message");
            	}else{
            		if( inputLine.endsWith(ConstantMessageParts.MESSAGE_ENDING_ELEMENT)){                	
	                	inputLine = inputLine.substring(0, inputLine.length() -
	                			ConstantMessageParts.MESSAGE_ENDING_ELEMENT.length());
	                    	builder.append(inputLine);
	                    	System.out.println("M134 Putting messge to consumer "+builder.toString());
	                    	System.out.println("M135 END MESSGE");
	                    	
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