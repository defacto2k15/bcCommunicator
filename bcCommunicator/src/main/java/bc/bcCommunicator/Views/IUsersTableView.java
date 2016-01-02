package bc.bcCommunicator.Views;

import bc.bcCommunicator.Model.BasicTypes.Username;

public interface IUsersTableView {
	void addLineToTable( Username username, UserConnectionState notconnected);
	void clearTable();
	void changeStateOfUser(Username username, UserConnectionState connected);
}
