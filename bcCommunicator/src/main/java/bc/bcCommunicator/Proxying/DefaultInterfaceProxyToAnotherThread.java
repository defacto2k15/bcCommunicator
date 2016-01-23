package bc.bcCommunicator.Proxying;
import java.util.function.Consumer;

public class DefaultInterfaceProxyToAnotherThread<TProxiedInterface> extends AbstractInterfaceProxyToAnotherThread<TProxiedInterface> {

	public DefaultInterfaceProxyToAnotherThread(TProxiedInterface target) {
		super(target);
	}

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
