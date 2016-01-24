package bc.bcCommunicator.Client.Views;

// TODO: Auto-generated Javadoc
/**
 * The Interface IServerConnectionStatusView.
 */
public interface IServerConnectionStatusView {

	/**
	 * Gets the server address.
	 *
	 * @return the server address
	 */
	public String getServerAddress();

	/**
	 * Sets the server connection status.
	 *
	 * @param status the new server connection status
	 */
	public void setServerConnectionStatus(ServerConnectionStatus status);

	/**
	 * Sets the server connection acceptance button was clicked handler.
	 *
	 * @param methodToRun the new server connection acceptance button was clicked handler
	 */
	public void setServerConnectionAcceptanceButtonWasClickedHandler(Runnable methodToRun);

	/**
	 * Disable viev.
	 */
	public void disableViev();

	/**
	 * Enable view.
	 */
	public void enableView();

}