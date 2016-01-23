package bc.bcCommunicator.Proxying;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;


public abstract class AbstractInterfaceProxyToAnotherThread<TProxiedInterface> implements InvocationHandler{
	protected BlockingQueue<MethodArgsPair> commandQueue = new ArrayBlockingQueue<>(30);
	protected TProxiedInterface target;
	
	public AbstractInterfaceProxyToAnotherThread(TProxiedInterface target) {
		this.target = target;
		
		Thread newThread = new Thread(()->{
			while(true){
				getMethodCalledBySpiningThread().run();
			}
		});
		newThread.setDaemon(true);
		newThread.start();	
	}
	
	abstract Runnable getMethodCalledBySpiningThread();


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if( method.getReturnType() != void.class){
			throw new IllegalArgumentException("Only void returning methods may be called by the proxy!");
		}
		commandQueue.put(new MethodArgsPair(method, args));
		return null;
	}

	
}
