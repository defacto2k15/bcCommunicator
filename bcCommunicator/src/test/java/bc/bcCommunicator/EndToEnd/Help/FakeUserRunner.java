package bc.bcCommunicator.EndToEnd.Help;

import java.net.URL;
import java.util.Date;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Letter.LetterDate;
import bc.bcCommunicator.Model.Messages.Letter.LetterText;
import bc.bcCommunicator.Model.Messages.Request.IntroductoryRequest;
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
		sendMessageToLastConnectionSocket( new LetterTalk(new LetterDate( new Date()), letterText, username));
	}

	public void assertRecievedLetterTalkWithText(Username username2, LetterText letterText) {	
		String withoutFirstLetters = letterText.getTextValue().substring(3, letterText.getTextValue().length());
		assertRecievedMessageContainingText(withoutFirstLetters);
	}


}
