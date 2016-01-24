package bc.bcCommunicator.Client.Model.Messages.Handling;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Client.Model.IActorUsernameContainer;
import bc.bcCommunicator.Client.Model.IModelMessagesSender;
import bc.bcCommunicator.Client.Model.Messages.Handling.UsernameOkResponseHandler;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse;
import bc.internetMessageProxy.ConnectionId;

public class UsernameOkResponseHandlerTest {
	private final Mockery context = new JUnit4Mockery();
	private IActorUsernameContainer container = context.mock( IActorUsernameContainer.class);
	private ICommunicatorController controller = context.mock(ICommunicatorController.class);
	private IUsernameOkResponse response = context.mock(IUsernameOkResponse.class);
	private ConnectionId id = new ConnectionId(92);
	private final IModelMessagesSender messagesSender = context.mock(IModelMessagesSender.class);
	private final UsernameOkResponseHandler handler = new UsernameOkResponseHandler(container, messagesSender, controller);
	
	@Test
	public void correctlyCallsViewThatUsernameWasOk() throws Exception {
		context.checking(new Expectations(){{
			ignoring(container);
			ignoring(response); 
			oneOf(controller).usernameWasOk();
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();
	}
	
	@Test
	public void getsUsernameFromResponseAndPutsItToContainer() throws Exception{
		final Username username = new Username("SomeName");
		context.checking(new Expectations(){{
			ignoring(controller);
			oneOf(response).getUsername(); will(returnValue(username)); 
			oneOf(container).setUsername(username);
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();		
	}
	
	@Test
	public void sendsAllUsersAddressesRequest() throws Exception{
		context.checking(new Expectations(){{
			ignoring(controller);
			ignoring(response);
			ignoring(container);
			oneOf(messagesSender).sendAllUsersAddressesRequest();
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();
	}

}
