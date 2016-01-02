package bc.bcCommunicator.Views;

public enum UserConnectionState {
	NotConnected, 
	CantConnect,
	Connected;
	
	public String getStateDescription(){
		return this.name();
	}
}
