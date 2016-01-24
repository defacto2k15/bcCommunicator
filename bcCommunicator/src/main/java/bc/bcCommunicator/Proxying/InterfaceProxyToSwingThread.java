package bc.bcCommunicator.Proxying;
import javax.swing.SwingUtilities;

// TODO: Auto-generated Javadoc
/**
 * The Class InterfaceProxyToSwingThread.
 *
 * @param <TProxiedInterface> the generic type
 */
public class InterfaceProxyToSwingThread<TProxiedInterface> extends AbstractInterfaceProxyToAnotherThread<TProxiedInterface> {

	/**
	 * Instantiates a new interface proxy to swing thread.
	 *
	 * @param target the target
	 */
	public InterfaceProxyToSwingThread(TProxiedInterface target) {
		super(target);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Proxying.AbstractInterfaceProxyToAnotherThread#getMethodCalledBySpiningThread()
	 */
	@Override
	Runnable getMethodCalledBySpiningThread() {
		return () -> {
			SwingUtilities.invokeLater( () -> {
				try {
					MethodArgsPair pair = commandQueue.take();
					pair.method.invoke(target, pair.args);
				} catch (Exception e) {
					System.err.println("E750");
					e.printStackTrace();
				}
			});
		};
	}

}
