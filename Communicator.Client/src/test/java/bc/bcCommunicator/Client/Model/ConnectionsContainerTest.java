package bc.bcCommunicator.Client.Model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bc.bcCommunicator.Client.Model.ConnectionsContainer;
import bc.bcCommunicator.Client.Model.IConnectionsContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.internetMessageProxy.ConnectionId;


// TODO: Auto-generated Javadoc
/**
 * The Class ConnectionsContainerTest.
 */
public class ConnectionsContainerTest {
	
	/** The container. */
	IConnectionsContainer container = new ConnectionsContainer();
	
	/**
	 * Can set and get server connection id.
	 */
	@Test
	public void canSetAndGetServerConnectionId(){
		ConnectionId id = new ConnectionId(10);
		container.setServerConnectionId(id);
		assertEquals(id, container.getServerConnectionId());
	}
	
	/**
	 * When getting server connection id before setting exception is thrown.
	 */
	@Test(expected=IllegalStateException.class)
	public void whenGettingServerConnectionIdBeforeSettingExceptionIsThrown(){
		container.getServerConnectionId();
	}
	
	/**
	 * When getting server connection id after removing it exception is thrown.
	 */
	@Test(expected=IllegalStateException.class)
	public void whenGettingServerConnectionIdAfterRemovingItExceptionIsThrown(){
		container.setServerConnectionId(new ConnectionId(99));
		container.removeServerConnectionIdIfExists();
		container.getServerConnectionId();
	}
	
	/**
	 * Can save and recieve id of other user from his name.
	 */
	@Test
	public void canSaveAndRecieveIdOfOtherUserFromHisName(){
		Username name = new Username("SomeName");
		ConnectionId id = new ConnectionId(99);
		container.setIdForUser(name, id);
		assertEquals(id, container.getConnectionIdOfUser(name));
	}
	
	/**
	 * When trying to get id for not added user exception is thrown.
	 */
	@Test(expected=IllegalStateException.class)
	public void whenTryingToGetIdForNotAddedUserExceptionIsThrown(){
		container.setIdForUser(new Username("Name1"), new ConnectionId(22));
		container.setIdForUser(new Username("Name2"), new ConnectionId(33));
		
		container.getConnectionIdOfUser(new Username("BAD NAME"));
	}
}
