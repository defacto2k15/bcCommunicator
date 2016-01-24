package bc.bcCommunicator.Model.Messages.Letter;

import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterId.
 */
public class LetterId {
	
	/** The number. */
	private int number;
	
	/** The talking user. */
	private Username talkingUser;

	/**
	 * Instantiates a new letter id.
	 *
	 * @param number the number
	 * @param talkingUser the talking user
	 */
	public LetterId(int number, Username talkingUser){
		this.number = number;
		this.talkingUser = talkingUser;	
	}
	
	/**
	 * Gets the string representation.
	 *
	 * @return the string representation
	 */
	public String getStringRepresentation(){
		return talkingUser.getName()+":"+number;
	}
}
