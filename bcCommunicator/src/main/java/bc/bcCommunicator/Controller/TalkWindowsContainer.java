package bc.bcCommunicator.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Views.ITalkWindow;
import bc.bcCommunicator.Views.ITalkWindowViewClosingListener;

public class TalkWindowsContainer implements ITalkWindowsContainer, ITalkWindowViewClosingListener {
	Map<String, ITalkWindow> windowsMap = new HashMap<>();
	
	@Override
	public boolean isWindowOpenForUser(Username username) {
		return windowsMap.containsKey(username.getName());
	}

	@Override
	public void addWindowForUser(Username username, ITalkWindow window) {
		if( windowsMap.containsKey(username)){
			throw new IllegalArgumentException("There arleady is window for username with name "+username.getName());
		}
		windowsMap.put(username.getName(), window);
		window.setClosingListener(this);
	}

	@Override
	public ITalkWindow getUserWindow(Username username) {
		if( isWindowOpenForUser(username) == false){
			throw new IllegalArgumentException("There is no window open for user "+username);
		}
		return windowsMap.get(username.getName());
	}

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
