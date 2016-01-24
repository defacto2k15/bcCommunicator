package bc.bcCommunicator.Client.Views;

// TODO: Auto-generated Javadoc
/**
 * The Enum ServerConnectionStatus.
 */
public enum ServerConnectionStatus {
	
	/** The Not connected. */
	NotConnected("Not connected to server"),
	
	/** The Connected. */
	Connected("Connected to server"), 
	
	/** The Connection failed. */
	ConnectionFailed("ConnectionFailed"),
	
	/** The Error malformed url. */
	ErrorMalformedUrl("Url was malformed");
	
	/** The text. */
	private final String text;
	
	/**
	 * Instantiates a new server connection status.
	 *
	 * @param text the text
	 */
	private ServerConnectionStatus(String text){
		this.text = text;
	}
	
	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText(){
		return text;
	}
}
