package sorryclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Stack;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import game.*;

/*
 * GamePanel
 * Panel to hold the graphical game
 * */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1593344194657112518L;
	
	//A grid to hold all the tiles in the game
	private final static int boardSize = 16;
	private final TilePanel[][] tileGrid;

	//Dialog for LeaderBoard
	LeaderBoardDialog leaderBoardDialog = new LeaderBoardDialog();
	
	//The card button for the game
	private final JButton cardButton;
	private final JLabel cardLabel;
	
	//The game manager that runs the actual logic
	private final GameManager mGameManager;
	
	//The way to exit the game menu
	private final ActionListener mQuitAction;

	private Font font;
	private ImageIcon imageIcon;
	private Image backgroundImage;

	//tiles
	private ImageIcon greenTileImage, redTileImage, blueTileImage, yellowTileImage, sorryImage, greyTileImage;
	private ImageIcon greenSlideImage, redSlideImage, blueSlideImage, yellowSlideImage;
	private ImageIcon greenPanelImage, redPanelImage, bluePanelImage, yellowPanelImage;
	private ImageIcon greenPawnImage, redPawnImage, bluePawnImage, yellowPawnImage;
	private ImageIcon cardButtonImage;
	{
		//Create and set-up the card button
		cardButton = new JButton(){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				int x = this.getWidth();
				int y = this.getHeight();
				g.drawImage(cardButtonImage.getImage(),0,0,x,y,this);
			}
		};
		cardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mGameManager.drawCard();
				redraw();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		int x = this.getWidth()/4;
		int y = this.getHeight()/4;
		g.drawImage(sorryImage.getImage(), x, y-5,x*2,(y*4)/5,this);
	}
	
	public GamePanel(ActionListener inQuitAction, GameManager inGameManager){
		//setting fonts and background
		try{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/kenvector_future.ttf"));

			cardButtonImage = new ImageIcon("images/cards/cardBack_red.png");
			imageIcon = new ImageIcon("images/checkboxes/grey_box.png");
			sorryImage = new ImageIcon("images/sorry.png");
			greyTileImage = new ImageIcon("images/tiles/grey_tile.png");
			blueTileImage = new ImageIcon("images/tiles/blue_tile.png");
			redTileImage = new ImageIcon("images/tiles/red_tile.png");
			greenTileImage = new ImageIcon("images/tiles/green_tile.png");
			yellowTileImage = new ImageIcon("images/tiles/yellow_tile.png");

			yellowSlideImage = new ImageIcon("images/sliders/yellow_slide.png");
			redSlideImage = new ImageIcon("images/sliders/red_slide.png");
			blueSlideImage = new ImageIcon("images/sliders/blue_slide.png");
			greenSlideImage = new ImageIcon("images/sliders/green_slide.png");

			yellowPanelImage = new ImageIcon("images/panels/yellow_panel.png");
			redPanelImage = new ImageIcon("images/panels/red_panel.png");
			bluePanelImage = new ImageIcon("images/panels/blue_panel.png");
			greenPanelImage = new ImageIcon("images/panels/green_panel.png");

			yellowPawnImage = new ImageIcon("images/pawns/yellow_pawn.png");
			redPawnImage = new ImageIcon("images/pawns/red_pawn.png");
			bluePawnImage = new ImageIcon("images/pawns/blue_pawn.png");
			greenPawnImage = new ImageIcon("images/pawns/green_pawn.png");

		}catch(IOException |FontFormatException ioe){
			System.out.print(ioe.toString() + " in GamePanel constructor\n");
			ioe.printStackTrace();
		}

		//testing
		/*for(int i = 0; i < 10; ++i){
			NewScoreDialog nsd = new NewScoreDialog(i);
			nsd.setVisible(true);
		}*/

		//Cards label
		cardLabel = new JLabel("Deck");
		cardLabel.setFont(font.deriveFont(Font.PLAIN, 12f));

		mQuitAction = inQuitAction;
		mGameManager = inGameManager;
		
		//Create the GUI to be a grid for all the tiles
		setLayout(new GridLayout(boardSize,boardSize));
		tileGrid = new TilePanel[boardSize][boardSize];
		
		//Create each grid square
		//Either give it a Start/Home label panel, or a TilePanel
		for(int y = 0; y < boardSize; ++y) {
			for(int x = 0; x < boardSize; ++x) {
				if(x == 4 && y == 2) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.YELLOW));}
				else if (x == 2 && y == 7) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.YELLOW));}
				else if(x == 13 && y == 4) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.GREEN));}
				else if(x == 8 && y == 2) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.GREEN));}
				else if(x == 11 && y == 13) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.RED));}
				else if(x == 13 && y == 8) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.RED));}
				else if(x == 2 && y == 11) {tileGrid[x][y] = new StartLabelPanel(GameHelpers.getIndexFromColor(Color.BLUE));}
				else if(x == 7 && y == 13) {tileGrid[x][y] = new HomeLabelPanel(GameHelpers.getIndexFromColor(Color.BLUE));}
				else {
					tileGrid[x][y] = new TilePanel(mGameManager.getTile(x,y));
					tileGrid[x][y].setOpaque(false);
				}
				add(tileGrid[x][y]);
			}
		}
		
		//Set in the card
		TilePanel cardLabelTile = tileGrid[boardSize/2-1][boardSize/2-1];
		cardLabelTile.setLayout(new GridLayout(1,1));
		cardLabelTile.add(cardLabel);
		
		TilePanel cardButtonTile = tileGrid[boardSize/2][boardSize/2-1];
		cardButtonTile.setLayout(new GridLayout(1,1));
		cardButtonTile.add(cardButton);

		//This is used to make sure the GameManager can redraw the GUI
		inGameManager.setGamePanel(this);
		
		redraw();
	}
	
	public void redraw() {
		for(TilePanel row[] : tileGrid) {
			for(TilePanel tp : row) {
				tp.update();
			}
		}
		revalidate();
		repaint();
	}

	//Each tile is a square in the grid, it can be null to hold a blank square
	class TilePanel extends JPanel {
		private static final long serialVersionUID = -9071191204545371340L;
		
		private final Tile mTile;
		private final Stack<Component> components;
		
		private JPanel pawn = new JPanel();
		private boolean pawnDisplayed = false;

		private ImageIcon img;
		private JLabel label;

		public TilePanel getMe(){return this;}

		TilePanel(Tile tile) {
			mTile = tile;
			img = null;
			//Used to keep track what component should be displayed
			components = new Stack<Component>();
			this.setOpaque(false);

			//If we are a meaningful tile in the game
			if(mTile != null) {
				label = new JLabel();
				label.setFont(font.deriveFont(Font.PLAIN, 10));
				if(mTile.getColor() == Color.BLACK){
					img = greyTileImage;
					if(mTile.doesSlide()){
						img = greenTileImage;
					}
				}else if(mTile.getColor() == Color.BLUE){
					img = blueTileImage;
					if(mTile.doesSlide()){
						img = blueSlideImage;
					}
					if(mTile.isStart()){
						img = bluePanelImage;
						label.setText("Start");
					}
					if(mTile.isHome()){
						img = bluePanelImage;
						label.setText("Home");
					}
				}else if(mTile.getColor() == Color.GREEN){
					img = greenTileImage;
					if(mTile.doesSlide()){
						img = greenSlideImage;
					}
					if(mTile.isStart()){
						img = greenPanelImage;
						label.setText("Start");
					}
					if(mTile.isHome()){
						img = greenPanelImage;
						label.setText("Home");
					}
				}else if(mTile.getColor() == Color.YELLOW){
					img = yellowTileImage;
					if(mTile.doesSlide()){
						img = yellowSlideImage;
					}
					if(mTile.isStart()){
						img = yellowPanelImage;
						label.setText("Start");
					}
					if(mTile.isHome()){
						img = yellowPanelImage;
						label.setText("Home");
					}
				}else if(mTile.getColor() == Color.RED){
					img = redTileImage;
					if(mTile.doesSlide()){
						img = redSlideImage;
					}
					if(mTile.isStart()){
						img = redPanelImage;
						label.setText("Start");
					}
					if(mTile.isHome()){
						img = redPanelImage;
						label.setText("Home");
					}
				}
				components.push(label);
				//If the tile is clicked by the user...
				addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent me) {
						//Update this in the action manager
						mGameManager.tileClicked(mTile, mGameManager.getMainPlayer());
					}
				});
			}
		}

		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			if(img != null){
				g.drawImage(img.getImage(),0,0,this.getWidth(),this.getHeight(), this);
			}
		}

		//Update the tile based on its properties
		protected void update() {
			if(mTile == null) return;
			if(mTile.isOccupied() && !pawnDisplayed) {
				pawnDisplayed = true;
				components.push(pawn);
			}
			if(mTile.isOccupied() && pawnDisplayed) {
				pawn.removeAll();
				//pawn.setBackground(mTile.getPawnColor());
				ImageIcon img = new ImageIcon();
				if(mTile.getPawnColor() == Color.RED){
					img = redPawnImage;
				}else if(mTile.getPawnColor() == Color.YELLOW){
					img = yellowPawnImage;
				}else if(mTile.getPawnColor() == Color.BLUE){
					img = bluePawnImage;
				}else if(mTile.getPawnColor() == Color.GREEN){
					img = greenPawnImage;
				}
				img = new ImageIcon(img.getImage().getScaledInstance(20/*tileGrid[0][0].getWidth()*/, 12/*tileGrid[0][0].getHeight()*/, Image.SCALE_SMOOTH));
				pawn.add(new JLabel(img));
			}
			if(!mTile.isOccupied() && pawnDisplayed) {
				pawnDisplayed = false;
				components.pop();
			}
			removeAll();
			if(!components.isEmpty())add(components.peek());
		}
	}
	
	//Used for the start counter display
	class StartLabelPanel extends TilePanel{
		private static final long serialVersionUID = -9166979703140637366L;
		private final int mPlayerNum;
		JLabel mLabel;
		{
			mLabel = new JLabel();
			mLabel.setFont(font.deriveFont(Font.PLAIN, 12f));
			add(mLabel);
		}
		StartLabelPanel(int numPlayer) {
			super(null);
			mPlayerNum = numPlayer;
		}
		@Override
		protected void update() {
			mLabel.setText(mGameManager.getPlayerStartCount(mPlayerNum));
		}
	}
	
	//Used for the home counter display
	class HomeLabelPanel extends TilePanel{
		private static final long serialVersionUID = -9166979703540637366L;
		private final int mPlayerNum;
		JLabel mLabel;
		{
			mLabel = new JLabel();
			mLabel.setFont(font.deriveFont(Font.PLAIN, 12f));
			add(mLabel);
		}
		HomeLabelPanel(int numPlayer) {
			super(null);
			mPlayerNum = numPlayer;
		}
		@Override
		protected void update() {
			mLabel.setText(mGameManager.getPlayerHomeCount(mPlayerNum));
		}
	}

	public void endGame(String winnerName) {
		JOptionPane.showMessageDialog(
				null, 
				mGameManager.getWinner() + " player won!", 
				"Sorry!", 
				JOptionPane.NO_OPTION
		);


		int pawnsInHome, pawnsInStart, pawnsInBoard;
		pawnsInBoard = pawnsInHome = pawnsInStart = 0;
		for(Pawn p: mGameManager.getMainPlayer().getPawns()) {
			if(p.getCurrentTile().isHome()){
				pawnsInHome += 5;
			}
		}

		Player[] players = mGameManager.getPlayers();
		for(int i = 0; i < players.length; ++i){
			if(players[i] != mGameManager.getMainPlayer()){
				for(Pawn p: players[i].getPawns()) {
					if(p.getCurrentTile().isStart()){
						pawnsInHome += 1;
					}
					if(!p.getCurrentTile().isHome()){
						pawnsInBoard += 3;
					}
				}
			}
		}

		//Dialog will pop up here
		System.out.println("Score! = " + new Integer(pawnsInBoard + pawnsInHome + pawnsInStart));
		NewScoreDialog newScoreDialog = new NewScoreDialog(pawnsInBoard + pawnsInHome + pawnsInStart);
		newScoreDialog.setVisible(true);
		//Quit out if over
		JButton exit = new JButton("");
		exit.addActionListener(mQuitAction);
		exit.doClick();
	}

	class NewScoreDialog extends JDialog{
		public NewScoreDialog getMe(){return this;}
		NewScoreDialog(int score){
			super();
			setLocationRelativeTo(getParent());
			JPanel centerPanel = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();

			this.setSize(300,80);
			this.setResizable(false);
			gbc.gridwidth = 3;
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.weightx = 2;
			JLabel scoreLabel = new JLabel("Your score:" + Integer.toString(score));
			scoreLabel.setFont(font.deriveFont(Font.BOLD, 12f));
			centerPanel.add(scoreLabel,gbc);


			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 0;
			JLabel nameLabel = new JLabel("   Name: ");
			nameLabel.setFont(font.deriveFont(Font.BOLD, 12f));
			centerPanel.add(nameLabel, gbc);

			gbc.gridx = 1;
			gbc.weightx = 2;
			TextField nameInputArea = new TextField(15);
			centerPanel.add(nameInputArea, gbc);

			gbc.gridx = 1;
			gbc.gridy = 2;
			JButton confirmButton = new JButton("Submit!");
			centerPanel.add(confirmButton, gbc);
			confirmButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!nameInputArea.getText().equals("")) {
						ObjectInputStream ois;
						ObjectOutputStream oos;
						JTable leaderBoardTable;
						try {
							ois = new ObjectInputStream(new FileInputStream("src/leaderBoard.txt"));
							leaderBoardTable = (JTable) ois.readObject();
							ois.close();
							DefaultTableModel tableModel = (DefaultTableModel) leaderBoardTable.getModel();
							Object[] newRow = {nameInputArea.getText(), score};
							int i;
							for(i = 0; i < tableModel.getRowCount(); ++i){
								if(score >= (int)leaderBoardTable.getValueAt(i,1)){
									tableModel.insertRow(i, newRow);
									break;
								}
							}
							//if there is nothing
							if(tableModel.getRowCount() == 0){
								System.out.print("0\n");
								tableModel.addRow(newRow);
							}
							if(i == tableModel.getRowCount()){
								tableModel.addRow(newRow);
							}
							//overriding file
							oos = new ObjectOutputStream(new FileOutputStream("src/leaderBoard.txt"));
							oos.writeObject(leaderBoardTable);
							oos.close();
						} catch (IOException ioe) {
							System.out.print(ioe.toString() + " in LeaderBoard constructor\n" + ioe.getMessage());
							ioe.printStackTrace();
						} catch (ClassNotFoundException cnfe) {
							System.out.print(cnfe.toString() + " in LeaderBoard constructor\n" + cnfe.getMessage());
							cnfe.printStackTrace();
						} finally {
							LeaderBoardDialog leaderBoardDialog = new LeaderBoardDialog();
							leaderBoardDialog.setVisible(true);
							nameInputArea.setText("");
							getMe().setVisible(false);
						}
					}
				}
			});

			add(centerPanel, BorderLayout.CENTER);
			}
		}
}
