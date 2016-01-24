package bc.bcCommunicator.Proxying;

import java.util.function.Consumer;

public class NewThreadProxyToOtherThread extends ProxyToOtherThread {
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
