package bc.bcCommunicator.Model;

import static org.junit.Assert.*;

import org.junit.Test;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Expectation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;

import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommand;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.Request.IRequest;
import bc.internetMessageProxy.ConnectionId;


public class ConnectionsContainerTest {
	IConnectionsContainer container = new ConnectionsContainer();
	
	@Test
	public void canSetAndGetServerConnectionId(){
		ConnectionId id = new ConnectionId(10);
		container.setServerConnectionId(id);
		assertEquals(id, container.getServerConnectionId());
	}
	
	@Test(expected=IllegalStateException.class)
	public void whenGettingServerConnectionIdBeforeSettingExceptionIsThrown(){
		container.getServerConnectionId();
	}
	
	@Test(expected=IllegalStateException.class)
	public void whenGettingServerConnectionIdAfterRemovingItExceptionIsThrown(){
		container.setServerConnectionId(new ConnectionId(99));
		container.removeServerConnectionIdIfExists();
		container.getServerConnectionId();
	}
}
