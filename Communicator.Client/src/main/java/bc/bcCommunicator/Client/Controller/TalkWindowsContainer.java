package bc.bcCommunicator.Client.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import bc.bcCommunicator.Client.Views.ITalkWindow;
import bc.bcCommunicator.Client.Views.ITalkWindowViewClosingListener;
import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Class TalkWindowsContainer.
 */
public class TalkWindowsContainer implements ITalkWindowsContainer, ITalkWindowViewClosingListener {
	
	/** The windows map. */
	Map<String, ITalkWindow> windowsMap = new HashMap<>();
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ITalkWindowsContainer#isWindowOpenForUser(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public boolean isWindowOpenForUser(Username username) {
		return windowsMap.containsKey(username.getName());
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ITalkWindowsContainer#addWindowForUser(bc.bcCommunicator.Model.BasicTypes.Username, bc.bcCommunicator.Client.Views.ITalkWindow)
	 */
	@Override
	public void addWindowForUser(Username username, ITalkWindow window) {
		if( windowsMap.containsKey(username)){
			throw new IllegalArgumentException("There arleady is window for username with name "+username.getName());
		}
		windowsMap.put(username.getName(), window);
		window.setClosingListener(this);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Controller.ITalkWindowsContainer#getUserWindow(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public ITalkWindow getUserWindow(Username username) {
		if( isWindowOpenForUser(username) == false){
			throw new IllegalArgumentException("There is no window open for user "+username);
		}
		return windowsMap.get(username.getName());
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.ITalkWindowViewClosingListener#windowIsClosing(bc.bcCommunicator.Client.Views.ITalkWindow)
	 */
	@Override
	public void windowIsClosing(ITalkWindow window) {
		System.out.println("M720 closing window");
		assert(windowsMap.containsValue(window));
		String usernameForWindow = windowsMap.entrySet().stream()
				.filter( (entry)->{ return entry.getValue() == window;})
				.map(entry->{return entry.getKey();})
				.collect(Collectors.toList())
				.get(0);
		windowsMap.remove(usernameForWindow);
	}

}
