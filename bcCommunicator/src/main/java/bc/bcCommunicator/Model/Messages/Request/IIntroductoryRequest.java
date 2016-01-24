package bc.bcCommunicator.Model.Messages.Request;

import java.net.MalformedURLException;
import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.MessageField;

public interface IIntroductoryRequest {
	public Username getUsername();
	
	public URL getClientUrl();
}
