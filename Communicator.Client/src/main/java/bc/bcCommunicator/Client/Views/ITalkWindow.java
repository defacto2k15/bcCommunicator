package bc.bcCommunicator.Client.Views;

import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Interface ITalkWindow.
 */
public interface ITalkWindow {

	/**
	 * Sets the connection state.
	 *
	 * @param connected the new connection state
	 */
	void setConnectionState(UserConnectionState connected);

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	void setUsername(Username username);

	/**
	 * Sets the letter state.
	 *
	 * @param noLetter the new letter state
	 */
	void setLetterState(LetterState noLetter);

	/**
	 * Adds the letter view.
	 *
	 * @param view the view
	 */
	void addLetterView(ILetterView view);

	/**
	 * Empty input field.
	 */
	void emptyInputField();
	
	/**
	 * Sets the closing listener.
	 *
	 * @param closingListener the new closing listener
	 */
	void setClosingListener( ITalkWindowViewClosingListener closingListener );

	/**
	 * Requets to be active frame.
	 */
	void requetsToBeActiveFrame();

}
