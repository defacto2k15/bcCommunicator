package bc.bcCommunicator.EndToEnd.Help;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;

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

	public void assertRecievedIntroductoryTalkWith(Username clientUsername, URL clientUrl) {
		// TODO Auto-generated method stub
		// TODO implement this!
	}

}
