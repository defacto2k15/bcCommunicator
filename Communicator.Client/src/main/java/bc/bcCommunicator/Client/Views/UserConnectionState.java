package bc.bcCommunicator.Client.Views;

public enum UserConnectionState {
	NotConnected("Connection not established"), 
	CantConnect("Can't connect"),
	Connected("Connected"),
	ConnectionLost("Connection lost");
	
	private String description;
	
	
	private UserConnectionState(String description) {
		this.description = description;
	}


	public String getStateDescription(){
		return description;
	}
}
