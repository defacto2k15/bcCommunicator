package bc.bcCommunicator.Client.EndToEnd.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bc.bcCommunicator.Client.EndToEnd.Help.CommunicatorClientRunner;
import bc.bcCommunicator.Client.Views.LetterState;
import bc.bcCommunicator.Client.Views.UserConnectionState;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.commonTestUtilities.FakeServerRunner;
import bc.commonTestUtilities.FakeUserRunner;
import bc.commonTestUtilities.FreePortGetter;


// TODO: Auto-generated Javadoc
/**
 * The Class EndToEndTests.
 */
public class EndToEndTests {
	
	/** The server port. */
	private static int SERVER_PORT;
	
	/** The client port. */
	private static int CLIENT_PORT;
	
	/** The server. */
	private static FakeServerRunner server;
	
	/** The Constant client. */
	private final static CommunicatorClientRunner client = new CommunicatorClientRunner();
	
	/** The username. */
	private final Username username = new Username("USER_NME");
	
	/** The client url. */
	private static URL clientUrl;
	
	/** The users. */
	//private AllUsersAddresses allUsersAddresses;
	private static List<FakeUserRunner> users = new ArrayList<>();
	
	/** The Constant CLIENT_COUNT. */
	private final static int CLIENT_COUNT = 3;
	
	/** The getter. */
	static FreePortGetter getter = new FreePortGetter();
	

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * Gets the usernames.
	 *
	 * @return the usernames
	 */
	private List<Username> getUsernames(){
		return users.stream().map(FakeUserRunner::getUsername).collect(Collectors.toList());
	}
	
	/**
	 * Gets the usernames with addresses.
	 *
	 * @return the usernames with addresses
	 */
	private Map<Username, URL> getUsernamesWithAddresses(){
		return users.stream().collect(Collectors.toMap(FakeUserRunner::getUsername, FakeUserRunner::getUrl));
	}

	/**
	 * Stop objects.
	 */
	@After
	public void stopObjects() {
		server.stop();
		client.stop();
	}

	/**
	 * When client starts it is not connected to server.
	 */
	@Test
	public void whenClientStartsItIsNotConnectedToServer() {
		client.assertIsNotConnectedToServer();
	}
	
