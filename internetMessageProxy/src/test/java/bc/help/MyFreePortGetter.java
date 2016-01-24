package bc.help;

import java.io.IOException;
import java.net.ServerSocket;

public class MyFreePortGetter {
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
