package bc.bcCommunicator.Model;

import java.net.URL;

import bc.bcCommunicator.Controller.ICommunicatorController;
import bc.internetMessageProxy.ConnectionId;

public interface IConnectivityHandler {
	public void serverConnectionWasSuccesfull(ConnectionId serverConnection) throws Exception;
	void serverConnectionFailed();
	void connectionLost(ConnectionId id);
	void userConnectionFailed(URL failedUrl);
	void userConnectionWasSuccesfull(URL sucessfullUrl, ConnectionId result) throws Exception;
	void messageSentSuccesfully(ConnectionId id) throws Exception;
	public void messageSendingFailed(ConnectionId id);
}
