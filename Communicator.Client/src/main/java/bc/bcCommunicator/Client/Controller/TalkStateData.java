package bc.bcCommunicator.Client.Controller;

import java.util.List;

import bc.bcCommunicator.Client.Views.UserConnectionState;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

// TODO: Auto-generated Javadoc
/**
 * The Class TalkStateData.
 */
public class TalkStateData {
	
	/** The username. */
	public Username username;
	
	/** The letters. */
	public List<Letter> letters;
	
	/** The state. */
	public UserConnectionState state;
	
	/**
	 * Instantiates a new talk state data.
	 *
	 * @param username the username
	 * @param letters the letters
	 * @param state the state
	 */
	public TalkStateData(Username username, List<Letter> letters, UserConnectionState state) {
		this.username = username;
		this.letters = letters;
		this.state = state;
	}
	
	
}
