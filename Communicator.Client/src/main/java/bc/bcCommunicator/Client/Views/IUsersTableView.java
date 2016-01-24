package bc.bcCommunicator.Client.Views;

import bc.bcCommunicator.Model.BasicTypes.Username;

public interface IUsersTableView {
	void addLineToTable( Username username, UserConnectionState notconnected,  TalkState talkState);
	void clearTable();
	void changeStateOfUser(Username username, UserConnectionState connected);
	void changeStateOfUser(Username username, TalkState newmessage);
}
