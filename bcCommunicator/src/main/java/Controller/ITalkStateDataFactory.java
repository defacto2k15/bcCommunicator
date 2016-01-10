package Controller;

import java.util.List;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Views.UserConnectionState;

public interface ITalkStateDataFactory {
	TalkStateData generate( Username username, List<Letter> letters, UserConnectionState state );
}
