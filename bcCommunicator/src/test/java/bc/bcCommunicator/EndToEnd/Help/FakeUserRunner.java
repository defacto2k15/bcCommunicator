package bc.bcCommunicator.EndToEnd.Help;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Request.IntroductoryRequest;
import bc.bcCommunicator.Model.Messages.Talk.IntroductoryTalk;

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
		assertRecievedMessageWithText( new IntroductoryTalk(clientUsername, clientUrl).getMessageText() );
	}

}