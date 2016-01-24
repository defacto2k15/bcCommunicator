package bc.bcCommunicator.Client.Help;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bc.bcCommunicator.Client.WindowNames;
import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Class TalkWindowDriverContainer.
 */
public class TalkWindowDriverContainer {
	
	/** The drivers list. */
	Map<Username, TalkWindowDriver> driversList = new HashMap<>();
	
	/**
	 * Gets the driver.
	 *
	 * @param username the username
	 * @return the driver
	 */
	TalkWindowDriver getDriver( Username username ){
		if(driversList.containsKey(username) == false){
			driversList.put(username, new TalkWindowDriver(WindowNames.TALK_WINDOW_PREFIX+username.getName()));
		}
		return driversList.get(username);
	}
}
