package bc.bcCommunicator.Client.Views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;

import bc.bcCommunicator.Client.WindowNames;


public class ScrollablePaneView extends JPanel implements IScrollblePaneView  {
	public static void main(String[] a){
	      JFrame frame = new JFrame("Test");
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          //frame.add(new ScrollablePaneView("asd"));
          TalkWindowView talkview = new TalkWindowView(null, null);
          talkview.setVisible(true);
          talkview.addLetterView(new LetterView("Username", "Lorem ipsum sasdghjkdsafbakfdbakwifsdbakjiodfsuhbjwkqoidsbjhnewsdcz\niuhjwqdasuihjbwqadsh\nhbjewdasgebwqajdszxyg\nck of cocks","1991-02-02" , true));
          frame.add(talkview );
          frame.pack();
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
	}
	
	
    private JPanel mainList;
    private JPanel letterListPanel;

    public ScrollablePaneView(String rootWindowName) {
    	setPreferredSize(new Dimension(400, 500));
    	setLayout(new BorderLayout());
    	
        letterListPanel = new JPanel();
        letterListPanel.setName(WindowNames.LETTERS_LIST_PREFIX+rootWindowName);
        letterListPanel.setLayout(new BorderLayout());
        letterListPanel.setPreferredSize(new Dimension(800, 800));
        
        mainList = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainList.add(new JPanel(), gbc);

        letterListPanel.add(new JScrollPane(mainList));

    
        add(letterListPanel);
        letterListPanel.setVisible(true);
//        for( int i = 0; i < 7; i++){
//        	LetterView letterView = new LetterView("Usernme", "hello, this is bartek", "1991-29-22", true);
//        	addLetter(letterView);
//        }
        setVisible(true);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

	@Override
	public void addLetter(ILetterView letterView) {
		//JLabel letterView = new JLabel();
		//letterView.setText("RRERRRR");
        letterView.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        letterView.setVisible(true);
        //panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainList.add(letterView, gbc, 0);

        letterListPanel.validate();
        letterListPanel.repaint();
	}

}



//
//private JPanel mainList;
//
//public static void main(String[] args){
//	new ScrollablePaneView();
//}
//
//public ScrollablePaneView() {
//    try {
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//    } catch (Exception ex) {
//    }
//
//    JFrame frame = new JFrame("Test");
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.add(new TestPane());
//    frame.setSize(400, 400);
//    frame.setLocationRelativeTo(null);
//    frame.setVisible(true);
//}