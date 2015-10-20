package sorryclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

/*
 * The main window for Sorry!
 * */
public class SorryClientWindow extends JFrame{
	private static final long serialVersionUID = 5147395078473323173L;
	
	//Dimensions for the game
	private final static Dimension minSize = new Dimension(640,480);
	private final static Dimension maxSize = new Dimension(960,640);

	//menu member variables
	private JMenuBar jmb;
	private JMenuItem helpMenuItem;
	private JMenuItem topScoresItem;
	private JPopupMenu jpm;


	Font font;

	{ //Construct
		//setting font
		try{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/kenvector_future.ttf"));
			ge.registerFont(font);
		}catch(IOException|FontFormatException ioe){
			System.out.print(ioe.toString() + " in SorryClientConstructor constructor\n");
			ioe.printStackTrace();
		}

		//setting up the menu
		jmb = new JMenuBar();
		helpMenuItem = new JMenuItem("Help");
		helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		topScoresItem = new JMenuItem("Top Scores");
		topScoresItem.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.CTRL_MASK));

		helpMenuItem.setFont(font.deriveFont(Font.PLAIN, 10f));
		topScoresItem.setFont(font.deriveFont(Font.PLAIN, 10f));

		jmb.add(helpMenuItem);
		jmb.add(topScoresItem);
		this.setJMenuBar(jmb);


		topScoresItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeaderBoardDialog leaderBoardDialog = new LeaderBoardDialog();
				leaderBoardDialog.setVisible(true);
			}
		});

		helpMenuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showHelpDialog();
			}

			private void showHelpDialog() {
				class HelpDialog extends JDialog{
					HelpDialog(){
						JTextPane text = new JTextPane();
						text.setText("SORRY!\nSTARTING THE GAME \n" +
								"   PRESS 'START'\n" +
								"   SELECT THE NUMBER OF PLAYERS\n" +
								"   SELECT YOUR COLOR\n" +
								"PLAYING THE GAME\n" +
								"   DRAW A CARD\n" +
								"   SELECT A SQUARE THAT IS VALID\n" +
								"   SELECT A CARD FOR BOTS\n" +
								"   REPEAT\n");
						text.setFont(font.deriveFont(Font.PLAIN, 12f));
						text.setEditable(false);
						text.setBackground(new Color(245,241,222));
						add(text);
						this.setLocationRelativeTo(this.getParent());
						this.setMinimumSize(new Dimension(300,270));
						setSize(new Dimension(300, 270));
					}
				}

				HelpDialog helpDialog = new HelpDialog();
				helpDialog.setModal(true);
				helpDialog.setVisible(true);
			}
		});

		setTitle("Sorry!");
		setSize(minSize);
		setMinimumSize(minSize);
		setMaximumSize(maxSize);
		add(new ClientPanel());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		//Create a new SorryClient Window
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		    	new SorryClientWindow();
		    }
		});
	}
	
}
