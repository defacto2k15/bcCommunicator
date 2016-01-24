package bc.bcCommunicator.Controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

import bc.bcCommunicator.Model.ICommunicatorModel;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterSendingType;
import bc.bcCommunicator.Views.ILetterView;
import bc.bcCommunicator.Views.ILetterViewFactory;
import bc.bcCommunicator.Views.IServerConnectionStatusView;
import bc.bcCommunicator.Views.ITalkWindow;
import bc.bcCommunicator.Views.IUsernameInputView;
import bc.bcCommunicator.Views.IUsersTableView;
import bc.bcCommunicator.Views.LetterState;
import bc.bcCommunicator.Views.ServerConnectionStatus;
import bc.bcCommunicator.Views.TalkState;
import bc.bcCommunicator.Views.UserConnectionState;
import bc.bcCommunicator.Views.UsernameInputStatus;

public class CommunicatorController implements ICommunicatorController {
	private IServerConnectionStatusView connectionStatusView;
	private ICommunicatorModel model;
	private IUsernameInputView usernameInputView;
	private IUsersTableView usersTableView;
	private ITalkWindowsContainer talkWindowsContainer;
	private ITalkWindowsFactory windowsFactory;
	private ILetterViewFactory letterViewFactory;

	public CommunicatorController(IServerConnectionStatusView connectionStatusView, 
			ICommunicatorModel model,  
			IUsernameInputView usernameInputView, IUsersTableView usersTableView, ITalkWindowsContainer talkWindowsContainer,
			ITalkWindowsFactory windowsFactory, ILetterViewFactory letterViewFactory){
		this.connectionStatusView = connectionStatusView;
		this.model = model;
		this.usernameInputView = usernameInputView;
		this.usersTableView = usersTableView;
		this.talkWindowsContainer = talkWindowsContainer;
		this.windowsFactory = windowsFactory;
		this.letterViewFactory = letterViewFactory;
	}
	
	@Override
	public void setViewHandlers(){
		this.connectionStatusView.setServerConnectionAcceptanceButtonWasClickedHandler(
				()->{this.serverConnectionAcceptanceButtonWasClicked();});	
		this.usernameInputView.setUsernameSubmitButtonWasClickedHandler(
				()->{try {
					this.usernameInputSubmitButtonWasClicked();
				} catch (Exception e) {
					System.err.println("E540");
					e.printStackTrace();
				}});
	}

