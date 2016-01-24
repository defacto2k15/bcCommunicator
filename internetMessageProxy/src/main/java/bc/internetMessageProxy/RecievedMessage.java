package bc.internetMessageProxy;

// TODO: Auto-generated Javadoc
/**
 * The Class RecievedMessage.
 */
public class RecievedMessage{
	
	/** The message. */
	private String message;
	
	/** The connection id. */
	private ConnectionId connectionId;
	
	/**
	 * Instantiates a new recieved message.
	 *
	 * @param message the message
	 * @param connectionId the connection id
	 */
	RecievedMessage( String message, ConnectionId connectionId ){
		this.message = message;
		this.connectionId = connectionId;
	}
	
	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage(){
		return message;
	}
	
	/**
	 * Gets the connection id.
	 *
	 * @return the connection id
	 */
	public ConnectionId getConnectionId(){
		return connectionId;
	}
}