	/**
	 * After client writes his name introduction request is sent to server.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterClientWritesHisNameIntroductionRequestIsSentToServer() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl);
	}
	

	/**
	 * When client is trying to connect to bad uri server cannt connect message is shown.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void whenClientIsTryingToConnectToBadUriServerCanntConnectMessageIsShown() throws Exception {
		client.connectToServer(new URL("http://bad_URI.com:1111"));
		client.assertConnectionToServerFailed();
	}
	
	/**
	 * When connection to server succeded message is shown.
	 *
	 * @throws MalformedURLException the malformed url exception
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void whenConnectionToServerSuccededMessageIsShown() throws MalformedURLException, InterruptedException{
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		client.assertConnectionToServerSucceded();
	}
	
	/**
	 * After connection client sends introduction request.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterConnectionClientSendsIntroductionRequest() throws Exception{
		client.insertUsername(username); 
		client.connectToServer(new URL("http://127.0.0.1:"+SERVER_PORT));
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
	}
	
	/**
	 * After client writes his name is is written that username was accepted.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterClientWritesHisNameIsIsWrittenThatUsernameWasAccepted() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		client.assertUsernameWasAccepted(); 		
	}

	/**
	 * After connection client requests server for users addresses.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterConnectionClientRequestsServerForUsersAddresses() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);
		server.assertHasRecievedRequestForUsersAdresses();
	}
	
	/**
	 * After recieving all users addresses they are written to table in view.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * After recieving all users addresses in view its state is set as not connected.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * After getting all users addresses client sends introductory talk to other users.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * After connecting to user appropiate change of state is written to table.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * When connection to user fails appropiate change of state is written to table.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * After client recieves introductory talk it writes user data to table.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * Do startup stuff.
	 *
	 * @throws Exception the exception
	 */
	private void doStartupStuff() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		
		Thread.sleep(100);
		server.assertHasRecievedIntrodutionRequestWith(username, clientUrl );
		server.sendUsernameOkResponseWith(username, clientUrl);	
		server.sendAllUsersAddressesResponse(getUsernamesWithAddresses());		
	}
	
	/**
	 * After client clicks on user row new window appears.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterClientClicksOnUserRowNewWindowAppears() throws Exception {
		doStartupStuff();
		
		Username clickedUser = getUsernamesWithAddresses().keySet().iterator().next();
		client.clickOnUserTableRow( clickedUser );
		client.assertHasTalkWindowForUser(clickedUser);
	}
	
	/**
	 * After opening talk window appropiate talk state data is present.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterOpeningTalkWindowAppropiateTalkStateDataIsPresent() throws Exception{
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);
		client.clickOnUserTableRow( userToTalkTo.getUsername() );		
		
		client.assertTalkWindowHasUserConnectionState(userToTalkTo.getUsername(), UserConnectionState.Connected );
		client.assertTalkWindowHasUsernameSet(userToTalkTo.getUsername());
		client.assertTalkWindowHasLetterStateSet(userToTalkTo.getUsername(), LetterState.No_Letter);
	}
	
	/**
	 * After recieving letter while talk screen is closed new letter message is displayed on table.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterRecievingLetterWhileTalkScreenIsClosedNewLetterMessageIsDisplayedOnTable() throws Exception {
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);
		//client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		LetterText letterText = new LetterText("Hello everyone this is message");
		userToTalkTo.sendLetterTalk( letterText );
		client.userTalkHasNewMessageInTable( userToTalkTo.getUsername());
	}
	
	/**
	 * After recieving letter while talk screen is open the letter contents are written to screen.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterRecievingLetterWhileTalkScreenIsOpenTheLetterContentsAreWrittenToScreen() throws Exception {
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);
		client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		LetterText letterText = new LetterText("Hello everyone this is message");
		userToTalkTo.sendLetterTalk( letterText );
		client.talkWindowsHasLetter( userToTalkTo.getUsername(), letterText);
	}	
	
	/**
	 * After opening talk window writing letter and clicking send the letter is sent to fake user.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * After user sends letter and talk view is clokse after opening it will be visible.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void afterUserSendsLetterAndTalkViewIsClokseAfterOpeningItWillBeVisible() throws Exception{
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);		
		
		LetterText letterText =  new LetterText("LETTER TEXT LOL");
		userToTalkTo.sendLetterTalk(letterText);
		
		client.clickOnUserTableRow( userToTalkTo.getUsername() );
		client.talkWindowsHasLetter( userToTalkTo.getUsername(), letterText);
	}
	
	/**
	 * After client sends letter and it is succesfull letter is put in talk view and letter sending status is changed.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * After sending and recieving several letters all are written to window.
	 *
	 * @throws Exception the exception
	 */
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



	/**
	 * Assert talk window has letters.
	 *
	 * @param userToTalkTo the user to talk to
	 * @param letters the letters
	 */
	private void assertTalkWindowHasLetters(FakeUserRunner userToTalkTo, List<LetterText> letters) {
		for( LetterText letterText : letters){
			client.talkWindowsHasLetter( userToTalkTo.getUsername(), letterText);
		}
	}

	/**
	 * Recieve letters.
	 *
	 * @param userToTalkTo the user to talk to
	 * @param lettersToRecieve the letters to recieve
	 * @throws Exception the exception
	 */
	private void recieveLetters(FakeUserRunner userToTalkTo, List<LetterText> lettersToRecieve) throws Exception {
		for( LetterText letterText : lettersToRecieve){
			userToTalkTo.sendLetterTalk(letterText);
		}
	}

	/**
	 * Write and send letters.
	 *
	 * @param userToTalkTo the user to talk to
	 * @param lettersToWrite the letters to write
	 */
	private void writeAndSendLetters(FakeUserRunner userToTalkTo, List<LetterText> lettersToWrite) {
		for( LetterText letterText : lettersToWrite){
			client.writeLetterTextToInputField( userToTalkTo.getUsername(), letterText);
			client.clickSendButton( userToTalkTo.getUsername());
		}
	}

	/**
	 * After closing and reopening talk window letters are still there.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * When connection with user is lost appropiate data is written to table.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void whenConnectionWithUserIsLostAppropiateDataIsWrittenToTable() throws Exception {
		doStartupStuff();
		
		FakeUserRunner userToTalkTo = users.get(0);	
		client.assertUserHasConnectionState( userToTalkTo.getUsername(), UserConnectionState.Connected);
		userToTalkTo.stop();
		client.assertUserHasConnectionState( userToTalkTo.getUsername(), UserConnectionState.ConnectionLost);
	}
	
	/**
	 * When connection with user is lost connection lost state is written in talk window.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void whenConnectionWithUserIsLostConnectionLostStateIsWrittenInTalkWindow() throws Exception {
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);			
		client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		client.assertTalkWindowHasUserConnectionState(userToTalkTo.getUsername(), UserConnectionState.Connected );
		userToTalkTo.stop();
		client.assertTalkWindowHasUserConnectionState(userToTalkTo.getUsername(), UserConnectionState.ConnectionLost );
	}
	
	/**
	 * If connection is lost and then reconnected connection state in user table is set.
	 *
	 * @throws Exception the exception
	 */
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
	
	
	/**
	 * If we send letter and it fails appropiate state is set in talk window.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void ifWeSendLetterAndItFailsAppropiateStateIsSetInTalkWindow() throws Exception {
		doStartupStuff();
		FakeUserRunner userToTalkTo = users.get(0);			
		client.clickOnUserTableRow( userToTalkTo.getUsername() );	
		
		userToTalkTo.stop();
		writeAndSendLetters(userToTalkTo, Arrays.asList(new LetterText("Some text")));
		client.assertTalkWindowHasLetterStateSet(userToTalkTo.getUsername(), LetterState.Letter_Failed);
	}
	
	/**
	 * When connection to server is lost status is updated.
	 *
	 * @throws Exception the exception
	 */
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
	
	@Test
	public void whenServerSendsBadUsernameResponseAppropiateDataIsSetInTable() throws Exception{
		client.insertUsername(username); 
		URL url = new URL("http://127.0.0.1:"+SERVER_PORT);
		client.connectToServer(url);
		server.sendUsernameBadResponse(username);
		client.assertUsernameWasNotAccepted();
	}
	
}