	@Override
	public void serverConnectionAcceptanceButtonWasClicked() {
		String serverAddressText = connectionStatusView.getServerAddress();
		URL serverAddress;
		try {
			serverAddress = new URL(serverAddressText);
		} catch (MalformedURLException e) {
			connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.ErrorMalformedUrl);
			return;
		}
		model.connectToServer(serverAddress);
	}

	@Override
	public void serverConnectionWasSuccesfull() {
		connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.Connected);
		connectionStatusView.disableViev();
		usernameInputView.disableView();
	}
	
	@Override
	public void serverConnectionFailed() {
		connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.ConnectionFailed);
		usernameInputView.setUsernameInputStatus(UsernameInputStatus.UsernameEmpty);
		connectionStatusView.enableView();
	}

	@Override
	public void usernameInputSubmitButtonWasClicked() throws Exception {
		String usernameText = usernameInputView.getUsernameText();
		if(usernameText.equals("")){
			usernameInputView.setUsernameInputStatus(UsernameInputStatus.UsernameEmpty);
		} else {
			model.usernameSubmitted(new Username(usernameText));
			usernameInputView.disableView();
			connectionStatusView.enableView();
		}
	}

	@Override
	public void setBulkUsers(List<Username> usernames) {
		usersTableView.clearTable();
		for( Username oneName : usernames){
			usersTableView.addLineToTable(oneName, UserConnectionState.NotConnected, TalkState.NoNewMessages);
		}
	}

	@Override
	public void userWasConnected(Username username) {
		usersTableView.changeStateOfUser(username, UserConnectionState.Connected);
		
	}

	@Override
	public void userConnectionLost(Username username) {
		usersTableView.changeStateOfUser(username, UserConnectionState.ConnectionLost);
		if( talkWindowsContainer.isWindowOpenForUser(username)){
			talkWindowsContainer.getUserWindow(username).setConnectionState(UserConnectionState.ConnectionLost);
		}
	}

	@Override
	public void newUserConnected(Username username) {	
		usersTableView.addLineToTable(username, UserConnectionState.Connected, TalkState.NoNewMessages);
		if( talkWindowsContainer.isWindowOpenForUser(username) ){
			talkWindowsContainer.getUserWindow(username).setConnectionState(UserConnectionState.Connected);
		}
	}

	@Override
	public void rowInUserTableWasClicked(Username username) throws ParseException {
		if (talkWindowsContainer.isWindowOpenForUser(username) ){
			ITalkWindow talkWindow = talkWindowsContainer.getUserWindow(username);
			talkWindow.requetsToBeActiveFrame();
			usersTableView.changeStateOfUser(username, TalkState.NoNewMessages);
		} else {
			model.getTalkStateData(username);
		}
	}

	@Override
	public void talkStateChanged(TalkStateData stateData) throws ParseException {
		if (talkWindowsContainer.isWindowOpenForUser(stateData.username) == false){
			ITalkWindow window = windowsFactory.createTalkWindow(stateData.username, this);
			window.setConnectionState(stateData.state);
			window.setUsername(stateData.username);
			window.setLetterState(LetterState.No_Letter);
			talkWindowsContainer.addWindowForUser(stateData.username, window);
			for( Letter letter : stateData.letters ){
				addLetterToView(letter.getOtherUserInTalk(), letter);
			}
		}		
	}

	@Override
	public void recievedNewLetter(Letter letter) throws ParseException {
		if( talkWindowsContainer.isWindowOpenForUser(letter.getOtherUserInTalk()) == false){
			usersTableView.changeStateOfUser(letter.getOtherUserInTalk(), TalkState.NewMessage);
		} else {
			addLetterToView(letter.getOtherUserInTalk(), letter);
		}
	}

	@Override
	public void letterWasWritten(Username username, String text) throws Exception {
		model.letterWasWritten(text, username);
		talkWindowsContainer.getUserWindow(username).setLetterState(LetterState.Letter_Sending);
	}
	
	private void addLetterToView( Username userTalkingTo,  Letter letter ) throws ParseException{
		ILetterView letterView = letterViewFactory.getLetterView(letter.sender.getName(), 
				letter.text.getTextValue(), letter.date.getDateAsString(), letter.type == LetterSendingType.Recieved);
		talkWindowsContainer.getUserWindow(userTalkingTo).addLetterView(letterView);			
	}

	@Override
	public void letterWasSent(Letter letter) throws ParseException {
		if( talkWindowsContainer.isWindowOpenForUser(letter.getOtherUserInTalk())){
			ITalkWindow talkWindow = talkWindowsContainer.getUserWindow(letter.getOtherUserInTalk());
			talkWindow.setLetterState(LetterState.Letter_Sent);
			talkWindow.emptyInputField();
			addLetterToView(letter.getOtherUserInTalk(), letter);
		}
	}

	@Override
	public void letterSendingFailed(Username username) {
		if( talkWindowsContainer.isWindowOpenForUser(username) ){
			talkWindowsContainer.getUserWindow(username).setLetterState(LetterState.Letter_Failed);
		}
	}
	
	@Override
	public void usernameWasOk(){
		usernameInputView.setUsernameInputStatus(UsernameInputStatus.UsernameOk);
	}
	
	@Override
	public void usernameWasBad(){
		usernameInputView.setUsernameInputStatus(UsernameInputStatus.UsernameBad);
		usernameInputView.enableView();
		connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.NotConnected);
		connectionStatusView.enableView();
	}
}
