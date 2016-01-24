package bc.bcCommunicator.Client.EndToEnd.Help;

import java.awt.Component;

import javax.swing.JTextArea;

import org.hamcrest.Matcher;

import com.objogate.wl.Prober;
import com.objogate.wl.swing.ComponentSelector;
import com.objogate.wl.swing.driver.ComponentDriver;
import com.objogate.wl.swing.driver.JTextComponentDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

// TODO: Auto-generated Javadoc
/**
 * The Class JTextAreaDriver.
 */
public class JTextAreaDriver extends JTextComponentDriver<JTextArea> {
    
    /**
     * Instantiates a new j text area driver.
     *
     * @param gesturePerformer the gesture performer
     * @param componentSelector the component selector
     * @param prober the prober
     */
    public JTextAreaDriver(GesturePerformer gesturePerformer, ComponentSelector<JTextArea> componentSelector, Prober prober) {
        super(gesturePerformer, componentSelector, prober);
    }
    
    /**
     * Instantiates a new j text area driver.
     *
     * @param parentOrOwner the parent or owner
     * @param componentSelector the component selector
     */
    public JTextAreaDriver(ComponentDriver<? extends Component> parentOrOwner, ComponentSelector<JTextArea> componentSelector) {
        super(parentOrOwner, componentSelector);
    }

    /**
     * Instantiates a new j text area driver.
     *
     * @param parentOrOwner the parent or owner
     * @param componentType the component type
     * @param matchers the matchers
     */
    public JTextAreaDriver(ComponentDriver<? extends Component> parentOrOwner, Class<JTextArea> componentType, Matcher<? super JTextArea>... matchers) {
        super(parentOrOwner, componentType, matchers);
    }
}