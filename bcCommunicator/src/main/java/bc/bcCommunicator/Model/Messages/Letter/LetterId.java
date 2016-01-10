package bc.bcCommunicator.Model.Messages.Letter;

import bc.bcCommunicator.Model.BasicTypes.Username;

public class LetterId {
	private int number;
	private Username talkingUser;

	public LetterId(int number, Username talkingUser){
		this.number = number;
		this.talkingUser = talkingUser;	
	}
	
	public String getStringRepresentation(){
		return talkingUser.getName()+":"+number;
	}
}
