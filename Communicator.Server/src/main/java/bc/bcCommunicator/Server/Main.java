package bc.bcCommunicator.Server;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;

import bc.bcCommunicator.Model.Internet.IInternetMessager;
import bc.bcCommunicator.Model.Internet.InternetMessager;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFieldsExtractor;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFieldsValuesCreator;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.MessageFromTypeCreator;
import bc.bcCommunicator.Model.Messages.CreatingFromRecievedString.RecievedMessageCreator;
import bc.bcCommunicator.Proxying.BoxingProxy;
import bc.bcCommunicator.Proxying.NewThreadProxyToOtherThread;
import bc.bcCommunicator.Proxying.ProxyToOtherThread;


public class Main {
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
	
	private <T> T createProxiedObject(Class<T> cls, InvocationHandler handler ){
		return (T)Proxy.newProxyInstance(
				cls.getClassLoader(),
                new Class[] { cls },
                handler);
	}

}
