package bc.bcCommunicator.Server;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.InternetMessager;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFieldsExtractor;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFieldsValuesCreator;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFromTypeCreator;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.RecievedMessageCreator;
import bc.bcCommunicator.Proxying.BoxingProxy;


// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main {
	
	/**
	 * The main method.
	 *
	 * @param inputs the arguments
	 */
	public static void main(String[] inputs){
		if( inputs.length == 0){
			try{
				Main main = new Main(10001);
			} catch( Exception e){
				e.printStackTrace();
			}			
		} else {
			try{
				Main main = new Main( Integer.parseInt(inputs[0]));
			} catch( Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Instantiates a new main.
	 *
	 * @param portNumber the port number
	 */
	public Main( int portNumber){
		BoxingProxy<IServerMessagesHandler> messageHandlerProxy = new BoxingProxy<>();
		IServerMessagesHandler boxedMessageHandler = createProxiedObject(IServerMessagesHandler.class, messageHandlerProxy);
		
		UsersContainer usersContainer = new UsersContainer();
		
		Server server = new Server( boxedMessageHandler, usersContainer);
		
		IInternetMessager messager = new InternetMessager(
				new RecievedMessageCreator( new MessageFieldsExtractor( new MessageFieldsValuesCreator()),
						new MessageFromTypeCreator()), server);
		messager.listenOnPort(portNumber);
		
		messageHandlerProxy.setTarget( new ServerMessagesHandler(messager, usersContainer));
	}
	
	/**
	 * Creates the proxied object.
	 *
	 * @param <T> the generic type
	 * @param cls the cls
	 * @param handler the handler
	 * @return the t
	 */
	private <T> T createProxiedObject(Class<T> cls, InvocationHandler handler ){
		return (T)Proxy.newProxyInstance(
				cls.getClassLoader(),
                new Class[] { cls },
                handler);
	}

}
