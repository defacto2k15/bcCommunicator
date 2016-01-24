package bc.bcCommunicator.Client.Views;

public enum TalkState {
	NoNewMessages("No new"),
	NewMessage("New!!");
	
	private String text;
	
	private TalkState(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
}
