package bc.internetMessageProxy;

public class RecievedMessage{
	private String message;
	private ConnectionId connectionId;
	
	RecievedMessage( String message, ConnectionId connectionId ){
		this.message = message;
		this.connectionId = connectionId;
	}
	
	public String getMessage(){
		return message;
	}
	
	public ConnectionId getConnectionId(){
		return connectionId;
	}
}