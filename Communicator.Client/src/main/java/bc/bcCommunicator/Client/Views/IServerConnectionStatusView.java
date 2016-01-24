package bc.bcCommunicator.Client.Views;

public interface IServerConnectionStatusView {

	public String getServerAddress();

	public void setServerConnectionStatus(ServerConnectionStatus status);

	public void setServerConnectionAcceptanceButtonWasClickedHandler(Runnable methodToRun);

	public void disableViev();

	public void enableView();

}