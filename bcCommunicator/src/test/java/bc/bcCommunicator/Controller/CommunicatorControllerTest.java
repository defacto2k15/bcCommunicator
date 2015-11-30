package bc.bcCommunicator.Controller;

import org.jmock.Expectations;

import java.net.MalformedURLException;
import java.net.URL;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

import Controller.CommunicatorController;
import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.ICommunicatorModel;
import bc.bcCommunicator.Model.ICommunicatorModelCommand;
import bc.bcCommunicator.Model.ICommunicatorModelCommandsProvider;
import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.Handling.IRecievedMessagesHandler;
import bc.bcCommunicator.Views.IServerConnectionStatusView;
import bc.bcCommunicator.Views.IUsernameInputView;
import bc.bcCommunicator.Views.ServerConnectionStatus;
import bc.bcCommunicator.Views.UsernameInputStatus;
import bc.internetMessageProxy.ConnectionId;

@RunWith(JMock.class)
public class CommunicatorControllerTest {
	private final Mockery context = new JUnit4Mockery();
	private final IServerConnectionStatusView connectionView = context.mock(IServerConnectionStatusView.class);
	private final IUsernameInputView usernameView = context.mock(IUsernameInputView.class);
	private final ICommunicatorModel communicatorModel = context.mock(ICommunicatorModel.class);
	private final ICommunicatorModelCommandsProvider commandsProvider =
				context.mock(ICommunicatorModelCommandsProvider.class);
	private final ICommunicatorModelCommand command = context.mock(ICommunicatorModelCommand.class);

	private final ICommunicatorController controller 
					= new CommunicatorController(connectionView, communicatorModel, commandsProvider, usernameView);
	
	@Test
	public void onStartConnectionButtonClickedWillPassCommandToModel() throws Exception{
		final String serverAddressString="http://localhost:9090";
		
		context.checking(new Expectations(){{
			oneOf(connectionView).getServerAddress(); will(returnValue(serverAddressString));
			oneOf(commandsProvider).getConnectServerCommand(with(new URL(serverAddressString)));
							will(returnValue(command));
			oneOf(communicatorModel).addCommand(command);				
		}});
		controller.serverConnectionAcceptanceButtonWasClicked();
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenPassedServerAddressIsBadlabelInViewWillReflectThis() throws MalformedURLException{
		final String badServerAddressString="BAD_ADDRESS";
		
		context.checking(new Expectations(){{
			oneOf(connectionView).getServerAddress(); will(returnValue(badServerAddressString));
			oneOf(connectionView).setServerConnectionStatus(ServerConnectionStatus.ErrorMalformedUrl);
		}});
		controller.serverConnectionAcceptanceButtonWasClicked();
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenServerConnectionFailedServerConnectionStatusIsSet(){
		context.checking(new Expectations(){{
			oneOf(connectionView).setServerConnectionStatus(ServerConnectionStatus.ConnectionFailed);
		}});
		controller.serverConnectionFailed();
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenUsernameInputButtonIsClickedAndNoInputIsInTextfieldLabelIsApropiatelySet(){
		String emptyUsernameText = "";
		context.checking(new Expectations(){{
			oneOf(usernameView).getUsernameText(); will(returnValue(emptyUsernameText));
			oneOf(usernameView).setUsernameInputStatus(UsernameInputStatus.UsernameEmpty);
		}});
		controller.usernameInputSubmitButtonWasClicked();
		context.assertIsSatisfied();
	}
	
	@Test
	public void whenUsernameIsSubmitedAnIdtIsOkControllerPassesUsernameToModel(){
		String usernameText = "SomeText";
		context.checking(new Expectations(){{
			oneOf(usernameView).getUsernameText(); will(returnValue(usernameText));
			oneOf(commandsProvider).getUsernameSubmittedCommand(with(equal(new Username(usernameText)))); will(returnValue(command));
			oneOf(communicatorModel).addCommand(command);
		}});
		controller.usernameInputSubmitButtonWasClicked();
		context.assertIsSatisfied();
	}
	

}
