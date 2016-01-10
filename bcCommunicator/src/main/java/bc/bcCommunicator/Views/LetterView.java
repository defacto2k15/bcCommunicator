package bc.bcCommunicator.Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import bc.bcCommunicator.WindowNames;
import bc.bcCommunicator.Model.Messages.Letter.Letter;

public class LetterView extends ILetterView{
	private JLabel senderName;
	private JTextArea letterText;
	private JLabel sendDate;
	
	public LetterView(String username, String inLetterText, String dateText, boolean alignLeft) {
		setLayout(new GridBagLayout());

		
		setBorder(new MatteBorder(2, 2, 2, 2, Color.CYAN));
		
		int aligment = SwingConstants.RIGHT;
		if( alignLeft == true){
			aligment = SwingConstants.LEFT;
		}
		
		Border paddingBorder = BorderFactory.createEmptyBorder(2,2,2,2);
		
		addSenderName(username, aligment, paddingBorder);
		
		String letterTextName = LetterViewNamesCreator.createLetterViewLetterTextName( inLetterText);
		addLetterText(letterTextName, inLetterText, aligment, paddingBorder);
		
		addSendDate(dateText, aligment, paddingBorder);
		
		

		
		setVisible(true);
		System.out.println(letterText.getPreferredSize());
		Dimension d = getPreferredSize();
		d.width = 300;
		d.height = (int) (sendDate.getPreferredSize().getHeight() + letterText.getPreferredSize().getHeight()+5);
		setPreferredSize(d); 
	}

	private void addSendDate(String dateText, int aligment, Border paddingBorder) {
		sendDate = new JLabel();
		sendDate.setName(WindowNames.LETTER_SEND_DATE_NAME);
		sendDate.setText(dateText);
		sendDate.setVisible(true);
		//sendDate.setHorizontalAlignment(aligment);
		sendDate.setBorder(BorderFactory.createCompoundBorder(paddingBorder,
				new MatteBorder(1, 1, 1, 1, Color.CYAN)));
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		add(sendDate, c);
	}

	private void addLetterText(String componentName, String inLetterText, int aligment, Border paddingBorder) {
		letterText = new JTextArea();
		letterText.setName(componentName);
		letterText.setText(inLetterText);
		letterText.setVisible(true);
		letterText.setEditable(true);
		
		//letterText.setHorizontalAlignment(aligment);
		letterText.setBorder(BorderFactory.createCompoundBorder(paddingBorder,
				new MatteBorder(1, 1, 1, 1, Color.CYAN)));
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 0.5;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.PAGE_END;
		
		add(letterText, c);
		
	}

	private void addSenderName(String username, int aligment, Border paddingBorder) {
		senderName = new JLabel();
		senderName.setName(WindowNames.LETTER_SENDER_NAME);
		senderName.setText(username);
		senderName.setVisible(true);
		//senderName.setHorizontalAlignment(aligment);
		senderName.setBorder(BorderFactory.createCompoundBorder(paddingBorder,
				new MatteBorder(1, 1, 1, 1, Color.CYAN)));
		
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		
		add(senderName, c);
	}

}