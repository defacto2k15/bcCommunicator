package bc.bcCommunicator.Client.Views;

import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Interface IUsersTableView.
 */
public interface IUsersTableView {
	
	/**
	 * Adds the line to table.
	 *
	 * @param username the username
	 * @param notconnected the notconnected
	 * @param talkState the talk state
	 */
	void addLineToTable( Username username, UserConnectionState notconnected,  TalkState talkState);
	
	/**
	 * Clear table.
	 */
	void clearTable();
	
	/**
	 * Change state of user.
	 *
	 * @param username the username
	 * @param connected the connected
	 */
	void changeStateOfUser(Username username, UserConnectionState connected);
	
	/**
	 * Change state of user.
	 *
	 * @param username the username
	 * @param newmessage the newmessage
	 */
	void changeStateOfUser(Username username, TalkState newmessage);
}
