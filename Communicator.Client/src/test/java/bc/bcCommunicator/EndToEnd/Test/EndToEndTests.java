package bc.bcCommunicator.EndToEnd.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bc.commonTestUtilities.FakeServerRunner;
import bc.commonTestUtilities.FakeUserRunner;
import bc.commonTestUtilities.FreePortGetter;

import bc.bcCommunicator.EndToEnd.Help.CommunicatorClientRunner;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.Talk.IntroductoryTalk;
import bc.bcCommunicator.Views.LetterState;
import bc.bcCommunicator.Views.UserConnectionState;


public class EndToEndTests {
	private static int SERVER_PORT;
	private static int CLIENT_PORT;
	private static FakeServerRunner server;
	private final static CommunicatorClientRunner client = new CommunicatorClientRunner();
	private final Username username = new Username("USER_NME");
	private static URL clientUrl;
	//private AllUsersAddresses allUsersAddresses;
	private static List<FakeUserRunner> users = new ArrayList<>();
	private final static int CLIENT_COUNT = 3;
	
	static FreePortGetter getter = new FreePortGetter();
	

	@Before
	public  void setUp() throws Exception {
		
		SERVER_PORT = getter.getFreePortNumber();
		CLIENT_PORT = getter.getFreePortNumber();
		server = new FakeServerRunner(SERVER_PORT);
		clientUrl = new URL("http://127.0.0.1:"+CLIENT_PORT);
		client.start(CLIENT_PORT);
		server.start();
		
		//Map<Username, URL> usernameAddressesMap = new HashMap<>();
		for( int i = 0; i < CLIENT_COUNT; i++ ){
			int port = getter.getFreePortNumber();
			FakeUserRunner runner = new FakeUserRunner(port, new Username("User"+i), new URL("http://127.0.0.1:"+port));
			runner.start();
			users.add( runner );		
			//usernameAddressesMap.put(new Username("User"+i), new URL("http://127.0.0.1:"+port));
		}
		
		//allUsersAddresses = new AllUsersAddresses(usernameAddressesMap);
	}
	
	private List<Username> getUsernames(){
		return users.stream().map(FakeUserRunner::getUsername).collect(Collectors.toList());
	}
	
	private Map<Username, URL> getUsernamesWithAddresses(){
		return users.stream().collect(Collectors.toMap(FakeUserRunner::getUsername, FakeUserRunner::getUrl));
	}

	@After
	public void stopObjects() {
		server.stop();
		client.stop();
	}

