package bc.bcCommunicator.Client.Views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import bc.bcCommunicator.Client.WindowNames;

// TODO: Auto-generated Javadoc
/**
 * The Class LetterView.
 */
public class LetterView extends ILetterView{
	
	/** The sender name. */
	private JLabel senderName;
	
	/** The letter text. */
	private JTextArea letterText;
	
	/** The send date. */
	private JLabel sendDate;
	
	/** The letter color. */
	private Color letterColor;
	
	/**
	 * Instantiates a new letter view.
	 *
	 * @param username the username
	 * @param inLetterText the in letter text
	 * @param dateText the date text
	 * @param alignLeft the align left
	 */
	public LetterView(String username, String inLetterText, String dateText, boolean alignLeft) {
		setLayout(new GridBagLayout());

		
		setBorder(new MatteBorder(2, 2, 2, 2, Color.CYAN));
		
		letterColor = Color.CYAN;
		if( alignLeft == true){
			letterColor = Color.RED;
		}
		
		Border paddingBorder = BorderFactory.createEmptyBorder(2,2,2,2);
		
		addSenderName(username, paddingBorder);
		
		String letterTextName = LetterViewNamesCreator.createLetterViewLetterTextName( inLetterText);
		addLetterText(letterTextName, inLetterText, paddingBorder);
		
		addSendDate(dateText, paddingBorder);
		
		

		
		setVisible(true);
		System.out.println(letterText.getPreferredSize());
		Dimension d = getPreferredSize();
		d.width = 300;
		d.height = (int) (sendDate.getPreferredSize().getHeight() + letterText.getPreferredSize().getHeight()+5);
		setPreferredSize(d); 
	}

	/**
	 * Adds the send date.
	 *
	 * @param dateText the date text
	 * @param paddingBorder the padding border
	 */
	private void addSendDate(String dateText, Border paddingBorder) {
		sendDate = new JLabel();
		sendDate.setName(WindowNames.LETTER_SEND_DATE_NAME);
		sendDate.setText(dateText);
		sendDate.setVisible(true);
		//sendDate.setHorizontalAlignment(aligment);
		sendDate.setBorder(BorderFactory.createCompoundBorder(paddingBorder,
				new MatteBorder(1, 1, 1, 1, letterColor)));
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		add(sendDate, c);
	}

	/**
	 * Adds the letter text.
	 *
	 * @param componentName the component name
	 * @param inLetterText the in letter text
	 * @param paddingBorder the padding border
	 */
	private void addLetterText(String componentName, String inLetterText,  Border paddingBorder) {
		letterText = new JTextArea();
		letterText.setName(componentName);
		letterText.setText(inLetterText);
		letterText.setVisible(true);
		letterText.setEditable(true);
		
		//letterText.setHorizontalAlignment(aligment);
		letterText.setBorder(BorderFactory.createCompoundBorder(paddingBorder,
				new MatteBorder(1, 1, 1, 1, letterColor)));
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.PAGE_END;
		
		add(letterText, c);
		
	}

	/**
	 * Adds the sender name.
	 *
	 * @param username the username
	 * @param paddingBorder the padding border
	 */
	private void addSenderName(String username, Border paddingBorder) {
		senderName = new JLabel();
		senderName.setName(WindowNames.LETTER_SENDER_NAME);
		senderName.setText(username);
		senderName.setVisible(true);
		//senderName.setHorizontalAlignment(aligment);
		senderName.setBorder(BorderFactory.createCompoundBorder(paddingBorder,
				new MatteBorder(1, 1, 1, 1, letterColor)));
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		
		add(senderName, c);
	}

}
