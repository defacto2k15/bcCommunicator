package bc.bcCommunicator.Proxying;
import javax.swing.SwingUtilities;

public class InterfaceProxyToSwingThread<TProxiedInterface> extends AbstractInterfaceProxyToAnotherThread<TProxiedInterface> {

	public InterfaceProxyToSwingThread(TProxiedInterface target) {
		super(target);
	}

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
