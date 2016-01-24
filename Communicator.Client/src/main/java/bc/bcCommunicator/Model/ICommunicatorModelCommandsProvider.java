package bc.bcCommunicator.Model;

import java.net.URL;
import java.util.Optional;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Model.Messages.IMessage;
import bc.internetMessageProxy.ConnectionId;

public interface ICommunicatorModelCommandsProvider {

	ICommunicatorModelCommand getConnectServerCommand(URL urlToConnect);

	ICommunicatorModelCommand getServerConnectionWasSuccesfullCommand(ConnectionId connectionId) throws Exception;

	ICommunicatorModelCommand getServerConnectionFailedCommand();

	ICommunicatorModelCommand getConnectionLostCommand(ConnectionId id);

	ICommunicatorModelCommand getUsernameSubmittedCommand(Username username);

	ICommunicatorModelCommand getMessageRecievedCommand(IMessage recievedMessage, ConnectionId connectionId);
	
	ICommunicatorModelCommand getUserConnectionFailed( URL failedUrl);

	ICommunicatorModelCommand getUserConectionWasSuccesfullCommand( URL sucessfullUrl,  ConnectionId result);

	ICommunicatorModelCommand getGetTalkStateDataCommand(Username username);

	ICommunicatorModelCommand getLetterWasWrittenCommand(String letterText, Username recipient);

	ICommunicatorModelCommand getMessageWasSentSuccesfullyCommand(ConnectionId id);

	ICommunicatorModelCommand getMessageSendingFailedCommand(ConnectionId id);
	
}