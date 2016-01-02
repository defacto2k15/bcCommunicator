package bc.bcCommunicator.Views;

public enum UsernameInputStatus{
	UsernameNotInserted("Insert username"),
	UsernameInserted("Username ok"),
	UsernameEmpty("Username can't be empty"), 
	UsernameOk("Username Ok");
	
	private String textToDisplay;
	
	private UsernameInputStatus(String textToDisplay ){
		this.textToDisplay = textToDisplay;
	}
	
	public String getText(){
		return textToDisplay;
	}
}