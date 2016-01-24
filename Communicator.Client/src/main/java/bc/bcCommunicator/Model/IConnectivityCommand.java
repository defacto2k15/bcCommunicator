package bc.bcCommunicator.Model;

public interface IConnectivityCommand {
	void run( IConnectivityHandler handler) throws Exception;
}
