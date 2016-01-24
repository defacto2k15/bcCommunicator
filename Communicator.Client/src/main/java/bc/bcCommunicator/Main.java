package bc.bcCommunicator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;

import bc.bcCommunicator.Controller.CommunicatorController;
import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.bcCommunicator.Controller.TalkStateData;
import bc.bcCommunicator.Controller.TalkWindowFactory;
import bc.bcCommunicator.Controller.TalkWindowsContainer;
import bc.bcCommunicator.Model.ActorUsernameContainer;
import bc.bcCommunicator.Model.CommunicatorModel;
import bc.bcCommunicator.Model.ConnectionsContainer;
import bc.bcCommunicator.Model.ConnectivityHandler;
import bc.bcCommunicator.Model.ICommunicatorModel;
import bc.bcCommunicator.Model.IConnectionsContainer;
import bc.bcCommunicator.Model.IConnectivityHandler;
import bc.bcCommunicator.Model.IModelMessagesSender;
import bc.bcCommunicator.Model.IPendingLettersContainer;
import bc.bcCommunicator.Model.LetterContainer;
import bc.bcCommunicator.Model.ModelMessagesSender;
import bc.bcCommunicator.Model.OtherUsersDataContainer;
import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.InternetMessager;
import bc.bcCommunicator.Model.Messages.IModelMessageProvider;
import bc.bcCommunicator.Model.Messages.ModelMessageProvider;
import bc.bcCommunicator.Model.Messages.PendingLettersContainer;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFieldsExtractor;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFieldsValuesCreator;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFromTypeCreator;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.RecievedMessageCreator;
import bc.bcCommunicator.Model.Messages.Handling.AllUsersAddressesResponseHandler;
import bc.bcCommunicator.Model.Messages.Handling.IntroductoryTalkHandler;
import bc.bcCommunicator.Model.Messages.Handling.LetterTalkMessageHandler;
import bc.bcCommunicator.Model.Messages.Handling.RecievedMessagesHandler;
import bc.bcCommunicator.Model.Messages.Handling.UsernameBadMessageHandler;
import bc.bcCommunicator.Model.Messages.Handling.UsernameOkResponseHandler;
import bc.bcCommunicator.Model.Messages.Letter.Letter;
import bc.bcCommunicator.Proxying.BoxingProxy;
import bc.bcCommunicator.Proxying.NewThreadProxyToOtherThread;
import bc.bcCommunicator.Proxying.ProxyToOtherThread;
import bc.bcCommunicator.Proxying.ProxyToSwingThread;
import bc.bcCommunicator.Views.LetterView;
import bc.bcCommunicator.Views.MainWindow;
import bc.bcCommunicator.Views.ServerConnectionStatusView;
import bc.bcCommunicator.Views.UsernameInputView;
import bc.bcCommunicator.Views.UsersTableView;

/**
 * The Class Main of client
 */
public class Main {

