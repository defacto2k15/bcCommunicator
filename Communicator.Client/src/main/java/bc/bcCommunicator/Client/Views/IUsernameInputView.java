package bc.bcCommunicator.Client.Views;

public interface IUsernameInputView {

	String getUsernameText();

	void submitUsernameButtonWasClickedHandler(Runnable procedure);
	
	void setUsernameInputStatus( UsernameInputStatus status);

	void setUsernameSubmitButtonWasClickedHandler(Runnable procedure);

	void disableView();

	void enableView();

}