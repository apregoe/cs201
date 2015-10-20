package prego_CSCI201_Assignment2;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private GridBagLayout startStateLayout;
		private JLabel sorryLabel;
        private JButton startButton;

	StartPanel(){
		initializeComponents();
		createGUI();
	}
	
	public JButton getButton(){return startButton;}

	private void initializeComponents(){
		startStateLayout = new GridBagLayout();
			sorryLabel = new JLabel("Sorry!");
			sorryLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
			startButton = new JButton("Start");
			startButton.setSize(100, 40);
			startButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
	}
	private void createGUI(){
		super.setLayout(startStateLayout);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			super.add(sorryLabel, gbc);
			gbc.gridy = 1;
			super.add(startButton, gbc);
	}
}