	@Test
	public void whenClientStartsItIsNotConnectedToServer() {
		client.assertIsNotConnectedToServer();
	}
	@Test
	public void afterClientWritesHisNameIntroductionRequestIsSentToServer() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl);
	}
	

	@Test
	public void whenClientIsTryingToConnectToBadUriServerCanntConnectMessageIsShown() throws Exception {
		client.connectToServer(new URL("http://bad_URI.com:1111"));
		client.assertConnectionToServerFailed();
	}
	
	@Test
	public void whenConnectionToServerSuccededMessageIsShown() throws MalformedURLException, InterruptedException{
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		client.assertConnectionToServerSucceded();
	}
	
	@Test
	public void afterConnectionClientSendsIntroductionRequest() throws Exception{
		client.insertUsername(username); 
		client.connectToServer(new URL("http://127.0.0.1:"+SERVER_PORT));
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
	}
	
	@Test
	public void afterClientWritesHisNameIsIsWrittenThatUsernameWasAccepted() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		client.assertUsernameWasAccepted(); 		
	}

	@Test
	public void afterConnectionClientRequestsServerForUsersAddresses() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		server.assertHasRecievedRequestForUsersAdresses();
	}
	
	@Test
	public void afterRecievingAllUsersAddressesTheyAreWrittenToTableInView() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());		
		for( Username oneUsername : getUsernames()) {
			client.assertHasUserInUsersTable(oneUsername);
		}
	}
	
	@Test
	public void afterRecievingAllUsersAddressesInViewItsStateIsSetAsNotConnected() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());		

		for( Username oneUsername : getUsernames()) {
			client.assertUserHasConnectionState( oneUsername, UserConnectionState.NotConnected);
		}
	}
	
	@Test
	public void afterGettingAllUsersAddressesClientSendsIntroductoryTalkToOtherUsers() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		
		Thread.sleep(100);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);	
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());
		for( FakeUserRunner user : users ){
			user.assertRecievedIntroductoryTalkWith(username, new URL("http://127.0.0.1:"+CLIENT_PORT));
		}
	}
	
	@Test
	public void afterConnectingToUserAppropiateChangeOfStateIsWrittenToTable() throws Exception {
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);	
		
		Username someUserUsername = getUsernamesWithAddresses().keySet().iterator().next();
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());		
		client.assertUserHasConnectionState( someUserUsername, UserConnectionState.Connected);
	}
	
	@Test
	public void whenConnectionToUserFailsAppropiateChangeOfStateIsWrittenToTable() throws Exception {
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);	
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());	
		
		Thread.sleep(400);
		
		Username someUserUsername = users.get(0).getUsername();
		users.get(0).stop();
		
		client.assertUserHasConnectionState( someUserUsername, UserConnectionState.NotConnected );
	}
	
	@Test
	public void afterClientRecievesIntroductoryTalkItWritesUserDataToTable() throws Exception {
		Username fakeUserUsername = new Username("SomeUsername");
		int fakeUserPort = getter.getFreePortNumber();
		URL fakeUserUrl = new URL("http://127.0.0.1:"+fakeUserPort);
		FakeUserRunner fakeUser = new FakeUserRunner(fakeUserPort, fakeUserUsername, fakeUserUrl);
		
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.sendUsernameOkResponseWith(username, clientUrl);	
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());	
		
		Thread.sleep(200);
		fakeUser.start();
		fakeUser.connectTo( clientUrl );
		fakeUser.sendIntroducoryTalk( fakeUserUsername, fakeUserUrl);
		
		client.assertUserHasConnectionState( fakeUserUsername, UserConnectionState.Connected );
	}
	
	private void doStartupStuff() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		
		Thread.sleep(100);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);	
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());		
	}
	
	@Test
	public void afterClientClicksOnUserRowNewWindowAppears() throws Exception {
		doStartupStuff();
		
		Username clickedUser = getUsernamesWithAddresses().keySet().iterator().next();
		client.clickOnUserTableRow( clickedUser );
		client.assertHasTalkWindowForUser(clickedUser);
	}
	
	@Test
	public void afterOpeningTalkWindowAppropiateTalkStateDataIsPresent() throws Exception{
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);
		client.clickOnUserTableRow( userToTalkTo.getUsername() );		
		
		client.assertTalkWindowHasUserConnectionState(userToTalkTo.getUsername(), UserConnectionState.Connected );
		client.assertTalkWindowHasUsernameSet(userToTalkTo.getUsername());
		client.assertTalkWindowHasLetterStateSet(userToTalkTo.getUsername(), LetterState.No_Letter);
	}
	
	@Test
	public void afterRecievingLetterWhileTalkScreenIsClosedNewLetterMessageIsDisplayedOnTable() throws Exception {
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);
		//client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		LetterText letterText = new LetterText("Hello everyone this is message");
		userToTalkTo.sendLetterTalk( letterText );
		client.userTalkHasNewMessageInTable( userToTalkTo.getUsername());
	}
	
	@Test
	public void afterRecievingLetterWhileTalkScreenIsOpenTheLetterContentsAreWrittenToScreen() throws Exception {
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);
		client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		LetterText letterText = new LetterText("Hello everyone this is message");
		userToTalkTo.sendLetterTalk( letterText );
		client.talkWindowsHasLetter( userToTalkTo.getUsername(), letterText);
	}	
	
	@Test
	public void afterOpeningTalkWindowWritingLetterAndClickingSendTheLetterIsSentToFakeUser() throws Exception{
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);
		client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		LetterText letterText = new LetterText("Some letter text");
		client.writeLetterTextToInputField( userToTalkTo.getUsername(), letterText);
		client.clickSendButton( userToTalkTo.getUsername());
		
		userToTalkTo.ignoreRecievedMessage();
		userToTalkTo.assertRecievedLetterTalkWithText( userToTalkTo.getUsername(), letterText);
	}
	
	@Test
	public void afterUserSendsLetterAndTalkViewIsClokseAfterOpeningItWillBeVisible() throws Exception{
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);		
		
		LetterText letterText =  new LetterText("LETTER TEXT LOL");
		userToTalkTo.sendLetterTalk(letterText);
		
		client.clickOnUserTableRow( userToTalkTo.getUsername() );
		client.talkWindowsHasLetter( userToTalkTo.getUsername(), letterText);
	}
	
	@Test
	public void afterClientSendsLetterAndItIsSuccesfullLetterIsPutInTalkViewAndLetterSendingStatusIsChanged() throws Exception{
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);
		client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		LetterText letterText = new LetterText("Some letter text");
		client.writeLetterTextToInputField( userToTalkTo.getUsername(), letterText);
		client.clickSendButton( userToTalkTo.getUsername());
		
		client.talkWindowsHasLetter(userToTalkTo.getUsername(), letterText);
		client.assertTalkWindowHasLetterStateSet(userToTalkTo.getUsername(), LetterState.Letter_Sent);
		client.assertTalkWindowHasEmptyInputField(userToTalkTo.getUsername());
	}
	
	@Test
	public void afterSendingAndRecievingSeveralLettersAllAreWrittenToWindow() throws Exception{
		List<LetterText> lettersToWrite = Arrays.asList( new LetterText("Text1"), new LetterText("Text2"), new LetterText("text3") );
		List<LetterText> lettersToRecieve = Arrays.asList( new LetterText("Letter1"), new LetterText("Letter2"));
		
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);
		client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		writeAndSendLetters(userToTalkTo, lettersToWrite);
		recieveLetters( userToTalkTo, lettersToRecieve);
		
		assertTalkWindowHasLetters( userToTalkTo, lettersToWrite);
		assertTalkWindowHasLetters( userToTalkTo, lettersToRecieve);
	}



	private void assertTalkWindowHasLetters(FakeUserRunner userToTalkTo, List<LetterText> letters) {
		for( LetterText letterText : letters){
			client.talkWindowsHasLetter( userToTalkTo.getUsername(), letterText);
		}
	}

	private void recieveLetters(FakeUserRunner userToTalkTo, List<LetterText> lettersToRecieve) throws Exception {
		for( LetterText letterText : lettersToRecieve){
			userToTalkTo.sendLetterTalk(letterText);
		}
	}

	private void writeAndSendLetters(FakeUserRunner userToTalkTo, List<LetterText> lettersToWrite) {
		for( LetterText letterText : lettersToWrite){
			client.writeLetterTextToInputField( userToTalkTo.getUsername(), letterText);
			client.clickSendButton( userToTalkTo.getUsername());
		}
	}

	@Test
	public void afterClosingAndReopeningTalkWindowLettersAreStillThere() throws Exception {
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);
		client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		LetterText letterText = new LetterText("Some letter text");
		client.writeLetterTextToInputField( userToTalkTo.getUsername(), letterText);
		client.clickSendButton( userToTalkTo.getUsername());
		
		client.closeTalkWindow(userToTalkTo.getUsername() );
		
		client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		client.talkWindowsHasLetter( userToTalkTo.getUsername(), letterText);
	}
	
	@Test
	public void whenConnectionWithUserIsLostAppropiateDataIsWrittenToTable() throws Exception {
		doStartupStuff();
		
		FakeUserRunner userToTalkTo = users.get(0);	
		client.assertUserHasConnectionState( userToTalkTo.getUsername(), UserConnectionState.Connected);
		userToTalkTo.stop();
		client.assertUserHasConnectionState( userToTalkTo.getUsername(), UserConnectionState.ConnectionLost);
	}
	
	@Test
	public void whenConnectionWithUserIsLostConnectionLostStateIsWrittenInTalkWindow() throws Exception {
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);			
		client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		client.assertTalkWindowHasUserConnectionState(userToTalkTo.getUsername(), UserConnectionState.Connected );
		userToTalkTo.stop();
		client.assertTalkWindowHasUserConnectionState(userToTalkTo.getUsername(), UserConnectionState.ConnectionLost );
	}
	
	@Test
	public void ifConnectionIsLostAndThenReconnectedConnectionStateInUserTableIsSet() throws Exception {
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);			
		client.assertUserHasConnectionState( userToTalkTo.getUsername(), UserConnectionState.Connected);	
		
		userToTalkTo.stop();
		client.assertUserHasConnectionState( userToTalkTo.getUsername(), UserConnectionState.ConnectionLost);
		
		FakeUserRunner newRunner = new FakeUserRunner(getter.getFreePortNumber(), userToTalkTo.getUsername(), userToTalkTo.getUrl());
		newRunner.start();
		newRunner.connectTo(clientUrl);

		newRunner.sendIntroducoryTalk(newRunner.getUsername(), newRunner.getUrl());

		client.assertThereIsOneUserInTable( newRunner.getUsername() );
		client.assertUserHasConnectionState( newRunner.getUsername(), UserConnectionState.Connected);	
	}
	
	
	@Test
	public void ifWeSendLetterAndItFailsAppropiateStateIsSetInTalkWindow() throws Exception {
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);			
		client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		userToTalkTo.stop();
		writeAndSendLetters(userToTalkTo, Arrays.asList(new LetterText("Some text")));
		client.assertTalkWindowHasLetterStateSet(userToTalkTo.getUsername(), LetterState.Letter_Failed);
	}
	
	@Test
	public void whenConnectionToServerIsLostStatusIsUpdated() throws Exception{
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.insertUsername(username);
		client.connectToServer(url);
		client.assertConnectionToServerSucceded();
		
		server.stop();
		Thread.sleep(300);
		client.assertConnectionToServerFailed();
	}
	
}
