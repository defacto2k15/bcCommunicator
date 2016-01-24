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

// TODO: Auto-generated Javadoc
/**
 * The Class UsernameOkResponseHandlerTest.
 */
public class UsernameOkResponseHandlerTest {
	
	/** The context. */
	private final Mockery context = new JUnit4Mockery();
	
	/** The container. */
	private IActorUsernameContainer container = context.mock( IActorUsernameContainer.class);
	
	/** The controller. */
	private ICommunicatorController controller = context.mock(ICommunicatorController.class);
	
	/** The response. */
	private IUsernameOkResponse response = context.mock(IUsernameOkResponse.class);
	
	/** The id. */
	private ConnectionId id = new ConnectionId(92);
	
	/** The messages sender. */
	private final IModelMessagesSender messagesSender = context.mock(IModelMessagesSender.class);
	
	/** The handler. */
	private final UsernameOkResponseHandler handler = new UsernameOkResponseHandler(container, messagesSender, controller);
	
	/**
	 * Correctly calls view that username was ok.
	 *
	 * @throws Exception the exception
	 */
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
	
	/**
	 * Gets the s username from response and puts it to container.
	 *
	 * @return the s username from response and puts it to container
	 * @throws Exception the exception
	 */
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
	
	/**
	 * Sends all users addresses request.
	 *
	 * @throws Exception the exception
	 */
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
