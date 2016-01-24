package bc.bcCommunicator.Client.Views;

public enum ServerConnectionStatus {
	NotConnected("Not connected to server"),
	Connected("Connected to server"), 
	ConnectionFailed("ConnectionFailed"),
	ErrorMalformedUrl("Url was malformed");
	
	private final String text;
	private ServerConnectionStatus(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
}
