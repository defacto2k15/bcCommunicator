package bc.Communicator.Server.Test;

import bc.bcCommunicator.Server.Main;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerRunner.
 */
public class ServerRunner {
	
	/**
	 * Start.
	 *
	 * @param portNumber the port number
	 */
	public void start(int portNumber) {
		Thread thread = new Thread("Communicator server"){
			@Override public void run(){
				try{
					String[] inputs = {new Integer(portNumber).toString()};
					Main.main(inputs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
	}
}
