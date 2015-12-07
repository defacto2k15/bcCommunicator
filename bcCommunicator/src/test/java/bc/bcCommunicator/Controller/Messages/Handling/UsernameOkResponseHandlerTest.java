package bc.bcCommunicator.Controller.Messages.Handling;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;

import bc.bcCommunicator.Model.IModelMessagesSender;
import bc.bcCommunicator.Model.IUsernameContainer;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.Handling.UsernameOkResponseHandler;
import bc.bcCommunicator.Model.Messages.Response.IUsernameOkResponse;
import bc.bcCommunicator.Views.IUsernameInputView;
import bc.bcCommunicator.Views.UsernameInputStatus;
import bc.internetMessageProxy.ConnectionId;

public class UsernameOkResponseHandlerTest {
	private final Mockery context = new JUnit4Mockery();
	private IUsernameContainer container = context.mock( IUsernameContainer.class);
	private IUsernameInputView inputView = context.mock( IUsernameInputView.class );
	private IUsernameOkResponse response = context.mock(IUsernameOkResponse.class);
	private ConnectionId id = new ConnectionId(92);
	private final IModelMessagesSender messagesSender = context.mock(IModelMessagesSender.class);
	private final UsernameOkResponseHandler handler = new UsernameOkResponseHandler(container, inputView, messagesSender);
	
	@Test
	public void correctlyCallsViewThatUsernameWasOk() throws Exception {
		context.checking(new Expectations(){{
			ignoring(container);
			ignoring(response); 
			oneOf(inputView).setUsernameInputStatus(UsernameInputStatus.UsernameOk);
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();
	}
	
	@Test
	public void getsUsernameFromResponseAndPutsItToContainer() throws Exception{
		final Username username = new Username("SomeName");
		context.checking(new Expectations(){{
			ignoring(inputView);
			oneOf(response).getUsername(); will(returnValue(username)); 
			oneOf(container).setUsername(username);
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();		
	}
	
	@Test
	public void sendsAllUsersAddressesRequest() throws Exception{
		context.checking(new Expectations(){{
			ignoring(inputView);
			ignoring(response);
			ignoring(container);
			oneOf(messagesSender).sendAllUsersAddressesRequest();
		}});
		handler.handle(response, id);
		context.assertIsSatisfied();
	}

}