	/**
	 * The main method of client
	 *
	 * @param inputs the arguments, we expect on argument: port client have to use, if this argument
	 * is absent, port is defaulted to 9090
	 */
	public static void main(String[] inputs) {
		if (inputs.length == 0) {
			try {
				URL clientUrl = new URL("http://localhost:9090");
				Main main = new Main(clientUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				URL clientUrl = new URL("http://127.0.0.1:" + inputs[0]);
				Main main = new Main(clientUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Instantiates a new main.
	 *
	 * @param clientUrl the client url, usually localhost+port from command line arg
	 */
	public Main(URL clientUrl) {
		ProxyToOtherThread proxy = new NewThreadProxyToOtherThread();

		// CommunicatorControllerContainer boxedController = new
		// CommunicatorControllerContainer();
		BoxingProxy<ICommunicatorController> controllerProxy = new BoxingProxy<>();
		ICommunicatorController boxedController = createProxiedObject(ICommunicatorController.class, controllerProxy);

		BoxingProxy<IConnectivityHandler> connectivityHandlerProxy = new BoxingProxy<>();
		IConnectivityHandler boxedConnectivityHandler = createProxiedObject(IConnectivityHandler.class,
				connectivityHandlerProxy);

		ProxyToOtherThread messagerProxy = new NewThreadProxyToOtherThread();

		IInternetMessager concreteMessager = new InternetMessager(
				new RecievedMessageCreator(new MessageFieldsExtractor(new MessageFieldsValuesCreator()),
						new MessageFromTypeCreator()),
				boxedConnectivityHandler);
		messagerProxy.addObjectToProxy(concreteMessager);
		IInternetMessager messager = createProxiedObject(IInternetMessager.class, messagerProxy);

		UsernameInputView usernameInputView = new UsernameInputView();

		OtherUsersDataContainer usernameContainer = new OtherUsersDataContainer();
		ActorUsernameContainer actorUsernameContainer = new ActorUsernameContainer();
		IPendingLettersContainer pendingLettersContainer = new PendingLettersContainer();

		LetterContainer letterContainer = new LetterContainer();

		IConnectionsContainer connectionsContainer = new ConnectionsContainer();
		IModelMessageProvider messagesProvider = new ModelMessageProvider();
		IModelMessagesSender messagesSender = new ModelMessagesSender(actorUsernameContainer, connectionsContainer,
				messagesProvider, messager, clientUrl);
		AllUsersAddressesResponseHandler allUsersResponseHandler = new AllUsersAddressesResponseHandler(
				usernameContainer, messager, clientUrl, boxedController);

		IntroductoryTalkHandler introductoryTalkHandler = new IntroductoryTalkHandler(boxedController,
				usernameContainer, connectionsContainer);
		LetterTalkMessageHandler letterTalkHandler = new LetterTalkMessageHandler(letterContainer, boxedController);

		ConnectivityHandler concreteConnectivityHandler = new ConnectivityHandler(boxedController, clientUrl,
				connectionsContainer, usernameContainer, actorUsernameContainer, messagesSender,
				pendingLettersContainer, letterContainer,
				new RecievedMessagesHandler(
						new UsernameOkResponseHandler(actorUsernameContainer, messagesSender, boxedController),
						allUsersResponseHandler, introductoryTalkHandler, letterTalkHandler,
						new UsernameBadMessageHandler(boxedController)));
		proxy.addObjectToProxy((IConnectivityHandler) concreteConnectivityHandler);
		IConnectivityHandler connectivityHandler = (IConnectivityHandler) Proxy.newProxyInstance(
				IConnectivityHandler.class.getClassLoader(), new Class[] { IConnectivityHandler.class }, proxy);

		CommunicatorModel concreteModel = new CommunicatorModel(messager, clientUrl, messagesProvider,
				connectionsContainer, usernameContainer, messagesSender, actorUsernameContainer, boxedController,
				TalkStateData::new, Letter::new, letterContainer, pendingLettersContainer);
		proxy.addObjectToProxy((ICommunicatorModel) concreteModel);
		ICommunicatorModel model = createProxiedObject(ICommunicatorModel.class, proxy);

		UsersTableView usersTableView = new UsersTableView(boxedController);
		ServerConnectionStatusView connectionStatusView = new ServerConnectionStatusView();

		ProxyToOtherThread swingProxy = new ProxyToSwingThread();
		ICommunicatorController concreteController = new CommunicatorController(connectionStatusView, model,
				usernameInputView, usersTableView, new TalkWindowsContainer(),
				new TalkWindowFactory(), LetterView::new);
		ICommunicatorController controller = createProxiedObject(ICommunicatorController.class, swingProxy);
		swingProxy.addObjectToProxy(concreteController);

		controller.setViewHandlers();
		controllerProxy.setTarget(controller);
		connectivityHandlerProxy.setTarget(connectivityHandler);

		MainWindow window = new MainWindow(connectionStatusView, usernameInputView, usersTableView);
	}

	private <T> T createProxiedObject(Class<T> cls, InvocationHandler handler) {
		return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[] { cls }, handler);
	}

}
