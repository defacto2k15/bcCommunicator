package bc.bcCommunicator.Controller;

import org.jmock.Expectations;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import bc.bcCommunicator.EndToEnd.Help.ConstantSampleInstances;
import bc.bcCommunicator.Model.ICommunicatorModel;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterSendingType;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
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
import bc.internetMessageProxy.ConnectionId;

@RunWith(JMock.class)
public class CommunicatorControllerTest {
	private final Mockery context = new JUnit4Mockery();
	private final IServerConnectionStatusView connectionView = context.mock(IServerConnectionStatusView.class);
	private final IUsernameInputView usernameView = context.mock(IUsernameInputView.class);
	private final ICommunicatorModel communicatorModel = context.mock(ICommunicatorModel.class);

	private final IUsersTableView usersTableView = context.mock(IUsersTableView.class);
	private final ITalkWindowsContainer talkWindowsContainer = context.mock(ITalkWindowsContainer.class);
	private final ITalkWindowsFactory talkWindowsFactory = context.mock(ITalkWindowsFactory.class);
	private final ILetterViewFactory letterViewFactory = context.mock(ILetterViewFactory.class);

	private final ICommunicatorController controller 
					= new CommunicatorController(connectionView, communicatorModel, usernameView, 
							usersTableView, talkWindowsContainer, talkWindowsFactory, letterViewFactory);
	
