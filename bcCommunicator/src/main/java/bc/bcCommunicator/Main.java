package bc.bcCommunicator;

import java.net.URL;

import Controller.CommunicatorController;
import Controller.ICommunicatorController;
import bc.bcCommunicator.Model.CommunicatorModel;
import bc.bcCommunicator.Model.CommunicatorModelCommandsProvider;
import bc.bcCommunicator.Model.ConnectionsContainer;
import bc.bcCommunicator.Model.ICommunicatorModel;
import bc.bcCommunicator.Model.ICommunicatorModelCommandsProvider;
import bc.bcCommunicator.Model.IConnectionsContainer;
import bc.bcCommunicator.Model.IModelMessagesSender;
import bc.bcCommunicator.Model.ModelMessagesSender;
import bc.bcCommunicator.Model.UsernameContainer;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.IInternetMessagerCommandProvider;
import bc.bcCommunicator.Model.Internet.InternetMessager;
import bc.bcCommunicator.Model.Internet.InternetMessagerCommandProvider;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.ModelMessageProvider;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.IRecievedMessageCreator;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFieldsExtractor;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFieldsValuesCreator;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFromTypeCreator;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.RecievedMessageCreator;
import bc.bcCommunicator.Model.Messages.Handling.AllUsersAddressesResponseHandler;
import bc.bcCommunicator.Model.Messages.Handling.RecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Handling.UsernameOkResponseHandler;
import bc.bcCommunicator.Views.IServerConnectionStatusView;
import bc.bcCommunicator.Views.MainWindow;
import bc.bcCommunicator.Views.ServerConnectionStatusView;
import bc.bcCommunicator.Views.UsernameInputView;
import bc.bcCommunicator.Views.UsersTableView;

public class Main {

	public static void main(String[] inputs) {
		if( inputs.length == 0){
			try{
				URL clientUrl = new URL("http://localhost:9090");
				Main main = new Main(clientUrl);
			} catch( Exception e){
				e.printStackTrace();
			}			
		} else {
			try{
				URL clientUrl = new URL(inputs[0]);
				Main main = new Main(clientUrl);
			} catch( Exception e){
				e.printStackTrace();
			}
		}

	}
	
	public Main( URL clientUrl){
		
		IInternetMessagerCommandProvider messagerCommandsProvider = new InternetMessagerCommandProvider();
		ICommunicatorModelCommandsProvider modelCommandsProvider = new CommunicatorModelCommandsProvider();
		IInternetMessager messager = new InternetMessager(messagerCommandsProvider, modelCommandsProvider,
				new RecievedMessageCreator( new MessageFieldsExtractor( new MessageFieldsValuesCreator()),
						new MessageFromTypeCreator()));
		
		UsernameInputView usernameInputView = new UsernameInputView();
		
		UsernameContainer usernameContainer = new UsernameContainer();

		
		IConnectionsContainer connectionsContainer = new ConnectionsContainer();
		IModelMessageProvider messagesProvider = new ModelMessageProvider();
		IInternetMessagerCommandProvider commandProvider = new InternetMessagerCommandProvider();
		IModelMessagesSender messagesSender = new ModelMessagesSender(usernameContainer, connectionsContainer, commandProvider, messagesProvider, messager, clientUrl);
		AllUsersAddressesResponseHandler allUsersResponseHandler = new AllUsersAddressesResponseHandler(usernameContainer, commandProvider, messager);
		ICommunicatorModel model 
			= new CommunicatorModel(messager, commandProvider, clientUrl,
					messagesProvider, connectionsContainer, usernameContainer, 
					new RecievedMessagesHandler(new UsernameOkResponseHandler(usernameContainer, usernameInputView, messagesSender), 
												allUsersResponseHandler), messagesSender );
		
		UsersTableView usersTableView = new UsersTableView();		
		ServerConnectionStatusView connectionStatusView = new ServerConnectionStatusView();
		ICommunicatorController controller
			= new CommunicatorController(connectionStatusView, model, modelCommandsProvider, usernameInputView, usersTableView);
		controller.setViewHandlers();
		model.setController(controller);
		allUsersResponseHandler.setController(controller);
		messager.setModel(model);
		
		
		MainWindow window = new MainWindow(connectionStatusView, usernameInputView, usersTableView);		
		// USTAW HANDLERA ALL usernames and addresses tak aby wywyłał odpowiednią wiadomość do controllera (setBulk..),
		// a potem stworz test co to testuje
	}

}
