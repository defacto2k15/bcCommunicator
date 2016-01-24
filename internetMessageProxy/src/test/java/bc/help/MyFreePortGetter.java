package bc.help;

import java.io.IOException;
import java.net.ServerSocket;

// TODO: Auto-generated Javadoc
/**
 * The Class MyFreePortGetter.
 */
public class MyFreePortGetter {
	
	/**
	 * Gets the free port number.
	 *
	 * @return the free port number
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public int getFreePortNumber() throws IOException{
		ServerSocket s = new ServerSocket(0);
		int portNumber = s.getLocalPort();
		s.close();		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return portNumber;
	}
}
