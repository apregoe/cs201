package sorryclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*
 * ColorSelector
 * Menu to select Red,Blue,Green,Yellow
 * */
public class ColorSelector extends JPanel {

	private static final long serialVersionUID = 1900724217285760485L;
	
	//The options for color selection
	private Color selection;
	private final int numOptions = 4;
	private final ColorButton[] optionButtons;
	private final ImageIcon[] toggledButtonImages;
	private Font font;
	private final JButton confirmButton;
	
	private final static String selectColorString = "Select your color";
	
	private final static String[] colorNames = {"Red", "Blue", "Green", "Yellow"};
	private final static Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
	
	//For spacing to the borders
	private final static Insets spacing = new Insets(20,20,20,20);

	//background Image
	private Image backgroundImage;
	private ImageIcon[] buttonImages;

	//confirm button images
	private ImageIcon untoggledImage, toggledImage;

	public Color getPlayerColor() {
		return selection;
	}
	
	public ColorSelector(ActionListener confirmAction) {
		//setting fonts and background
		untoggledImage = toggledImage = null;
		buttonImages = new ImageIcon[numOptions];
		toggledButtonImages = new ImageIcon[numOptions];
		try{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/kenvector_future.ttf"));
			ge.registerFont(font);

			backgroundImage = (new ImageIcon("images/panels/grey_panel.png")).getImage();

			//button images
			buttonImages[0] = (new ImageIcon("images/buttons/red_button00.png"));
			buttonImages[1] = (new ImageIcon("images/buttons/blue_button00.png"));
			buttonImages[2] = (new ImageIcon("images/buttons/green_button00.png"));
			buttonImages[3] = (new ImageIcon("images/buttons/yellow_button00.png"));

			toggledButtonImages[0] = new ImageIcon("images/buttons/red_button01.png");
			toggledButtonImages[1] = new ImageIcon("images/buttons/blue_button01.png");
			toggledButtonImages[2] = new ImageIcon("images/buttons/green_button01.png");
			toggledButtonImages[3] = new ImageIcon("images/buttons/yellow_button01.png");

			//confirm button images
			untoggledImage = new ImageIcon("images/buttons/grey_button01.png");
			toggledImage = new ImageIcon("images/buttons/grey_button00.png");

		}catch(IOException|FontFormatException ioe){
			System.out.print(ioe.toString() + " in ColorSelector constructor\n");
			ioe.printStackTrace();
		}catch(NullPointerException npe){
			System.out.println(npe.toString() + " in ColorSelector constructor");
			npe.printStackTrace();
		}

		//set up the button so we can proceed
		confirmButton = new JButton("                  "){
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				if ((toggledImage != null) && (untoggledImage != null))
				{
					if(this.isEnabled()) {
						int x = this.getWidth();
						int y = this.getHeight();
						g.drawImage(toggledImage.getImage(), 0, 0, x, y, this);
						this.setForeground(Color.black);
						g.drawString("CONFIRM", 15, y/2 + 7);
					}else{
						int x = this.getWidth();
						int y = this.getHeight();
						g.drawImage(untoggledImage.getImage(), 0, 0, x, y, this);
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
		JLabel selectPlayerLabel = new JLabel(selectColorString);
		selectPlayerLabel.setFont(font.deriveFont(Font.BOLD, 28f));
		
		JPanel topPanel = new JPanel();
		topPanel.setOpaque(false);
		topPanel.add(selectPlayerLabel);
		
		//The middle of the panel, the color buttons
		JPanel centerPanel = new JPanel(new GridLayout(2,2,2,2));
		centerPanel.setOpaque(false);
		optionButtons = new ColorButton[numOptions];
		for(int i = 0; i < numOptions; ++i) {
			optionButtons[i] = new ColorButton(colorNames[i], buttonImages[i], toggledButtonImages[i]);
			optionButtons[i].setOpaque(false);
			optionButtons[i].setFont(font.deriveFont(Font.BOLD, 40f));
			optionButtons[i].setIcon(buttonImages[i]);
			optionButtons[i].setHorizontalTextPosition(JButton.CENTER);
			optionButtons[i].setVerticalTextPosition(JButton.CENTER);
			final int buttonSelection = i;
			optionButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					selection = colors[buttonSelection];
					for(JButton button : optionButtons) button.setEnabled(true);
					optionButtons[buttonSelection].setEnabled(false);
					confirmButton.setEnabled(true);
				}
			});
			centerPanel.add(optionButtons[i]);
		}
		centerPanel.setBorder(new EmptyBorder(spacing));
		
		//The bottom of the panel, the confirm button
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
	}
	class ColorButton extends JButton{
		ImageIcon img, img2;
		String name;
		ColorButton(String name, ImageIcon img, ImageIcon img2){
			super(name);
			this.name = name;
			this.img = img;
			this.img2 = img2;
			this.setMaximumSize(new Dimension(200,80));
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			for(int i = 0; i < numOptions; ++i){
				if(optionButtons[i] != this){
					optionButtons[i].repaint();
					optionButtons[i].revalidate();
				}
			}

			if (img != null)
			{
				if(this.isEnabled()) {
					int x = this.getWidth();
					int y = this.getHeight();
					g.drawImage(img.getImage(), 0, 0, x, y, this);
					this.setForeground(Color.black);
					g.drawString(name, x / 4, y / 2);
				}else{
					int x = this.getWidth();
					int y = this.getHeight();
					g.drawImage(img2.getImage(), 0, 0, x, y, this);
					this.setForeground(Color.gray);
					g.drawString(name, x / 4, y / 2);
				}
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (backgroundImage != null)
		{
			int x = this.getWidth();
			int y = this.getHeight();
			g.drawImage(backgroundImage,0,0,x,y,this);
		}
	}

}