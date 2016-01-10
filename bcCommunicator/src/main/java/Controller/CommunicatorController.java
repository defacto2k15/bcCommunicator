package Controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

import bc.bcCommunicator.Model.CommunicatorModel;
import bc.bcCommunicator.Model.ICommunicatorModel;
import bc.bcCommunicator.Model.ICommunicatorModelCommandsProvider;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
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
import bc.bcCommunicator.Views.UsersTableView;

public class CommunicatorController implements ICommunicatorController {
	private IServerConnectionStatusView connectionStatusView;
	private ICommunicatorModelCommandsProvider commandsProvider;
	private ICommunicatorModel model;
	private IUsernameInputView usernameInputView;
	private IUsersTableView usersTableView;
	private ITalkWindowsContainer talkWindowsContainer;
	private ITalkWindowsFactory windowsFactory;
	private ILetterViewFactory letterViewFactory;

	public CommunicatorController(IServerConnectionStatusView connectionStatusView, 
			ICommunicatorModel model, ICommunicatorModelCommandsProvider commandsProvider, 
			IUsernameInputView usernameInputView, IUsersTableView usersTableView, ITalkWindowsContainer talkWindowsContainer,
			ITalkWindowsFactory windowsFactory, ILetterViewFactory letterViewFactory){
		this.connectionStatusView = connectionStatusView;
		this.model = model;
		this.commandsProvider = commandsProvider;
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
				()->{this.usernameInputSubmitButtonWasClicked();});
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
		model.addCommand(commandsProvider.getConnectServerCommand(serverAddress));
	}

	@Override
	public void serverConnectionWasSuccesfull() {
		connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.Connected);
	}
	
	@Override
	public void serverConnectionFailed() {
		connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.ConnectionFailed);
		
	}

	@Override
	public void usernameInputSubmitButtonWasClicked() {
		String usernameText = usernameInputView.getUsernameText();
		if(usernameText.equals("")){
			usernameInputView.setUsernameInputStatus(UsernameInputStatus.UsernameEmpty);
		} else {
			model.addCommand(commandsProvider.getUsernameSubmittedCommand(new Username(usernameText)));
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
	}

	@Override
	public void newUserConnected(Username username) {	
		usersTableView.addLineToTable(username, UserConnectionState.Connected, TalkState.NoNewMessages);
	}

	@Override
	public void rowInUserTableWasClicked(Username username) {
		System.out.println("M347: CC row clicked");
		if (talkWindowsContainer.isWindowOpenForUser(username) ){
			// todo zrob cos to
		} else {
			System.out.println("M348: openin windo");
			model.addCommand( commandsProvider.getGetTalkStateDataCommand(username));
		}
	}

	@Override
	public void talkStateChanged(TalkStateData stateData) throws ParseException {
		System.out.println("M900");
		if (talkWindowsContainer.isWindowOpenForUser(stateData.username) == false){
			ITalkWindow window = windowsFactory.createTalkWindow(stateData.username, this);
			window.setConnectionState(stateData.state);
			window.setUsername(stateData.username);
			window.setLetterState(LetterState.No_Letter);
			talkWindowsContainer.addWindowForUser(stateData.username, window);
			System.out.println("M901");
			for( Letter letter : stateData.letters ){
				System.out.println("M902");
				addLetterToView(stateData.username, letter);
			}
		}		
	}

	@Override
	public void recievedNewLetter(Letter letter) throws ParseException {
		if( talkWindowsContainer.isWindowOpenForUser(letter.sender) == false){
			usersTableView.changeStateOfUser(letter.sender, TalkState.NewMessage);
		} else {
			addLetterToView(letter.sender, letter);
		}
	}

	@Override
	public void letterWasWritten(Username username, String text) {
		model.addCommand( commandsProvider.getLetterWasWrittenCommand(text, username));
		talkWindowsContainer.getUserWindow(username).setLetterState(LetterState.Letter_Sending);
	}
	
	private void addLetterToView( Username userTalkingTo,  Letter letter ) throws ParseException{
		ILetterView letterView = letterViewFactory.getLetterView(letter.sender.getName(), 
				letter.text.getTextValue(), letter.date.getDateAsString(), letter.type == LetterSendingType.Recieved);
		talkWindowsContainer.getUserWindow(userTalkingTo).addLetterView(letterView);			
	}

	@Override
	public void letterWasSent(Letter letter) {
		// TODO Auto-generated method stub
		
	}
}
