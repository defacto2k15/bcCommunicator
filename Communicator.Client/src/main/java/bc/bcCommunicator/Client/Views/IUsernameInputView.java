package bc.bcCommunicator.Client.Views;

// TODO: Auto-generated Javadoc
/**
 * The Interface IUsernameInputView.
 */
public interface IUsernameInputView {

	/**
	 * Gets the username text.
	 *
	 * @return the username text
	 */
	String getUsernameText();

	/**
	 * Submit username button was clicked handler.
	 *
	 * @param procedure the procedure
	 */
	void submitUsernameButtonWasClickedHandler(Runnable procedure);
	
	/**
	 * Sets the username input status.
	 *
	 * @param status the new username input status
	 */
	void setUsernameInputStatus( UsernameInputStatus status);

	/**
	 * Sets the username submit button was clicked handler.
	 *
	 * @param procedure the new username submit button was clicked handler
	 */
	void setUsernameSubmitButtonWasClickedHandler(Runnable procedure);

	/**
	 * Disable view.
	 */
	void disableView();

	/**
	 * Enable view.
	 */
	void enableView();

}