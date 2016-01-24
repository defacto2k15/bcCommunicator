package bc.bcCommunicator.Client.Views;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

// TODO: Auto-generated Javadoc
/**
 * The Class ComponentHelp.
 */
public class ComponentHelp {
	 
 	/**
 	 * Adds the component.
 	 *
 	 * @param container the container
 	 * @param component the component
 	 * @param gridx the gridx
 	 * @param gridy the gridy
 	 * @param gridwidth the gridwidth
 	 * @param gridheight the gridheight
 	 * @param weightx the weightx
 	 * @param weighty the weighty
 	 * @param anchor the anchor
 	 * @param fill the fill
 	 */
 	public static void addComponent(Container container, Component component, int gridx, int gridy,
		      int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill) {
		  Insets insets = new Insets(0, 0, 0, 0);
		    GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty,
		        anchor, fill, insets, 0, 0);
		    container.add(component, gbc);
	}
	 
	 /**
 	 * Sets the all three sizes.
 	 *
 	 * @param component the component
 	 * @param dimension the dimension
 	 */
 	public static void setAllThreeSizes( Component component, Dimension dimension){
		 component.setSize(dimension);
		 component.setPreferredSize(dimension);
		 component.setMinimumSize(dimension);
	 }
}
