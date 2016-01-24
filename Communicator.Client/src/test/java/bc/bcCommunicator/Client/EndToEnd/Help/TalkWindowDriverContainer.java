package bc.bcCommunicator.Client.EndToEnd.Help;

import java.util.HashMap;
import java.util.Map;

import bc.bcCommunicator.Client.WindowNames;
import bc.bcCommunicator.Model.BasicTypes.Username;

public class TalkWindowDriverContainer {
	Map<Username, TalkWindowDriver> driversList = new HashMap<>();
	
	TalkWindowDriver getDriver( Username username ){
		if(driversList.containsKey(username) == false){
			driversList.put(username, new TalkWindowDriver(WindowNames.TALK_WINDOW_PREFIX+username.getName()));
		}
		return driversList.get(username);
	}
}
