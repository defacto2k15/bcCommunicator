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

public class TalkWindowView  extends JFrame implements ITalkWindow, WindowListener {
	JLabel talkUserUsernameLabel;
	JLabel talkUserConnectionStateLabel;
	JLabel letterStateLabel;
	JTextArea letterTextInputField;
	ScrollablePaneView letterList;
	JButton sendLetterButton;
	private ICommunicatorController controller;
	private Username username;
	private ITalkWindowViewClosingListener closingListener;
	
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

	@Override
	public void setConnectionState(UserConnectionState connected) {
		talkUserConnectionStateLabel.setText(connected.getStateDescription());
	}

	@Override
	public void setUsername(Username username) {
		talkUserUsernameLabel.setText("Talk with: "+username.getName());
	}

	@Override
	public void setLetterState(LetterState state) {
		letterStateLabel.setText(state.getMessage());
	}

	@Override
	public void addLetterView(ILetterView letterView) {
		letterList.addLetter(letterView);
	}
	
	 private static void addComponent(Container container, Component component, int gridx, int gridy,
		      int gridwidth, int gridheight, double weightx, double weighty, int anchor, int fill) {
		  Insets insets = new Insets(0, 0, 0, 0);
		    GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, weightx, weighty,
		        anchor, fill, insets, 0, 0);
		    container.add(component, gbc);
	}

	@Override
	public void emptyInputField() {
		letterTextInputField.setText("");
	}

	@Override
	public void setClosingListener(ITalkWindowViewClosingListener closingListener) {
		this.closingListener = closingListener; 
	}
	
	@Override
	public void requetsToBeActiveFrame() {
		requestFocus();
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		closingListener.windowIsClosing(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}


	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	
		
}
