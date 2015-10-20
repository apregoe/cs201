package prego_CSCI201_Assignment2;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectColorPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel selectColorLabel;
		private JButton redButton, blueButton, greenButton, yellowButton;
	private JButton confirmButton;
	private PawnColor selectedColor;
	public PawnColor getSelectedColor() {
		return selectedColor;
	}

	public JLabel getSelectColorLabel() {
		return selectColorLabel;
	}
	public void setSelectColorLabel(JLabel selectColorLabel) {
		this.selectColorLabel = selectColorLabel;
	}
	public JButton getRedButton() {
		return redButton;
	}
	public void setRedButton(JButton redButton) {
		this.redButton = redButton;
	}
	public JButton getBlueButton() {
		return blueButton;
	}
	public void setBlueButton(JButton blueButton) {
		this.blueButton = blueButton;
	}
	public JButton getGreenButton() {
		return greenButton;
	}
	public void setGreenButton(JButton greenButton) {
		this.greenButton = greenButton;
	}
	public JButton getYellowButton() {
		return yellowButton;
	}
	public void setYellowButton(JButton yellowButton) {
		this.yellowButton = yellowButton;
	}
	public JButton getConfirmButton() {
		return confirmButton;
	}
	public void setConfirmButton(JButton confirmButton) {
		this.confirmButton = confirmButton;
	}

	SelectColorPanel(){
		super();
		initializeComponents();
		createGUI();
		addEvents();
	}
	
	
	private void initializeComponents() {
		//used in addEvents()::ButtonPressed
		previousButtonPressed = null;
		selectColorLabel = new JLabel("Select your color");
			redButton = new JButton("RED");
			blueButton = new JButton("BLUE");
			greenButton = new JButton("GREEN");
			yellowButton = new JButton("YELLOW");
		confirmButton = new JButton("Confirm");
		confirmButton.setEnabled(false);

		selectedColor = null;
	}
	private void createGUI() {
		
		//setting fonts
		selectColorLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
		redButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		blueButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		greenButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		yellowButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		
		//setting buttons colors
		redButton.setBackground(new Color(0xFF0000));
		redButton.setOpaque(true);
		redButton.setBorderPainted(false);

		blueButton.setBackground(new Color(0x0708FF));
		blueButton.setOpaque(true);
		blueButton.setBorderPainted(false);

		greenButton.setBackground(new Color(0x00FF00));
		greenButton.setOpaque(true);
		greenButton.setBorderPainted(false);

		yellowButton.setBackground(new Color(0xFFF900));
		yellowButton.setOpaque(true);
		yellowButton.setBorderPainted(false);
		
		//Layout Management
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		
		//first row
		gbc.gridwidth = 4;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTH;
		super.add(selectColorLabel, gbc);

		
		
		gbc.insets = new Insets(10,10,10,10);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 100;
		gbc.gridwidth = 2;
		
		//second row, first column
		gbc.gridy = 1;
		gbc.gridx = 0;
		super.add(redButton, gbc);
		
		//second row, second column
		gbc.gridy = 1;
		gbc.gridx = 2;
		super.add(blueButton, gbc);
		
		//third row, first column
		gbc.gridy = 2;
		gbc.gridx = 0;
		super.add(greenButton, gbc);
		
		//third row, second column
		gbc.gridy = 2;
		gbc.gridx = 2;
		super.add(yellowButton, gbc);
		
		//forth row
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weighty = 1;
		gbc.weightx = 0.25;
		gbc.gridwidth = 1;
		
		gbc.gridx = 3;
		gbc.gridy = 3;
		super.add(confirmButton, gbc);
		
	}
	
	//this member will only be used in ButtonPressed.actionPerformed()
	JButton previousButtonPressed;
	private void addEvents() {
		class ButtonPressed implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(ae.getSource() instanceof JButton){
					JButton button = (JButton)ae.getSource();
					if(previousButtonPressed != null){
						previousButtonPressed.setForeground(Color.black);
					}
					button.setForeground(Color.gray);
					previousButtonPressed = button;
					confirmButton.setEnabled(true);
				}
			}
		}
		redButton.addActionListener(new ButtonPressed(){
			@Override
			public void actionPerformed(ActionEvent ae){
				super.actionPerformed(ae);
				selectedColor = PawnColor.RED;
			}
		});
		blueButton.addActionListener(new ButtonPressed(){
			@Override
			public void actionPerformed(ActionEvent ae){
				super.actionPerformed(ae);
				selectedColor = PawnColor.BLUE;
			}
		});
		greenButton.addActionListener(new ButtonPressed(){
			@Override
			public void actionPerformed(ActionEvent ae){
				super.actionPerformed(ae);
				selectedColor = PawnColor.GREEN;
			}
		});
		yellowButton.addActionListener(new ButtonPressed(){
			@Override
			public void actionPerformed(ActionEvent ae){
				super.actionPerformed(ae);
				selectedColor = PawnColor.YELLOW;
			}
		});
	}
}

















