package bc.bcCommunicator.Proxying;
import java.util.function.Consumer;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultInterfaceProxyToAnotherThread.
 *
 * @param <TProxiedInterface> the generic type
 */
public class DefaultInterfaceProxyToAnotherThread<TProxiedInterface> extends AbstractInterfaceProxyToAnotherThread<TProxiedInterface> {

	/**
	 * Instantiates a new default interface proxy to another thread.
	 *
	 * @param target the target
	 */
	public DefaultInterfaceProxyToAnotherThread(TProxiedInterface target) {
		super(target);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Proxying.AbstractInterfaceProxyToAnotherThread#getMethodCalledBySpiningThread()
	 */
	@Override
	Runnable getMethodCalledBySpiningThread() {
		return () -> {
			try {
				MethodArgsPair pair = commandQueue.take();
				pair.method.invoke(target, pair.args);
			} catch (Exception e) {
				System.err.println("E750");
				e.printStackTrace();
			}
		};
	}


}
