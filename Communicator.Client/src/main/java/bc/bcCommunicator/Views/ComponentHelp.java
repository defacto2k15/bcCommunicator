package bc.bcCommunicator.Views;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ComponentHelp {
	 public static void addComponent(Container container, Component component, int gridx, int gridy,
		      int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill) {
		  Insets insets = new Insets(0, 0, 0, 0);
		    GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty,
		        anchor, fill, insets, 0, 0);
		    container.add(component, gbc);
	}
	 
	 public static void setAllThreeSizes( Component component, Dimension dimension){
		 component.setSize(dimension);
		 component.setPreferredSize(dimension);
		 component.setMinimumSize(dimension);
	 }
}