	@Test
	public void onStartConnectionButtonClickedWillPassCommandToModel() throws Exception{
		final String serverAddressString="http://localhost:9090";
		
		context.checking(new Expectations(){{
			oneOf(connectionView).getServerAddress(); will(returnValue(serverAddressString));
			oneOf(communicatorModel).connectToServer(new URL(serverAddressString));
		}});
		controller.serverConnectionAcceptanceButtonWasClicked();
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenPassedServerAddressIsBadlabelInViewWillReflectThis() throws MalformedURLException{
		final String badServerAddressString="BAD_ADDRESS";
		
		context.checking(new Expectations(){{
			oneOf(connectionView).getServerAddress(); will(returnValue(badServerAddressString));
			oneOf(connectionView).setServerConnectionStatus(ServerConnectionStatus.ErrorMalformedUrl);
		}});
		controller.serverConnectionAcceptanceButtonWasClicked();
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenServerConnectionFailedServerConnectionStatusIsSet(){
		context.checking(new Expectations(){{
			oneOf(connectionView).setServerConnectionStatus(ServerConnectionStatus.ConnectionFailed);
			oneOf(usernameView).setUsernameInputStatus(UsernameInputStatus.UsernameEmpty);
			oneOf(connectionView).enableView();
		}});
		controller.serverConnectionFailed();
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenUsernameInputButtonIsClickedAndNoInputIsInTextfieldLabelIsApropiatelySet() throws Exception{
		String emptyUsernameText = "";
		context.checking(new Expectations(){{
			oneOf(usernameView).getUsernameText(); will(returnValue(emptyUsernameText));
			oneOf(usernameView).setUsernameInputStatus(UsernameInputStatus.UsernameEmpty);
		}});
		controller.usernameInputSubmitButtonWasClicked();
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenUsernameIsSubmitedAnIdtIsOkControllerPassesUsernameToModel() throws Exception{
		String usernameText = "SomeText";
		context.checking(new Expectations(){{
			oneOf(usernameView).getUsernameText(); will(returnValue(usernameText));
			oneOf(communicatorModel).usernameSubmitted(new Username(usernameText));
			oneOf(usernameView).disableView();
			oneOf(connectionView).enableView();
		}});
		controller.usernameInputSubmitButtonWasClicked();
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenUsersAreBulkSetViewIsClearedAndUsernamesAreSetAgain(){
		List<Username> usernames = new ArrayList<Username>();
		usernames.add(new Username("USER1"));
		usernames.add(new Username("USER2"));
		usernames.add(new Username("USER3"));
		
		context.checking(new Expectations(){{
			oneOf(usersTableView).clearTable();
			for( Username oneName : usernames ){
				oneOf(usersTableView).addLineToTable(oneName, UserConnectionState.NotConnected, TalkState.NoNewMessages);
			}
		}});
		
		controller.setBulkUsers(usernames);
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenUserWasConnectedControllerTellsUserTableToSetAppropiateUserConnectionStatus(){
		Username username = new Username("Some name");
		context.checking(new Expectations(){{
			oneOf(usersTableView).changeStateOfUser(username, UserConnectionState.Connected);
		}});
		controller.userWasConnected(username);
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenConnectedUserIsLostInUserTableAppropiateStatusIsSet(){
		Username username = new Username("Some name");
		context.checking(new Expectations(){{
			oneOf(usersTableView).changeStateOfUser(username, UserConnectionState.ConnectionLost);
			oneOf(talkWindowsContainer).isWindowOpenForUser(username); will(returnValue(false));
		}});
		controller.userConnectionLost(username);
		context.assertIsSatisfied();	
	}
	
	@Test
	public void whenConnectedUserIsLostAndTalkWindowIsOpenAppropiateStatusIsSet(){
		ITalkWindow talkWindow = context.mock(ITalkWindow.class);
		Username username = new Username("Some name");
		context.checking(new Expectations(){{
			allowing(usersTableView);
			oneOf(talkWindowsContainer).isWindowOpenForUser(username); will(returnValue(true));
			oneOf(talkWindowsContainer).getUserWindow(username); will(returnValue(talkWindow));
			oneOf(talkWindow).setConnectionState(UserConnectionState.ConnectionLost);
		}});
		controller.userConnectionLost(username);
		context.assertIsSatisfied();	
	}
	
	@Test
	public void whenNewUserIsAddedAppropiateLineIsAddedToTableAndIfTalkWindowIsOpenedWeUpdateState(){
		Username username = new Username("Some name");
		ITalkWindow talkWindow = context.mock(ITalkWindow.class);
		
		context.checking( new Expectations(){{
			oneOf(usersTableView).addLineToTable(username, UserConnectionState.Connected, TalkState.NoNewMessages);
			oneOf(talkWindowsContainer).isWindowOpenForUser(username); will(returnValue(true));
			oneOf(talkWindowsContainer).getUserWindow(username); will(returnValue(talkWindow));
			oneOf(talkWindow).setConnectionState(UserConnectionState.Connected);
		}});
		controller.newUserConnected(username);
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenUsersTableRowIsClickedWeCheckIfTalkWindowIsOpenAndIfNotWeAskModelForTalkStateData() throws ParseException{
		Username username = new Username("Some name");
		context.checking( new Expectations(){{
			oneOf(talkWindowsContainer).isWindowOpenForUser(username); will(returnValue(false));
			oneOf(communicatorModel).getTalkStateData(username);
		}});
		
		controller.rowInUserTableWasClicked(username);
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenTalkStateChangedAndTalkWindowIsNotPresentItIsGenerated() throws ParseException{
		ITalkWindow window = context.mock(ITalkWindow.class);
		Username username = new Username("Some name");
		
		Letter letter = ConstantSampleInstances.getSampleLetter();
		List<Letter> letterList = new ArrayList<>();
		letterList.add(letter);
		
		ILetterView view = new ILetterView() {
		};
		
		TalkStateData stateData = new TalkStateData(username, letterList, UserConnectionState.Connected);
		context.checking( new Expectations(){{
			oneOf(talkWindowsContainer).isWindowOpenForUser(username); will(returnValue(false));
			oneOf(talkWindowsFactory).createTalkWindow(username, controller); will(returnValue(window));
			oneOf(talkWindowsContainer).addWindowForUser(username, window);
			oneOf(window).setConnectionState(  UserConnectionState.Connected);
			oneOf(window).setUsername( username);
			oneOf(window).setLetterState(LetterState.No_Letter);
			
			//allowing(window).addLetterView(with(any(ILetterView.class)));
		}});
		checkThatNewLetterViewIsCreated(letter.getOtherUserInTalk(), letter, false);
		
		controller.talkStateChanged(stateData);
		context.assertIsSatisfied();
	}
	
	@Test
	public void afterLetterSentIfTalkWindowIsNotOpenRowInTalkTableIsChangedToInsidateNewMessageShown() throws ParseException{
		Letter letter = ConstantSampleInstances.getSampleLetter();
		context.checking(new Expectations(){{
			oneOf(talkWindowsContainer).isWindowOpenForUser(letter.getOtherUserInTalk()); will(returnValue(false));
			oneOf(usersTableView).changeStateOfUser(letter.getOtherUserInTalk(), TalkState.NewMessage);
		}});
		
		controller.recievedNewLetter(letter);
		context.assertIsSatisfied();
	}
	
	@Test
	public void afterLetterSendIfTalkWindowIsOpenNewLetterViewIsAdded() throws ParseException{
		
		Letter letter = ConstantSampleInstances.getSampleLetter();
		context.checking(new Expectations(){{
			oneOf(talkWindowsContainer).isWindowOpenForUser(letter.getOtherUserInTalk()); will(returnValue(true));
		}});
		checkThatNewLetterViewIsCreated(letter.getOtherUserInTalk(), letter, false);
		
		controller.recievedNewLetter(letter);
		context.assertIsSatisfied();			
	}
	
	
	@Test
	public void afterLetterIsWrittenModelIsNotifiedAndStateInTalkWindowIsChanged() throws Exception{
		String letterText = "SomeLetterText";
		Username recipient = new Username("SomeName");
		ITalkWindow window = context.mock(ITalkWindow.class);
		
		context.checking(new Expectations(){{
			oneOf(communicatorModel).letterWasWritten(letterText, recipient);
			oneOf(talkWindowsContainer).getUserWindow(recipient); will(returnValue(window));
			oneOf(window).setLetterState(LetterState.Letter_Sending);
		}});
		
		controller.letterWasWritten(recipient, letterText);
		context.assertIsSatisfied();
	}
	
	@Test
	public void afterLetterWasSentWeAddItToTalkWindowAndSetLetterStateLabelInThere() throws ParseException{
		Letter letter = ConstantSampleInstances.getSampleLetter();
		ITalkWindow window = context.mock(ITalkWindow.class);
		
		context.checking( new Expectations(){{
			oneOf(talkWindowsContainer).isWindowOpenForUser(letter.getOtherUserInTalk()); will(returnValue(true));
			oneOf(talkWindowsContainer).getUserWindow(letter.getOtherUserInTalk()); will(returnValue(window));
			oneOf(window).setLetterState(LetterState.Letter_Sent);
			oneOf(window).emptyInputField();
		}});
		checkThatNewLetterViewIsCreated(letter.getOtherUserInTalk(), letter, false);
		
		controller.letterWasSent(letter);
		context.assertIsSatisfied();
	}
	
	private void checkThatNewLetterViewIsCreated( Username userTalkingTo,  Letter letter, boolean alignLeft) throws ParseException{
		ILetterView view = new ILetterView() {
		};			
		ITalkWindow window = context.mock(ITalkWindow.class, "w1");
		
		context.checking(new Expectations(){{
			oneOf(letterViewFactory).getLetterView(letter.sender.getName(), letter.text.getTextValue(),
					letter.date.getDateAsString(), alignLeft); will(returnValue(view));
					
			oneOf(talkWindowsContainer).getUserWindow(userTalkingTo); will(returnValue(window));
			oneOf(window).addLetterView(view);
		}});
		/* contex.assertIsSatisfied NOT WYMAGANE */
	}
	
	@Test
	public void whenLetterSendingFailedWeChangeStateInTalkWindow() throws Exception {
		Username username = new Username("SomeName");
		ITalkWindow window = context.mock(ITalkWindow.class);
		
		context.checking(new Expectations(){{
			oneOf(talkWindowsContainer).isWindowOpenForUser(username); will(returnValue(true));
			oneOf(talkWindowsContainer).getUserWindow(username); will(returnValue(window));
			oneOf(window).setLetterState(LetterState.Letter_Failed);	
		}});
		controller.letterSendingFailed(username);
		context.assertIsSatisfied();
	}

}
