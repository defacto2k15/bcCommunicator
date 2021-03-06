package bc.bcCommunicator.Proxying;

import javax.swing.SwingUtilities;


// TODO: Auto-generated Javadoc
/**
 * The Class ProxyToSwingThread.
 */
public class ProxyToSwingThread extends ProxyToOtherThread {

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Proxying.ProxyToOtherThread#threadingMethod()
	 */
	@Override
	void threadingMethod() {
		Thread newThread = new Thread( ()->{
			while(true){
				try {
					MethodArgsPair pair = commandQueue.take();
					Object target = objectsToCallMap.stream().filter( 
							p -> p.methods.contains(pair.method)).map( p -> p.concreteObject).findFirst().get();
					SwingUtilities.invokeAndWait(() -> {
						try {
							pair.method.invoke(target, pair.args);
						} catch (Exception e) {
							System.err.println("E752");
							e.printStackTrace();
						}					
					});
				} catch (Exception e) {
					System.err.println("E751");
					e.printStackTrace();
				}
			}
		});
		newThread.setDaemon(true);
		newThread.start();
	}

}
