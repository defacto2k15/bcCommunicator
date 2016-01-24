package bc.commonTestUtilities;

import java.net.URL;
import java.util.Date;
import java.util.Map;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.AllUsersAddresses;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.Request.AllUsersAddressesRequest;
import bc.bcCommunicator.Model.Messages.Request.IntroductoryRequest;
import bc.bcCommunicator.Model.Messages.Response.AllUsersAddressesResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameBadResponse;
import bc.bcCommunicator.Model.Messages.Response.UsernameOkResponse;
import bc.bcCommunicator.Model.Messages.Talk.IntroductoryTalk;
import bc.bcCommunicator.Model.Messages.Talk.LetterTalk;

public class FakeUserRunner extends FakeInternetEntity{

	private Username username;
	private URL url;
	
	public FakeUserRunner(int port, Username username, URL url) {
		super(port);
		this.username = username;
		this.url = url;
	}

	public Username getUsername() {
		return username;
	}

	public URL getUrl() {
		return url;
	}
	
	public int getPort(){
		return this.port;
	}

	public void assertRecievedIntroductoryTalkWith(Username clientUsername, URL clientUrl) throws Exception {
		assertRecievedMessageWithExactText( new IntroductoryTalk(clientUsername, clientUrl).getMessageText() );
	}

	public void sendIntroducoryTalk(Username fakeUserUsername, URL fakeUserUrl) throws Exception {
		sendMessageToLastConnectionSocket( new IntroductoryTalk(fakeUserUsername, fakeUserUrl) );
	}

	public void sendLetterTalk( LetterText letterText) throws Exception {
		sendMessageToLastConnectionSocket( new LetterTalk(new LetterDate( new Date()), letterText, username, new Username("recipient")));
	}

	public void assertRecievedLetterTalkWithText(Username username2, LetterText letterText) {	
		String withoutFirstLetters = letterText.getTextValue().substring(3, letterText.getTextValue().length());
		assertRecievedMessageContainingText(withoutFirstLetters);
	}

	public void sendIntroductoryRequest() throws Exception {
		sendMessageToLastConnectionSocket( new IntroductoryRequest(username, url) );
	}

	public void assertRecievedUsernameOkResponse() throws Exception {
		assertRecievedMessageWithExactText(new UsernameOkResponse(username).getMessageText());
	}

	public void assertRecievedUsernameBadResponse() throws Exception {
		assertRecievedMessageWithExactText(new UsernameBadResponse(username).getMessageText());
	}

	public void sendAllUsersAddressesRequest() throws Exception {
		sendMessageToLastConnectionSocket( new AllUsersAddressesRequest() );
	}

	public void assertRecievedAllUsersAddressesResponse(Map<Username, URL> allUsersAddressesMap) throws Exception {
		assertRecievedMessageWithExactText( 
				new AllUsersAddressesResponse( new AllUsersAddresses(allUsersAddressesMap)).getMessageText());
	}


}
