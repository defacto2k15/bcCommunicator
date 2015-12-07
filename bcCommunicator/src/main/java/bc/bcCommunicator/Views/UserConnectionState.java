package bc.bcCommunicator.Views;

public enum UserConnectionState {
	NotConnected, 
	CantConnect;
	
	public String getStateDescription(){
		return this.name();
	}
}
