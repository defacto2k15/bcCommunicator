package bc.bcCommunicator.Client.Views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.MatteBorder;

import bc.bcCommunicator.Client.WindowNames;
import bc.bcCommunicator.Client.Controller.ICommunicatorController;
import bc.bcCommunicator.Model.BasicTypes.Username;

// TODO: Auto-generated Javadoc
/**
 * The Class TalkWindowView.
 */
public class TalkWindowView  extends JFrame implements ITalkWindow, WindowListener {
	
	/** The talk user username label. */
	JLabel talkUserUsernameLabel;
	
	/** The talk user connection state label. */
	JLabel talkUserConnectionStateLabel;
	
	/** The letter state label. */
	JLabel letterStateLabel;
	
	/** The letter text input field. */
	JTextArea letterTextInputField;
	
	/** The letter list. */
	ScrollablePaneView letterList;
	
	/** The send letter button. */
	JButton sendLetterButton;
	
	/** The controller. */
	private ICommunicatorController controller;
	
	/** The username. */
	private Username username;
	
	/** The closing listener. */
	private ITalkWindowViewClosingListener closingListener;
	
	/**
	 * Instantiates a new talk window view.
	 *
	 * @param username the username
	 * @param controller the controller
	 */
	public TalkWindowView( Username username, ICommunicatorController controller){
		super(WindowNames.TALK_WINDOW_PREFIX+username.getName());
		
		this.controller = controller;
		this.username = username;
		
		setName(WindowNames.TALK_WINDOW_PREFIX+username.getName());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 600);
		setLayout(new GridBagLayout());
		addWindowListener(this);
		
		talkUserUsernameLabel = new JLabel(WindowNames.TALK_USER_USERNAME_LABEL);
		talkUserUsernameLabel.setName(WindowNames.TALK_USER_USERNAME_LABEL);
		talkUserUsernameLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		addComponent(this, talkUserUsernameLabel, 0, 0, 3, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE  );
		add(talkUserUsernameLabel);
		
		talkUserConnectionStateLabel = new JLabel(WindowNames.TALK_USER_CONNECTION_STATE_LABEL);
		talkUserConnectionStateLabel.setName(WindowNames.TALK_USER_CONNECTION_STATE_LABEL);
		talkUserConnectionStateLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		addComponent(this, talkUserConnectionStateLabel, 0, 1, 3, 1, 0, 0,GridBagConstraints.CENTER, GridBagConstraints.NONE  );
		
		letterStateLabel = new JLabel( WindowNames.LETTER_STATE_LABEL);
		letterStateLabel.setName(WindowNames.LETTER_STATE_LABEL);
		letterStateLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		addComponent(this, letterStateLabel, 0, 2, 3, 1, 0, 0 ,GridBagConstraints.CENTER, GridBagConstraints.NONE  );
		
		letterList = new ScrollablePaneView(WindowNames.TALK_WINDOW_PREFIX+username.getName());
		letterList.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		addComponent(this, letterList, 0, 4, 3, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH  );
		
		letterTextInputField = new JTextArea();
		letterTextInputField.setName(WindowNames.LETTER_TEXT_INPUT_FIELD);
		letterTextInputField.setBorder(new MatteBorder(1, 1, 1, 1, Color.GRAY));
		
		Dimension inputDimension = new Dimension(400, 100);
		//letterTextInputField.setSize(inputDimension);;
		letterTextInputField.setMinimumSize(inputDimension);
		//letterTextInputField.setMaximumSize(inputDimension);
		//letterTextInputField.setText("T");
		//letterTextInputField.setPreferredSize(inputDimension);;
		letterTextInputField.setLineWrap(true);
		
		JScrollPane scroll = new JScrollPane ( letterTextInputField );
		scroll.setPreferredSize(inputDimension);
		scroll.setMinimumSize(inputDimension);
		scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		
		addComponent(this, scroll, 0, 6, 3, 1, 0, 0 ,GridBagConstraints.PAGE_END, GridBagConstraints.NONE  );
		
		sendLetterButton = new JButton();
		sendLetterButton.setName(WindowNames.SEND_LETTER_BUTTON);
		sendLetterButton.setText("SEND!!!");
		sendLetterButton.setPreferredSize( new Dimension(400, 50));
		sendLetterButton.setMinimumSize(new Dimension(400, 50));
		addComponent(this, sendLetterButton, 0, 7, 3, 1, 0, 0 ,GridBagConstraints.PAGE_END, GridBagConstraints.NONE  );
		
		sendLetterButton.addActionListener( new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = letterTextInputField.getText();
				if( text.isEmpty() == false ){
					try {
						controller.letterWasWritten(username, text);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		setVisible(true);
		
		setConnectionState(UserConnectionState.Connected);
		setLetterState(LetterState.No_Letter);
		setUsername(new Username("?"));
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.ITalkWindow#setConnectionState(bc.bcCommunicator.Client.Views.UserConnectionState)
	 */
	@Override
	public void setConnectionState(UserConnectionState connected) {
		talkUserConnectionStateLabel.setText(connected.getStateDescription());
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.ITalkWindow#setUsername(bc.bcCommunicator.Model.BasicTypes.Username)
	 */
	@Override
	public void setUsername(Username username) {
		talkUserUsernameLabel.setText("Talk with: "+username.getName());
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.ITalkWindow#setLetterState(bc.bcCommunicator.Client.Views.LetterState)
	 */
	@Override
	public void setLetterState(LetterState state) {
		letterStateLabel.setText(state.getMessage());
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.ITalkWindow#addLetterView(bc.bcCommunicator.Client.Views.ILetterView)
	 */
	@Override
	public void addLetterView(ILetterView letterView) {
		letterList.addLetter(letterView);
	}
	
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
 	private static void addComponent(Container container, Component component, int gridx, int gridy,
		      int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill) {
		  Insets insets = new Insets(0, 0, 0, 0);
		    GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty,
		        anchor, fill, insets, 0, 0);
		    container.add(component, gbc);
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.ITalkWindow#emptyInputField()
	 */
	@Override
	public void emptyInputField() {
		letterTextInputField.setText("");
	}

	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.ITalkWindow#setClosingListener(bc.bcCommunicator.Client.Views.ITalkWindowViewClosingListener)
	 */
	@Override
	public void setClosingListener(ITalkWindowViewClosingListener closingListener) {
		this.closingListener = closingListener; 
	}
	
	/* (non-Javadoc)
	 * @see bc.bcCommunicator.Client.Views.ITalkWindow#requetsToBeActiveFrame()
	 */
	@Override
	public void requetsToBeActiveFrame() {
		requestFocus();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		closingListener.windowIsClosing(this);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
	}


	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	
		
}
