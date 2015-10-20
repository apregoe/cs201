package sorryclient;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/*
 * MainMenu
 * Menu to hold the start button
 * */
public class MainMenu extends JPanel{
	private static final long serialVersionUID = 3609831945869059312L;
	private Font font;
	private final JButton start;

	private ImageIcon background;
	private Image backgroundImage;
	private ImageIcon iconLogo;
	private ImageIcon startButtonImage;
	private ImageIcon clickedStartButtonImage;

	public MainMenu(ActionListener startAction){
		//setting fonts and background
		startButtonImage = clickedStartButtonImage = null;
		try{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/kenvector_future.ttf"));
			iconLogo = new ImageIcon("images/sorry.png");
			ge.registerFont(font);

			startButtonImage = new ImageIcon("images/buttons/grey_button01.png");
			clickedStartButtonImage =  new ImageIcon("images/buttons/grey_button00.png");
			background = new ImageIcon("images/panels/grey_panel.png");
			backgroundImage = background.getImage();
		}catch(IOException|FontFormatException ioe){
			System.out.print(ioe.toString() + " in MainMenu constructor\n");
			ioe.printStackTrace();
		}

		start = new JButton("                      "){
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				if((startButtonImage != null) && (clickedStartButtonImage != null)) {
					int x = this.getWidth();
					int y = this.getHeight();
					g.drawImage(startButtonImage.getImage(), 0, 0, x, y, this);
					this.setForeground(Color.black);
					g.drawString("START!", x/4, 20);
				}
			}
		};
		start.setMaximumSize(new Dimension(150,50));
		start.setHorizontalTextPosition(SwingConstants.CENTER);
		start.setVerticalTextPosition(SwingConstants.CENTER);
		start.addActionListener(startAction);
		start.setFont(font.deriveFont(Font.BOLD, 20f));

		JLabel titleLabel = new JLabel();
		titleLabel.setIcon(iconLogo);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(0,0,100,0);
		gbc.gridy = 1;
		add(titleLabel, gbc);
		gbc.insets = new Insets(0,0,0,0);
		gbc.gridy = 2;
		add(start,gbc);
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
