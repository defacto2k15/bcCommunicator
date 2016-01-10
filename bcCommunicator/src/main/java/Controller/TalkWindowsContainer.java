package Controller;

import java.util.HashMap;
import java.util.Map;

import bc.bcCommunicator.Model.BasicTypes.Username;
import bc.bcCommunicator.Views.ITalkWindow;

public class TalkWindowsContainer implements ITalkWindowsContainer {
	Map<String, ITalkWindow> windowsMap = new HashMap<>();
	// TODO co zrobic jak ktos zamknie okno - trzeba wtedy jakos z tej mapy okno usunąc

	
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
	}

	@Override
	public ITalkWindow getUserWindow(Username username) {
		if( isWindowOpenForUser(username) == false){
			throw new IllegalArgumentException("There is no window open for user "+username);
		}
		return windowsMap.get(username.getName());
	}

}
