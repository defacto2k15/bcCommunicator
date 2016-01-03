package bc.bcCommunicator.Model.Messages.Talk;

import java.net.MalformedURLException;
import java.net.URL;

import bc.bcCommunicator.Model.BasicTypes.Username;

public interface IIntroductoryTalk {

	Username getUsername();

	URL getUrl() throws MalformedURLException;

}