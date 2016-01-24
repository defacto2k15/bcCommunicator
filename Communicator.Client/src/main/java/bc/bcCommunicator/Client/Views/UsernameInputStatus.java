package bc.bcCommunicator.Client.Views;

public enum UsernameInputStatus{
	UsernameNotInserted("Insert username"),
	UsernameInserted("Username ok"),
	UsernameEmpty("Username can't be empty"), 
	UsernameOk("Username Ok"),
	UsernameBad("Username is not ok, choose another");
	
	private String textToDisplay;
	
	private UsernameInputStatus(String textToDisplay ){
		this.textToDisplay = textToDisplay;
	}
	
	public String getText(){
		return textToDisplay;
	}
}