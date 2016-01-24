package bc.bcCommunicator.Model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bc.bcCommunicator.Model.BasicTypes.Username;
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
	
	@Test
	public void canSaveAndRecieveIdOfOtherUserFromHisName(){
		Username name = new Username("SomeName");
		ConnectionId id = new ConnectionId(99);
		container.setIdForUser(name, id);
		assertEquals(id, container.getConnectionIdOfUser(name));
	}
	
	@Test(expected=IllegalStateException.class)
	public void whenTryingToGetIdForNotAddedUserExceptionIsThrown(){
		container.setIdForUser(new Username("Name1"), new ConnectionId(22));
		container.setIdForUser(new Username("Name2"), new ConnectionId(33));
		
		container.getConnectionIdOfUser(new Username("BAD NAME"));
	}
}
