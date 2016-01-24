package bc.bcCommunicator.Client.Help;

import java.awt.Component;

import javax.swing.JTextArea;

import org.hamcrest.Matcher;

import com.objogate.wl.Prober;
import com.objogate.wl.swing.ComponentSelector;
import com.objogate.wl.swing.driver.ComponentDriver;
import com.objogate.wl.swing.driver.JTextComponentDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class JTextAreaDriver extends JTextComponentDriver<JTextArea> {
    public JTextAreaDriver(GesturePerformer gesturePerformer, ComponentSelector<JTextArea> componentSelector, Prober prober) {
        super(gesturePerformer, componentSelector, prober);
    }
    
    public JTextAreaDriver(ComponentDriver<? extends Component> parentOrOwner, ComponentSelector<JTextArea> componentSelector) {
        super(parentOrOwner, componentSelector);
    }

    public JTextAreaDriver(ComponentDriver<? extends Component> parentOrOwner, Class<JTextArea> componentType, Matcher<? super JTextArea>... matchers) {
        super(parentOrOwner, componentType, matchers);
    }
}