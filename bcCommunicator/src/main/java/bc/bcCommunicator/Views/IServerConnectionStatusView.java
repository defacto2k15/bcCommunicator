package bc.bcCommunicator.Views;

public interface IServerConnectionStatusView {

	public String getServerAddress();

	public void setServerConnectionStatus(ServerConnectionStatus status);

	public void setServerConnectionAcceptanceButtonWasClickedHandler(Runnable methodToRun);

}