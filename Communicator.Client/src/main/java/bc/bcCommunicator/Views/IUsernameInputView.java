package bc.bcCommunicator.Views;

import bc.bcCommunicator.Controller.ICommunicatorController;

public interface IUsernameInputView {

	String getUsernameText();

	void submitUsernameButtonWasClickedHandler(Runnable procedure);
	
	void setUsernameInputStatus( UsernameInputStatus status);

	void setUsernameSubmitButtonWasClickedHandler(Runnable procedure);

}