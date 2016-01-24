package bc.bcCommunicator.Client.Views;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving ITalkWindowViewClosing events.
 * The class that is interested in processing a ITalkWindowViewClosing
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addITalkWindowViewClosingListener<code> method. When
 * the ITalkWindowViewClosing event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ITalkWindowViewClosingEvent
 */
public interface ITalkWindowViewClosingListener {
	
	/**
	 * Window is closing.
	 *
	 * @param window the window
	 */
	void windowIsClosing( ITalkWindow window);
}
