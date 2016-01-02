package bc.bcCommunicator.Views;

import Controller.ICommunicatorController;

public interface IUsernameInputView {

	String getUsernameText();

	void submitUsernameButtonWasClickedHandler(Runnable procedure);
	
	void setUsernameInputStatus( UsernameInputStatus status);

	void setUsernameSubmitButtonWasClickedHandler(Runnable procedure);

}