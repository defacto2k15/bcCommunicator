package bc.bcCommunicator.Proxying;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;


// TODO: Auto-generated Javadoc
/**
 * The Class AbstractInterfaceProxyToAnotherThread.
 *
 * @param <TProxiedInterface> the generic type
 */
public abstract class AbstractInterfaceProxyToAnotherThread<TProxiedInterface> implements InvocationHandler{
	
	/** The command queue. */
	protected BlockingQueue<MethodArgsPair> commandQueue = new ArrayBlockingQueue<>(30);
	
	/** The target. */
	protected TProxiedInterface target;
	
	/**
	 * Instantiates a new abstract interface proxy to another thread.
	 *
	 * @param target the target
	 */
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
	
	/**
	 * Gets the method called by spining thread.
	 *
	 * @return the method called by spining thread
	 */
	abstract Runnable getMethodCalledBySpiningThread();


	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if( method.getReturnType() != void.class){
			throw new IllegalArgumentException("Only void returning methods may be called by the proxy!");
		}
		commandQueue.put(new MethodArgsPair(method, args));
		return null;
	}

	
}
