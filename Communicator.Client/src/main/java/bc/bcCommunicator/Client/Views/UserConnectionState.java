package bc.bcCommunicator.Client.Views;

// TODO: Auto-generated Javadoc
/**
 * The Enum UserConnectionState.
 */
public enum UserConnectionState {
	
	/** The Not connected. */
	NotConnected("Connection not established"), 
	
	/** The Cant connect. */
	CantConnect("Can't connect"),
	
	/** The Connected. */
	Connected("Connected"),
	
	/** The Connection lost. */
	ConnectionLost("Connection lost");
	
	/** The description. */
	private String description;
	
	
	/**
	 * Instantiates a new user connection state.
	 *
	 * @param description the description
	 */
	private UserConnectionState(String description) {
		this.description = description;
	}


	/**
	 * Gets the state description.
	 *
	 * @return the state description
	 */
	public String getStateDescription(){
		return description;
	}
}
