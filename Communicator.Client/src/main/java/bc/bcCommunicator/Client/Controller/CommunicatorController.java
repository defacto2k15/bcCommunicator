package bc.bcCommunicator.Client.Controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;

import bc.bcCommunicator.Client.Model.ICommunicatorModel;
import bc.bcCommunicator.Client.Views.ILetterView;
import bc.bcCommunicator.Client.Views.ILetterViewFactory;
import bc.bcCommunicator.Client.Views.IServerConnectionStatusView;
import bc.bcCommunicator.Client.Views.ITalkWindow;
import bc.bcCommunicator.Client.Views.IUsernameInputView;
import bc.bcCommunicator.Client.Views.IUsersTableView;
import bc.bcCommunicator.Client.Views.LetterState;
import bc.bcCommunicator.Client.Views.ServerConnectionStatus;
import bc.bcCommunicator.Client.Views.TalkState;
import bc.bcCommunicator.Client.Views.UserConnectionState;
import bc.bcCommunicator.Client.Views.UsernameInputStatus;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterSendingType;

// TODO: Auto-generated Javadoc
/**
 * The Class CommunicatorController.
 */
public class CommunicatorController implements ICommunicatorController {
	
	/** The connection status view. */
	private IServerConnectionStatusView connectionStatusView;
	
	/** The model. */
	private ICommunicatorModel model;
	
	/** The username input view. */
	private IUsernameInputView usernameInputView;
	
	/** The users table view. */
	private IUsersTableView usersTableView;
	
	/** The talk windows container. */
	private ITalkWindowsContainer talkWindowsContainer;
	
	/** The windows factory. */
	private ITalkWindowsFactory windowsFactory;
	
	/** The letter view factory. */
	private ILetterViewFactory letterViewFactory;

	/**
	 * Instantiates a new communicator controller.
	 *
	 * @param connectionStatusView the connection status view
	 * @param model the model
	 * @param usernameInputView the username input view
	 * @param usersTableView the users table view
	 * @param talkWindowsContainer the talk windows container
	 * @param windowsFactory the windows factory
	 * @param letterViewFactory the letter view factory
	 */
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
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#setViewHandlers()
	 */
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

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#serverConnectionAcceptanceButtonWasClicked()
	 */
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

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#serverConnectionWasSuccesfull()
	 */
	@Override
	public void serverConnectionWasSuccesfull() {
		connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.Connected);
		connectionStatusView.disableViev();
		usernameInputView.disableView();
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#serverConnectionFailed()
	 */
	@Override
	public void serverConnectionFailed() {
		connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.ConnectionFailed);
		usernameInputView.setUsernameInputStatus(UsernameInputStatus.UsernameEmpty);
		connectionStatusView.enableView();
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#usernameInputSubmitButtonWasClicked()
	 */
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

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#setBulkUsers(java.util.List)
	 */
	@Override
	public void setBulkUsers(List<Username> usernames) {
		usersTableView.clearTable();
		for( Username oneName : usernames){
			usersTableView.addLineToTable(oneName, UserConnectionState.NotConnected, TalkState.NoNewMessages);
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#userWasConnected(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void userWasConnected(Username username) {
		usersTableView.changeStateOfUser(username, UserConnectionState.Connected);
		
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#userConnectionLost(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void userConnectionLost(Username username) {
		usersTableView.changeStateOfUser(username, UserConnectionState.ConnectionLost);
		if( talkWindowsContainer.isWindowOpenForUser(username)){
			talkWindowsContainer.getUserWindow(username).setConnectionState(UserConnectionState.ConnectionLost);
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#newUserConnected(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void newUserConnected(Username username) {	
		usersTableView.addLineToTable(username, UserConnectionState.Connected, TalkState.NoNewMessages);
		if( talkWindowsContainer.isWindowOpenForUser(username) ){
			talkWindowsContainer.getUserWindow(username).setConnectionState(UserConnectionState.Connected);
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#rowInUserTableWasClicked(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void rowInUserTableWasClicked(Username username) throws ParseException {
		if (talkWindowsContainer.isWindowOpenForUser(username) ){
			ITalkWindow talkWindow = talkWindowsContainer.getUserWindow(username);
			talkWindow.requetsToBeActiveFrame();
			usersTableView.changeStateOfUser(username, TalkState.NoNewMessages);
		} else {
			usersTableView.changeStateOfUser(username, TalkState.NoNewMessages);
			model.getTalkStateData(username);
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#talkStateChanged(bc.bcCommunicator.Client.Controller.TalkStateData)
	 */
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

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#recievedNewLetter(bc.bcCommunicator.Model.Messages.Letter.Letter)
	 */
	@Override
	public void recievedNewLetter(Letter letter) throws ParseException {
		if( talkWindowsContainer.isWindowOpenForUser(letter.getOtherUserInTalk()) == false){
			usersTableView.changeStateOfUser(letter.getOtherUserInTalk(), TalkState.NewMessage);
		} else {
			addLetterToView(letter.getOtherUserInTalk(), letter);
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#letterWasWritten(bc.bcCommunicator.Model.BasicTypes.Username, java.lang.String)
	 */
	@Override
	public void letterWasWritten(Username username, String text) throws Exception {
		model.letterWasWritten(text, username);
		talkWindowsContainer.getUserWindow(username).setLetterState(LetterState.Letter_Sending);
	}
	
	/**
	 * Adds the letter to view.
	 *
	 * @param userTalkingTo the user talking to
	 * @param letter the letter
	 * @throws ParseException the parse exception
	 */
	private void addLetterToView( Username userTalkingTo,  Letter letter ) throws ParseException{
		ILetterView letterView = letterViewFactory.getLetterView(letter.sender.getName(), 
				letter.text.getTextValue(), letter.date.getDateAsString(), letter.type == LetterSendingType.Recieved);
		talkWindowsContainer.getUserWindow(userTalkingTo).addLetterView(letterView);			
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#letterWasSent(bc.bcCommunicator.Model.Messages.Letter.Letter)
	 */
	@Override
	public void letterWasSent(Letter letter) throws ParseException {
		if( talkWindowsContainer.isWindowOpenForUser(letter.getOtherUserInTalk())){
			ITalkWindow talkWindow = talkWindowsContainer.getUserWindow(letter.getOtherUserInTalk());
			talkWindow.setLetterState(LetterState.Letter_Sent);
			talkWindow.emptyInputField();
			addLetterToView(letter.getOtherUserInTalk(), letter);
		}
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#letterSendingFailed(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void letterSendingFailed(Username username) {
		if( talkWindowsContainer.isWindowOpenForUser(username) ){
			talkWindowsContainer.getUserWindow(username).setLetterState(LetterState.Letter_Failed);
		}
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#usernameWasOk()
	 */
	@Override
	public void usernameWasOk(){
		usernameInputView.setUsernameInputStatus(UsernameInputStatus.UsernameOk);
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ICommunicatorController#usernameWasBad()
	 */
	@Override
	public void usernameWasBad(){
		usernameInputView.setUsernameInputStatus(UsernameInputStatus.UsernameBad);
		usernameInputView.enableView();
		connectionStatusView.setServerConnectionStatus(ServerConnectionStatus.NotConnected);
		connectionStatusView.enableView();
	}
}
