package sorryclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*
 * NumPlayerSelector
 * Menu to select a number of players 2-3
 * */
public class NumPlayerSelector extends JPanel {
	private static final long serialVersionUID = -4510696620583889943L;
	
	//The options for number of player selection
	private int selection = -1;
	private final int numOptions = 3;
	private final JRadioButton[] optionButtons;
	private final ButtonGroup buttonGroup;
	
	private JButton confirmButton;

	private Font font;
	private ImageIcon background;
	private Image backgroundImage;
	private ImageIcon confirmButtonImage, disabledConfirmButtonImage;
	private final String selectNumPlayerString = "Select the number of players";
	
	//The spacing of the border
	private static final Insets spacing = new Insets(20,20,20,20);

	//radiobuttons images
	private ImageIcon untoggledImage, toggledImage;

	public int getNumberOfPlayers() {
		return selection;
	}
	
	public NumPlayerSelector(ActionListener confirmAction){
		untoggledImage = toggledImage = null;
		//setting fonts and background
		try{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/kenvector_future.ttf"));

			confirmButtonImage = new ImageIcon("images/buttons/grey_button00.png");
			disabledConfirmButtonImage = new ImageIcon("images/buttons/grey_button01.png");
			background = new ImageIcon("images/panels/grey_panel.png");
			backgroundImage = background.getImage();

			untoggledImage = new ImageIcon("images/checkboxes/grey_circle.png");
			toggledImage = new ImageIcon("images/checkboxes/grey_boxTick.png");
		}catch(IOException |FontFormatException ioe){
			System.out.print(ioe.toString() + " in NumPlayerSelector constructor\n");
			ioe.printStackTrace();
		}

		//set up the button so we can proceed
		confirmButton = new JButton("                 \n"){
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				if (confirmButtonImage != null)
				{
					if(this.isEnabled()) {
						int x = this.getWidth();
						int y = this.getHeight();
						g.drawImage(confirmButtonImage.getImage(), 0, 0, x, y, this);
						this.setForeground(Color.black);
						g.drawString("CONFIRM", 15, y/2 + 7);
					}else{
						int x = this.getWidth();
						int y = this.getHeight();
						g.drawImage(disabledConfirmButtonImage.getImage(), 0, 0, x, y, this);
						this.setForeground(Color.gray);
						g.drawString("CONFIRM", 15, y/2 + 7);
					}
				}
			}
		};
		confirmButton.addActionListener(confirmAction);
		confirmButton.setEnabled(false);
		confirmButton.setFont(font.deriveFont(Font.BOLD, 26f));
		
		//The top of the panel, the label
		JLabel selectPlayerLabel = new JLabel(selectNumPlayerString);
		selectPlayerLabel.setFont(font.deriveFont(Font.BOLD, 26f));
		
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.add(selectPlayerLabel);

		//The center panel to hold the button panel
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		//The button panel to hold the buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		buttonGroup = new ButtonGroup();
		optionButtons = new JRadioButton[numOptions];
		for(int i = 0; i < numOptions; ++i) {
			JPanel numPanel = new JPanel();
			numPanel.setOpaque(false);
			optionButtons[i] = new JRadioButton(){
				@Override
				protected void paintComponent(Graphics g){
					super.paintComponent(g);
					for(int i = 0; i < 3; ++i){
						if(optionButtons[i] != this){
							optionButtons[i].repaint();
							optionButtons[i].revalidate();
						}
					}

					if ((toggledImage != null) && (untoggledImage != null))
					{
						if(this.isSelected()) {
							int x = this.getWidth();
							int y = this.getHeight();
							g.drawImage(toggledImage.getImage(), 0, 0, x, y, this);
						}else{
							int x = this.getWidth();
							int y = this.getHeight();
							g.drawImage(untoggledImage.getImage(), 0, 0, x, y, this);
						}
					}
				}
			};
			optionButtons[i].setOpaque(false);
			optionButtons[i].setFont(font.deriveFont(Font.BOLD, 14f));
			final int buttonSelection = i+2;
			optionButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					selection = buttonSelection;
					confirmButton.setEnabled(true);
				}
			});
			buttonGroup.add(optionButtons[i]);
			numPanel.add(optionButtons[i]);
			JLabel indexLabel = new JLabel("" + (i+2));
			indexLabel.setFont(font.deriveFont(Font.BOLD, 20f));
			numPanel.add(indexLabel);
			
			buttonPanel.add(Box.createGlue());
			buttonPanel.add(numPanel);
			buttonPanel.add(Box.createGlue());
		}
		
		centerPanel.add(Box.createGlue());
		centerPanel.add(buttonPanel);
		centerPanel.add(Box.createGlue());
		
		//The bottom panel to hold the confirm button
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.X_AXIS));
		bottomPanel.setBorder(new EmptyBorder(spacing));
		bottomPanel.add(Box.createGlue());
		bottomPanel.add(confirmButton);
		
		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backgroundImage != null){
			int x = this.getWidth();
			int y = this.getHeight();
			g.drawImage(backgroundImage,0,0,x,y,this);
		}
	}
}
