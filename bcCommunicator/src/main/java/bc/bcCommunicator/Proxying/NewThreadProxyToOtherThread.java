package bc.bcCommunicator.Proxying;

// TODO: Auto-generated Javadoc
/**
 * The Class NewThreadProxyToOtherThread.
 */
public class NewThreadProxyToOtherThread extends ProxyToOtherThread {
	
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
					pair.method.invoke(target, pair.args);
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
