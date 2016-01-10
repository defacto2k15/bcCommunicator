package bc.bcCommunicator.Views;

import bc.bcCommunicator.Model.BasicTypes.Username;

public interface ITalkWindow {

	void setConnectionState(UserConnectionState connected);

	void setUsername(Username username);

	void setLetterState(LetterState noLetter);

	void addLetterView(ILetterView view);

}
