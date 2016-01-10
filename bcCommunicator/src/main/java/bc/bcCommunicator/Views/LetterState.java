package bc.bcCommunicator.Views;

public enum LetterState {
	No_Letter(""),
	Letter_Sent("Letter sent"),
	Letter_Sending("Letter begin sent"),
	Letter_Failed("Failed sending letter");
	
	private String message;
	
	private LetterState( String message){
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
