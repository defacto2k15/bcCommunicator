package bc.bcCommunicator.Client.Controller;

import java.util.List;

import bc.bcCommunicator.Client.Views.UserConnectionState;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

public interface ITalkStateDataFactory {
	TalkStateData generate( Username username, List<Letter> letters, UserConnectionState state );
}
