package bc.Communicator.Server.Test;

import java.net.URL;

import bc.bcCommunicator.Server.Main;

public class ServerRunner {
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
