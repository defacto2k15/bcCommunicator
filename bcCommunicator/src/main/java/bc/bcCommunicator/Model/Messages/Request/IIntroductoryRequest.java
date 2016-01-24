package bc.bcCommunicator.Model.Messages.Request;

import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;

public interface IIntroductoryRequest {
	public Username getUsername();
	
	public URL getClientUrl();
}
