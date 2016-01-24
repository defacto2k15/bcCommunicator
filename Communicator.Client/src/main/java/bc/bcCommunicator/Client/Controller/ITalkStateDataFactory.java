package bc.bcCommunicator.Client.Controller;

import java.util.List;

import bc.bcCommunicator.Client.Views.UserConnectionState;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating ITalkStateData objects.
 */
public interface ITalkStateDataFactory {
	
	/**
	 * Generate.
	 *
	 * @param username the username
	 * @param letters the letters
	 * @param state the state
	 * @return the talk state data
	 */
	TalkStateData generate( Username username, List<Letter> letters, UserConnectionState state );
}
