package prego_CSCI201_Assignment2;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SelectPlayersPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private GridBagLayout gbl;
		private JLabel selectAmountOfPlayersLabel;
		private ButtonGroup selectAmountOfPlayersButtonGroup;
			private JRadioButton radioButton2, radioButton3, radioButton4;
		private JButton confirmButton;
		
		public JRadioButton getRadioButton2() {
			return radioButton2;
		}
		public JRadioButton getRadioButton3() {
			return radioButton3;
		}
		public JRadioButton getRadioButton4() {
			return radioButton4;
		}
		public JButton getConfirmButton() {
			return confirmButton;
		}

	SelectPlayersPanel(){
		initializeComponents();
		createGUI();
		addEvents();
	}
	private void initializeComponents(){
		gbl = new GridBagLayout();
			//Upper label
			selectAmountOfPlayersLabel = new JLabel("Select the number of Players");
			selectAmountOfPlayersLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
			//radioButtons
			selectAmountOfPlayersButtonGroup = new ButtonGroup();
				radioButton2 = new JRadioButton("2");
				radioButton3 = new JRadioButton("3");
				radioButton4 = new JRadioButton("4");
			//ConfirmButton
				confirmButton = new JButton("Confirm");
				confirmButton.setEnabled(false);
	}
	private void createGUI(){
		super.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
			//adding label
			gbc.gridwidth = 3;
			gbc.gridheight = 1;
			gbc.anchor = GridBagConstraints.NORTH;
			gbc.gridx = 0;
			gbc.gridy = 0;
			super.add(selectAmountOfPlayersLabel, gbc);
			//adding radio button to buttonGroup
			selectAmountOfPlayersButtonGroup.add(radioButton2);
			selectAmountOfPlayersButtonGroup.add(radioButton3);
			selectAmountOfPlayersButtonGroup.add(radioButton4);
				//adding ButtonGroup to layout
				gbc.gridy = 1;
				gbc.gridx = 0;
				gbc.gridwidth = 1;
				super.add(radioButton2, gbc);
				gbc.gridx = 1;
				super.add(radioButton3, gbc);
				gbc.gridx = 2;
				super.add(radioButton4, gbc);
			//adding confirmation Button
			gbc.anchor = GridBagConstraints.PAGE_END;
			super.add(confirmButton, gbc);
	}
	
	private void addEvents() {
		radioButton2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmButton.setEnabled(true);
			}
		});
		radioButton3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmButton.setEnabled(true);
			}
		});
		radioButton4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmButton.setEnabled(true);
			}
		});
	}
}















