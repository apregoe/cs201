package prego_CSCI201_Assignment2;

import javafx.util.Pair;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;


import javax.swing.*;

public class MainWindow  extends JFrame{
	//member variables
	//GUI
	private static final long serialVersionUID = 1L;
	private JPanel outerPanel;
		private StartPanel startStatePanel;
		private SelectPlayersPanel selectPlayersStatePanel;
		private SelectColorPanel selectColorPanel;
		private GamePanel gamePanel;
	private final MainWindow me = this;

	//important objects
	Vector<Pair<Integer, Integer>> redPawnsOnBoard, bluePawnsOnBoard, greenPawnsOnBoard, yellowPawnsOnBoard, currentPawnsOnBoard;
	int currentCard;
	int currentPlayerIndex;
	private Player[] allPlayers;
	private MyDeck myDeck;
	private int amountOfPlayers;

	//volatiles
	int split1;
	int split2;

	//for card 10
	boolean backwards;

	MainWindow(){
		super("Sorry!");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeComponents();
		createGUI();
		addEvents();
	}

	public void setAmountOfPlayers(int amountOfPlayers) {
		if((amountOfPlayers < 2) || (amountOfPlayers > 4)){
			throw new IndexOutOfBoundsException();
		}
		allPlayers = new Player[amountOfPlayers];
		this.amountOfPlayers = amountOfPlayers;
		currentPlayerIndex = 0;

	}

	private void initializeComponents(){
		amountOfPlayers = -1;
		currentPlayerIndex = -1;
		currentCard = -1;
		myDeck = new MyDeck();
		//initializing salute window (startStateLayout)
		outerPanel = new JPanel();
				startStatePanel = new StartPanel();
				selectPlayersStatePanel = new SelectPlayersPanel();
				selectColorPanel = new SelectColorPanel();
				gamePanel = new GamePanel();
	}
	private void createGUI(){
		super.setSize(640,480);
		super.setLocation(200, 200);
		outerPanel.setLayout(new CardLayout());
			startStatePanel.setName("Start Panel");
			outerPanel.add(startStatePanel, "Start Panel");
			selectPlayersStatePanel.setName("Select Players Panel");
			outerPanel.add(selectPlayersStatePanel, "Select Players Panel");
			selectColorPanel.setName("Select Color Panel");
			outerPanel.add(selectColorPanel, "Select Color Panel");
			gamePanel.setName("Game Panel");
			outerPanel.add(gamePanel, "Game Panel");
		add(outerPanel);
	}
	
	private void addEvents() {
		//Changing to second panel
		startStatePanel.getButton().addActionListener(new ChangeStateEvent(selectPlayersStatePanel.getName()));
		//Changing to third Panel
		selectPlayersStatePanel.getConfirmButton().addActionListener(new ChangeStateEvent(selectColorPanel.getName()) {
			@Override
			public void actionPerformed(ActionEvent ae) {
				super.actionPerformed(ae);
				if (selectPlayersStatePanel.getRadioButton2().isSelected()) {
					setAmountOfPlayers(2);
				} else if (selectPlayersStatePanel.getRadioButton3().isSelected()) {
					setAmountOfPlayers(3);
				} else if (selectPlayersStatePanel.getRadioButton4().isSelected()) {
					setAmountOfPlayers(4);
				}
			}
		});
		//changing to game panel
		selectColorPanel.getConfirmButton().addActionListener(new ChangeStateEvent(gamePanel.getName()) {
			@Override
			public void actionPerformed(ActionEvent ae) {
				super.actionPerformed(ae);
				allPlayers[0] = new Player(selectColorPanel.getSelectedColor());
				if (allPlayers.length == 2) {
					if (allPlayers[0].getMyColor() == PawnColor.BLUE) {
						allPlayers[1] = new Player(PawnColor.GREEN);
					} else if (allPlayers[0].getMyColor() == PawnColor.GREEN) {
						allPlayers[1] = new Player(PawnColor.BLUE);
					} else if (allPlayers[0].getMyColor() == PawnColor.YELLOW) {
						allPlayers[1] = new Player(PawnColor.RED);
					} else if (allPlayers[0].getMyColor() == PawnColor.RED) {
						allPlayers[1] = new Player(PawnColor.YELLOW);
					}
				} else if (allPlayers.length == 3) {
					if (allPlayers[0].getMyColor() == PawnColor.BLUE) {
						allPlayers[1] = new Player(PawnColor.YELLOW);
						allPlayers[2] = new Player(PawnColor.GREEN);
					} else if (allPlayers[0].getMyColor() == PawnColor.GREEN) {
						allPlayers[1] = new Player(PawnColor.RED);
						allPlayers[2] = new Player(PawnColor.BLUE);
					} else if (allPlayers[0].getMyColor() == PawnColor.YELLOW) {
						allPlayers[1] = new Player(PawnColor.GREEN);
						allPlayers[2] = new Player(PawnColor.RED);
					} else if (allPlayers[0].getMyColor() == PawnColor.RED) {
						allPlayers[1] = new Player(PawnColor.BLUE);
						allPlayers[2] = new Player(PawnColor.YELLOW);
					}
				} else if (allPlayers.length == 4) {
					if (allPlayers[0].getMyColor() == PawnColor.BLUE) {
						allPlayers[1] = new Player(PawnColor.YELLOW);
						allPlayers[2] = new Player(PawnColor.GREEN);
						allPlayers[3] = new Player(PawnColor.RED);
					} else if (allPlayers[0].getMyColor() == PawnColor.GREEN) {
						allPlayers[1] = new Player(PawnColor.RED);
						allPlayers[2] = new Player(PawnColor.BLUE);
						allPlayers[3] = new Player(PawnColor.YELLOW);
					} else if (allPlayers[0].getMyColor() == PawnColor.YELLOW) {
						allPlayers[1] = new Player(PawnColor.GREEN);
						allPlayers[2] = new Player(PawnColor.RED);
						allPlayers[3] = new Player(PawnColor.BLUE);
					} else if (allPlayers[0].getMyColor() == PawnColor.RED) {
						allPlayers[1] = new Player(PawnColor.BLUE);
						allPlayers[2] = new Player(PawnColor.YELLOW);
						allPlayers[3] = new Player(PawnColor.GREEN);
					}
				}
				//red Start
				if(allPlayers[0].getMyColor() == PawnColor.RED){
					gamePanel.getAllPanels()[14][11].addMouseListener(new StartLabelPressedEvent());
				}
				//yellow Start
				if(allPlayers[0].getMyColor() == PawnColor.YELLOW) {
					gamePanel.getAllPanels()[1][4].addMouseListener(new StartLabelPressedEvent());
				}
				//green Start
				if(allPlayers[0].getMyColor() == PawnColor.GREEN) {
					gamePanel.getAllPanels()[4][14].addMouseListener(new StartLabelPressedEvent());
				}
				//Blue Start
				if(allPlayers[0].getMyColor() == PawnColor.BLUE) {
					gamePanel.getAllPanels()[11][1].addMouseListener(new StartLabelPressedEvent());
				}
			}
		});
		//MouseEvent for each MyPanel
		gamePanel.getCardsButton().addActionListener(new CardButtonPressEvent());
		for (int i = 0; i < 16; ++i) {// rows
			for (int j = 0; j < 16; ++j) {// col
				if (isInBounds(i, j)) {
					gamePanel.getAllPanels()[i][j].addMouseListener(new MyPanelPressedEvent(i, j));
				}
			}
		}
	}
	class MyInt{
		MyInt(int i){value = i;}
		public int value;
	}
	/*
		i == row (y axis)
		j == col (x axis)
	 */
	//1 = clean
	//2 = sameType//never used
	//3 = different type
	//4 = begin of slide//never used
	//5 = invalid move
	//6 = you got home!
	//If lastItaration == 0, that means it is the last iteration
	private int next(MyInt i, MyInt j, PawnColor color, int lastIteration, int direction){
		if(direction == 1) {
			if ((i.value == 0) && (j.value >= 1)) {//top row
				if ((color == PawnColor.YELLOW) && (j.value == 2)) {//if j == 2
					i.value = i.value + 1;
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						return 5;
					} else {
						return 1;
					}
				} else {//any other situation, regarding the color
					if (j.value == 8) {
						if (lastIteration == 0) {
							j.value = 14;
						} else {
							j.value = 9;
						}
					} else if (j.value == 15) {//last column in 1=0
						if (lastIteration == 0) {
							if (color == PawnColor.GREEN) {
								i.value = 1;
							} else {
								i.value = 5;
							}
						} else {
							i.value = 1;
						}
					} else {
						j.value = j.value + 1;
					}
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						if (gamePanel.getAllPanels()[i.value][j.value].getMyPawn().getPawnColor() == color) {
							return 5;
						} else {
							return 3;
						}
					} else {
						return 1;
					}
				}
			} else if ((i.value == 15) && (j.value <= 14)) {//button row
				if ((color == PawnColor.RED) && (j.value == 13)) {//if j == 2
					i.value = i.value - 1;
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						return 5;
					} else {
						return 1;
					}
				} else {//any other situation, regarding the color
					if (j.value == 7) {
						if (lastIteration == 0) {
							j.value = 1;
						} else {
							j.value = 6;
						}
					} else if (j.value == 0) {
						if (lastIteration == 0) {
							if (color == PawnColor.BLUE) {
								i.value = 14;
							} else {
								i.value = 10;
							}
						} else {
							i.value = 14;
						}
					} else {
						j.value = j.value - 1;
					}
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						if (gamePanel.getAllPanels()[i.value][j.value].getMyPawn().getPawnColor() == color) {
							return 5;
						} else {
							return 3;
						}
					} else {
						return 1;
					}
				}
			} else if ((j.value == 0) && (i.value <= 14)) {//left column
				if ((color == PawnColor.BLUE) && (i.value == 13)) {
					j.value = j.value + 1;
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						return 5;
					} else {
						return 1;
					}
				} else {//any other situation, regarding the color
					if (i.value == 7) {
						if (lastIteration == 0) {
							i.value = 1;
						} else {
							i.value = 6;
						}
					} else if (i.value == 0) {
						if (lastIteration == 0) {
							if (color == PawnColor.YELLOW) {
								j.value = 1;
							} else {
								j.value = 5;
							}
						} else {
							j.value = 1;
						}
					} else {
						i.value = i.value - 1;
					}
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						if (gamePanel.getAllPanels()[i.value][j.value].getMyPawn().getPawnColor() == color) {
							return 5;
						} else {
							return 3;
						}
					} else {
						return 1;
					}
				}
			} else if ((j.value == 15) && (i.value >= 1)) {//right column
				if ((color == PawnColor.GREEN) && (i.value == 2)) {
					j.value = j.value - 1;
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						return 5;
					} else {
						return 1;
					}
				} else {//any other situation, regarding the color
					if (i.value == 15) {
						if (lastIteration == 0) {
							if (color == PawnColor.RED) {
								j.value = 14;
							} else {
								j.value = 10;
							}
						} else {
							j.value = 14;
						}
					} else if (i.value == 8) {
						if (lastIteration == 0) {
							i.value = 14;
						} else {
							i.value = 9;
						}
					} else {
						i.value = i.value + 1;
					}
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						if (gamePanel.getAllPanels()[i.value][j.value].getMyPawn().getPawnColor() == color) {
							return 5;
						} else {
							return 3;
						}
					} else {
						return 1;
					}
				}
			} else if ((i.value == 13) && ((j.value >= 1) && (j.value <= 5))) {//BLUE HOME line, it is assumed it will always be blue
				if (j.value <= 4) {
					j.value = j.value + 1;
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						return 5;
					} else {
						return 1;
					}
				} else if (j.value == 5) {
					return 6;
				}
			} else if ((i.value == 2) && ((j.value >= 10) && (j.value <= 14))) {//GREEN HOME line, it is assumed it will always be green
				if (j.value >= 11) {
					j.value = j.value - 1;
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						return 5;
					} else {
						return 1;
					}
				} else if (j.value == 10) {
					return 6;
				}
			} else if ((j.value == 2) && ((i.value >= 1) && (i.value <= 5))) {//YELLOW HOME line, it is assumed it will always be yellow
				if (i.value <= 4) {
					i.value = i.value + 1;
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						return 5;
					} else {
						return 1;
					}
				} else if (i.value == 5) {
					return 6;
				}
			} else if ((j.value == 13) && ((i.value >= 10) && (i.value <= 14))) {//RED HOME line, it is assumed it will always be red
				if (i.value >= 11) {
					i.value = i.value - 1;
					if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
						return 5;
					} else {
						return 1;
					}
				} else if (i.value == 10) {
					return 6;
				}
			}
		}else{//If I draw a 4
			//on borders
			if ((i.value == 0) && (j.value >= 1)) {//top row
				j.value = j.value - 1;
			} else if ((i.value == 15) && (j.value <= 14)) {//button row
				j.value = j.value + 1;
			} else if ((j.value == 0) && (i.value <= 14)) {//left column
				i.value = i.value + 1;
			} else if ((j.value == 15) && (i.value >= 1)) {//right column
				i.value = i.value - 1;
			}//on home
			else if ((i.value == 13) && ((j.value >= 1) && (j.value <= 5))) {//BLUE HOME line, it is assumed it will always be blue
				j.value = j.value - 1;
			} else if ((i.value == 2) && ((j.value >= 10) && (j.value <= 14))) {//GREEN HOME line, it is assumed it will always be green
				j.value = j.value + 1;
			} else if ((j.value == 2) && ((i.value >= 1) && (i.value <= 5))) {//YELLOW HOME line, it is assumed it will always be yellow
				i.value = i.value - 1;
			} else if ((j.value == 13) && ((i.value >= 10) && (i.value <= 14))) {//RED HOME line, it is assumed it will always be red
				i.value = i.value + 1;
			}
			if (gamePanel.getAllPanels()[i.value][j.value].hasPawn()) {
				if (gamePanel.getAllPanels()[i.value][j.value].getMyPawn().getPawnColor() == color) {
					return 5;
				} else {
					return 3;
				}
			} else {
				return 1;
			}
		}
		return 5;//never reached
	}

	private boolean move(MyPawn myPawn, int i, int j, int offset, boolean change) {//i == row ()
		if(offset == 0){
			return true;
		}
		MyInt newi = new MyInt(i);
		MyInt newj = new MyInt(j);
		int dir;
		if(offset >= 0){
			dir = 1;
		}else{
			dir = -1;
			offset = offset*(dir);
		}
		int current = 0;
		int next = 0;
		while(current < offset){
			next = next(newi, newj, myPawn.getPawnColor(), (offset - current - 1), dir);
			if(next == 6){
				break;
			}else if(next == 5){
				break;
			}
			++current;
		}
		if(next == 1){//clean
			if(change) {
				PawnColor c = myPawn.getPawnColor();
				gamePanel.getAllPanels()[i][j].remove();
				gamePanel.getAllPanels()[newi.value][newj.value].add(new MyPawn(c));
				revalidate();
				repaint();
			}
			return true;
		}else if(next == 3){
			if(change) {
				PawnColor c = myPawn.getPawnColor();
				increaseStart(gamePanel.getAllPanels()[i][j].getMyPawn().getPawnColor());
				gamePanel.getAllPanels()[i][j].remove();
				gamePanel.getAllPanels()[newi.value][newj.value].remove();
				MyPawn p = new MyPawn(c);
				gamePanel.getAllPanels()[newi.value][newj.value].add(p);
				revalidate();
				repaint();
			}
			return true;
		}else if(next == 5){
			return false;
		}else if (next == 6){
			if(change) {
				increaseHome(gamePanel.getAllPanels()[i][j].getMyPawn().getPawnColor());
				gamePanel.getAllPanels()[i][j].remove();
				revalidate();
				repaint();
				if(currentCard == 7 || current == 11){
					updatePlayerIndex();
					if(currentPlayerIndex == 0){
						gamePanel.getCardsButton().setEnabled(true);
					}
					nextTurn();
				}
			}
			return true;
		}

		return false;

	}

	private boolean isInBounds(int i, int j){
		if((i == 0) || (i == 15) || (j == 0) || (j ==15)){
			return true;
		}else{//for home lines
			if((j == 2) && ((i >= 1) && (i <= 5))){//yellow home line
				return true;
			}else if((j == 13) && ((i >= 10) && (i <= 14))){// red home line
				return true;
			}else if((i == 2) && ((j >= 10) && (j <= 14))){//green home line
				return true;
			}else if((i == 13) && ((j >= 1) && (j <= 5))){// blue home line
				return true;
			}else{
				return false;
			}
		}
	}
	private boolean insertNewToken(){
		return insertPawn(allPlayers[currentPlayerIndex]);
	}

	private void increaseStart(PawnColor c){
		if(c == PawnColor.RED){
			int n = Integer.parseInt(gamePanel.getRedStartlabel().getText());
			++n;
			gamePanel.getRedStartlabel().setText(Integer.toString(n));
		}else if(c == PawnColor.GREEN){
			int n = Integer.parseInt(gamePanel.getGreenStartLabel().getText());
			++n;
			gamePanel.getGreenStartLabel().setText(Integer.toString(n));
		}else if(c == PawnColor.BLUE){
			int n = Integer.parseInt(gamePanel.getBlueStartLabel().getText());
			++n;
			gamePanel.getBlueStartLabel().setText(Integer.toString(n));
		}else if(c == PawnColor.YELLOW){
			int n = Integer.parseInt(gamePanel.getYellowStartLabel().getText());
			++n;
			gamePanel.getYellowStartLabel().setText(Integer.toString(n));
		}
	}
	private void decreaseStart(PawnColor c){
		if(c == PawnColor.RED){
			int n = Integer.parseInt(gamePanel.getRedStartlabel().getText());
			--n;
			gamePanel.getRedStartlabel().setText(Integer.toString(n));
		}else if(c == PawnColor.GREEN){
			int n = Integer.parseInt(gamePanel.getGreenStartLabel().getText());
			--n;
			gamePanel.getGreenStartLabel().setText(Integer.toString(n));
		}else if(c == PawnColor.BLUE){
			int n = Integer.parseInt(gamePanel.getBlueStartLabel().getText());
			--n;
			gamePanel.getBlueStartLabel().setText(Integer.toString(n));
		}else if(c == PawnColor.YELLOW){
			int n = Integer.parseInt(gamePanel.getYellowStartLabel().getText());
			--n;
			gamePanel.getYellowStartLabel().setText(Integer.toString(n));
		}
	}

	private void increaseHome(PawnColor c){
		if(c == PawnColor.RED){
			int n = Integer.parseInt(gamePanel.getRedHomelabel().getText());
			++n;
			gamePanel.getRedHomelabel().setText(Integer.toString(n));
		}else if(c == PawnColor.GREEN){
			int n = Integer.parseInt(gamePanel.getGreenHomeLabel().getText());
			++n;
			gamePanel.getGreenHomeLabel().setText(Integer.toString(n));
		}else if(c == PawnColor.BLUE){
			int n = Integer.parseInt(gamePanel.getBlueHomeLabel().getText());
			++n;
			gamePanel.getBlueHomeLabel().setText(Integer.toString(n));
		}else if(c == PawnColor.YELLOW){
			int n = Integer.parseInt(gamePanel.getYellowHometLabel().getText());
			++n;
			gamePanel.getYellowHometLabel().setText(Integer.toString(n));
		}
	}

	public boolean insertPawn(Player player){
		if(player.getMyPawnsInStart().size() != 0) {
			if (player.getMyColor() == PawnColor.RED) {
				if(!gamePanel.getAllPanels()[15][10].hasPawn()){
					decreaseStart(PawnColor.RED);
					MyPawn pawn = player.getAPawn();
					gamePanel.getAllPanels()[15][10].add(pawn);
				}else{
					if(gamePanel.getAllPanels()[15][10].getMyPawn().getPawnColor() != PawnColor.RED){
						increaseStart(gamePanel.getAllPanels()[15][10].getMyPawn().getPawnColor());
						decreaseStart(PawnColor.RED);
						gamePanel.getAllPanels()[15][10].remove();
						MyPawn pawn = player.getAPawn();
						gamePanel.getAllPanels()[15][10].add(pawn);
						return true;
					}else{
						return false;

					}
				}
			} else if (player.getMyColor() == PawnColor.GREEN) {
				if(!gamePanel.getAllPanels()[5][15].hasPawn()){
					decreaseStart(PawnColor.GREEN);
					MyPawn pawn = player.getAPawn();
					gamePanel.getAllPanels()[5][15].add(pawn);
				}else{
					if(gamePanel.getAllPanels()[5][15].getMyPawn().getPawnColor() != PawnColor.GREEN){
						increaseStart(gamePanel.getAllPanels()[5][15].getMyPawn().getPawnColor());
						decreaseStart(PawnColor.GREEN);
						gamePanel.getAllPanels()[5][15].remove();
						MyPawn pawn = player.getAPawn();
						gamePanel.getAllPanels()[5][15].add(pawn);
						return true;
					}else{
						return false;

					}
				}
			} else if (player.getMyColor() == PawnColor.BLUE) {
				if(!gamePanel.getAllPanels()[10][0].hasPawn()){
					decreaseStart(PawnColor.BLUE);
					MyPawn pawn = player.getAPawn();
					gamePanel.getAllPanels()[10][0].add(pawn);
				}else{
					if(gamePanel.getAllPanels()[10][0].getMyPawn().getPawnColor() != PawnColor.BLUE){
						increaseStart(gamePanel.getAllPanels()[10][0].getMyPawn().getPawnColor());
						decreaseStart(PawnColor.BLUE);
						gamePanel.getAllPanels()[10][0].remove();
						MyPawn pawn = player.getAPawn();
						gamePanel.getAllPanels()[10][0].add(pawn);
						return true;
					}else{
						return false;

					}
				}
			} else if (player.getMyColor() == PawnColor.YELLOW) {
				if(!gamePanel.getAllPanels()[0][5].hasPawn()) {
					decreaseStart(PawnColor.YELLOW);
					MyPawn pawn = player.getAPawn();
					gamePanel.getAllPanels()[0][5].add(pawn);
				}else{
					if(gamePanel.getAllPanels()[0][5].getMyPawn().getPawnColor() != PawnColor.YELLOW){
						increaseStart(gamePanel.getAllPanels()[0][5].getMyPawn().getPawnColor());
						decreaseStart(PawnColor.YELLOW);
						gamePanel.getAllPanels()[0][5].remove();
						MyPawn pawn = player.getAPawn();
						gamePanel.getAllPanels()[0][5].add(pawn);
						return true;
					}else{
						return false;

					}
				}
			}
			return true;
		}else{
			return false;
		}
	}

	private void updateVectors(PawnColor color){
		redPawnsOnBoard = new Vector<>();
		bluePawnsOnBoard = new Vector<>();
		yellowPawnsOnBoard = new Vector<>();
		greenPawnsOnBoard = new Vector<>();

		MyInt tempi, tempj;
		if(color == PawnColor.BLUE){
			tempi = new MyInt(12);
			tempj = new MyInt(0);
		}else if(color == PawnColor.GREEN){
			tempi = new MyInt(3);
			tempj = new MyInt(15);
		}else if(color == PawnColor.RED){
			tempi = new MyInt(15);
			tempj = new MyInt(12);
		}else{//Yellow
			tempi = new MyInt(0);
			tempj = new MyInt(3);
		}
		int current;
		while((current = next(tempi, tempj, color, 1, 1)) != 6) {
			if(gamePanel.getAllPanels()[tempi.value][tempj.value].hasPawn()){
				if(gamePanel.getAllPanels()[tempi.value][tempj.value].getMyPawn().getPawnColor() == PawnColor.RED){
					Pair<Integer, Integer> p = new Pair<>(tempi.value, tempj.value);
					redPawnsOnBoard.add(p);
				}else if(gamePanel.getAllPanels()[tempi.value][tempj.value].getMyPawn().getPawnColor() == PawnColor.GREEN){
					Pair<Integer, Integer> p = new Pair<>(tempi.value, tempj.value);
					greenPawnsOnBoard.add(p);
				}else if(gamePanel.getAllPanels()[tempi.value][tempj.value].getMyPawn().getPawnColor() == PawnColor.BLUE){
					Pair<Integer, Integer> p = new Pair<>(tempi.value, tempj.value);
					bluePawnsOnBoard.add(p);
				}else if(gamePanel.getAllPanels()[tempi.value][tempj.value].getMyPawn().getPawnColor() == PawnColor.YELLOW){
					Pair<Integer, Integer> p = new Pair<>(tempi.value, tempj.value);
					yellowPawnsOnBoard.add(p);
				}
			}
		}

		if(color == PawnColor.BLUE){
			currentPawnsOnBoard = bluePawnsOnBoard;
		}else if(color == PawnColor.GREEN){
			currentPawnsOnBoard = greenPawnsOnBoard;
		}else if(color == PawnColor.RED){
			currentPawnsOnBoard = redPawnsOnBoard;
		}else{//yellow
			currentPawnsOnBoard = yellowPawnsOnBoard;
		}
	}

	private void playBot(MyInt i, MyInt j, PawnColor color){

		updateVectors(color);

		if(currentCard == 1){
			//attempting to look on the start
			if(insertNewToken()){
				//a token was inserted
			}else{
				if(currentPawnsOnBoard.size() == 0){
						/*No Valid Moves*/
				}else{
					for(int k = 0; k < currentPawnsOnBoard.size(); ++k){
						int y, x;
						y = currentPawnsOnBoard.elementAt(k).getKey();
						x = currentPawnsOnBoard.elementAt(k).getValue();
						if(move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 1, true)){
							return;
						}
					}
				}
			}
		}else if(currentCard == 2){
			if(insertNewToken()){
				//a token was inserted
			}else{
				if(currentPawnsOnBoard.size() == 0){
						/*No Valid Moves*/
					return;
				}else{
					for(int k = 0; k < currentPawnsOnBoard.size(); ++k){
						int y, x;
						y = currentPawnsOnBoard.elementAt(k).getKey();
						x = currentPawnsOnBoard.elementAt(k).getValue();
						if(move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 2, true)){
							return;
						}
					}
				}
			}
		}else if(currentCard == 3) {
			if (currentPawnsOnBoard.size() == 0) {
						/*No Valid Moves*/
				return;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 3, true)) {
						return;
					}
				}
			}
		}else if(currentCard == 4){
			if (currentPawnsOnBoard.size() == 0) {
						/*No Valid Moves*/
				return;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, -4, true)) {
						return;
					}
				}
			}
		}else if(currentCard == 5){
			if (currentPawnsOnBoard.size() == 0) {
						/*No Valid Moves*/
				return;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 5, true)) {
						return;
					}
				}
			}
		}else if(currentCard == 6){
			if(allPlayers[currentPlayerIndex].getMyPawnsInStart().size() >= 1){
				boolean found = false;
				int y, x;
				if((redPawnsOnBoard.size() >= 1) && (allPlayers[currentPlayerIndex].getMyColor() != PawnColor.RED)){
					y = redPawnsOnBoard.elementAt(0).getKey();
					x = redPawnsOnBoard.elementAt(0).getValue();
					found = true;
				}else if((greenPawnsOnBoard.size() >= 1) && (allPlayers[currentPlayerIndex].getMyColor() != PawnColor.GREEN)){
					y = greenPawnsOnBoard.elementAt(0).getKey();
					x = greenPawnsOnBoard.elementAt(0).getValue();
					found = true;
				}else if((bluePawnsOnBoard.size() >= 1) && (allPlayers[currentPlayerIndex].getMyColor() != PawnColor.BLUE)){
					y = bluePawnsOnBoard.elementAt(0).getKey();
					x = bluePawnsOnBoard.elementAt(0).getValue();
					found = true;
				}else if((yellowPawnsOnBoard.size() >= 1) && (allPlayers[currentPlayerIndex].getMyColor() != PawnColor.YELLOW)){
					y = yellowPawnsOnBoard.elementAt(0).getKey();
					x = yellowPawnsOnBoard.elementAt(0).getValue();
					found = true;
				}else{
					y = x = -1;
					found = false;
				}
				if(found){
					PawnColor colorRemoved = gamePanel.getAllPanels()[y][x].getMyPawn().getPawnColor();
					increaseStart(colorRemoved);
					gamePanel.getAllPanels()[y][x].remove();
					MyPawn p = new MyPawn(colorRemoved);
					for(int s = 0; s < allPlayers.length; ++s){
						if(colorRemoved == allPlayers[s].getMyColor()){
							allPlayers[s].pushToStack(p);
							break;
						}
					}
					p = allPlayers[currentPlayerIndex].getAPawn();
					decreaseStart(p.getPawnColor());
					gamePanel.getAllPanels()[y][x].add(p);
				}
			}
		}else if(currentCard == 7){
			if (currentPawnsOnBoard.size() == 1) {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 7, true)) {
						return;
					}
				}
				return;
			}
			for(int k = 0; k < currentPawnsOnBoard.size(); ++k){
				for(int t = 0; t < currentPawnsOnBoard.size(); ++t){
					for(int s = 0; s <= 7; ++s) {
						if (k != s) {
							int y, x, y1, x1;
							y = currentPawnsOnBoard.elementAt(k).getKey();
							x = currentPawnsOnBoard.elementAt(k).getValue();
							if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, s, false)) {
								if(s == 7){
									move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, s, true);
								}
								y1 = currentPawnsOnBoard.elementAt(t).getKey();
								x1= currentPawnsOnBoard.elementAt(t).getValue();
								if (move(gamePanel.getAllPanels()[y1][x1].getMyPawn(), y1, x1, 7 - s, false)) {
									if((7 - s) == 7){
										move(gamePanel.getAllPanels()[y1][x1].getMyPawn(), y1, x1, 7 - s, true);
										return;
									}else{
										move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, s, true);
										move(gamePanel.getAllPanels()[y1][x1].getMyPawn(), y1, x1, 7 - s, true);
										return;
									}
								}
							}
						}
					}
				}
			}
		}else if(currentCard == 8){
			if (currentPawnsOnBoard.size() == 0) {
						/*No Valid Moves*/
				return;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 8, true)) {
						return;
					}
				}
			}
		}else if(currentCard == 10){
			if (currentPawnsOnBoard.size() == 0) {
				/*No Valid Moves*/
				return;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 10, true)) {
						return;
					}
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, -1, true)) {
						return;
					}
				}
			}
		}else if(currentCard == 11){
			if (currentPawnsOnBoard.size() == 1) {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 11, true)) {
						return;
					}
				}
				return;
			}
			for(int k = 0; k < currentPawnsOnBoard.size(); ++k){
				for(int t = 0; t < currentPawnsOnBoard.size(); ++t){
					for(int s = 0; s <= 11; ++s) {
						if (k != s) {
							int y, x, y1, x1;
							y = currentPawnsOnBoard.elementAt(k).getKey();
							x = currentPawnsOnBoard.elementAt(k).getValue();
							if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, s, false)) {
								if(s == 11){
									move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, s, true);
								}
								y1 = currentPawnsOnBoard.elementAt(t).getKey();
								x1= currentPawnsOnBoard.elementAt(t).getValue();
								if (move(gamePanel.getAllPanels()[y1][x1].getMyPawn(), y1, x1, 11 - s, false)) {
									if((11 - s) == 11){
										move(gamePanel.getAllPanels()[y1][x1].getMyPawn(), y1, x1, 11 - s, true);
										return;
									}else{
										move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, s, true);
										move(gamePanel.getAllPanels()[y1][x1].getMyPawn(), y1, x1, 11 - s, true);
										return;
									}
								}
							}
						}
					}
				}
			}
		}else if (currentCard == 12){
			if (currentPawnsOnBoard.size() == 0) {
						/*No Valid Moves*/
				return;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 12, true)) {
						return;
					}
				}
			}
		}
	}

	//returns 1 if there is a pawn of the same color of the current player
	private boolean pawnOnStart(){
		if(allPlayers[currentPlayerIndex].getMyColor() == PawnColor.BLUE){
			if(gamePanel.getAllPanels()[10][0].hasPawn()){
				if(gamePanel.getAllPanels()[10][0].getMyPawn().getPawnColor() == PawnColor.BLUE){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else if(allPlayers[currentPlayerIndex].getMyColor() == PawnColor.RED){
			if(gamePanel.getAllPanels()[15][10].hasPawn()){
				if(gamePanel.getAllPanels()[15][10].getMyPawn().getPawnColor() == PawnColor.RED){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else if(allPlayers[currentPlayerIndex].getMyColor() == PawnColor.GREEN){
			if(gamePanel.getAllPanels()[5][15].hasPawn()){
				if(gamePanel.getAllPanels()[5][15].getMyPawn().getPawnColor() == PawnColor.GREEN){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{//YELLOW
			if(gamePanel.getAllPanels()[0][5].hasPawn()){
				if(gamePanel.getAllPanels()[0][5].getMyPawn().getPawnColor() == PawnColor.YELLOW){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
	}
	private boolean doIHaveValidMoves(){
		updateVectors(allPlayers[currentPlayerIndex].getMyColor());

		if(currentCard == 1){
			//attempting to look on the start
			if(!pawnOnStart()){

				return true;
			}else{
				if(currentPawnsOnBoard.size() == 0){
						return false;
				}else{
					for(int k = 0; k < currentPawnsOnBoard.size(); ++k){
						int y, x;
						y = currentPawnsOnBoard.elementAt(k).getKey();
						x = currentPawnsOnBoard.elementAt(k).getValue();
						if(move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 1, false)){
							return true;
						}
					}
				}
			}
		}else if(currentCard == 2){
			if(!pawnOnStart()){
				return true;
			}else{
				if(currentPawnsOnBoard.size() == 0){
						return false;
				}else{
					for(int k = 0; k < currentPawnsOnBoard.size(); ++k){
						int y, x;
						y = currentPawnsOnBoard.elementAt(k).getKey();
						x = currentPawnsOnBoard.elementAt(k).getValue();
						if(move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 2, false)){
							return true;
						}
					}
				}
			}
		}else if(currentCard == 3) {
			if (currentPawnsOnBoard.size() == 0) {
						return false;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 3, false)) {
						return true;
					}
				}
			}
		} else if(currentCard == 4){
			if (currentPawnsOnBoard.size() == 0) {
				return false;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, -4, false)) {
						return true;
					}
				}
			}
		} else if(currentCard == 5){
			if (currentPawnsOnBoard.size() == 0) {
				return false;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 5, false)) {
						return true;
					}
				}
			}
		}else if(currentCard == 6){//Sorry!
			if(allPlayers[currentPlayerIndex].getMyPawnsInStart().size() >= 1){
				if(allPlayers[currentPlayerIndex].getMyColor() == PawnColor.BLUE){
					if(redPawnsOnBoard.size() >= 1){
						return true;
					}
					if(greenPawnsOnBoard.size() >= 1){
						return true;
					}
					if(yellowPawnsOnBoard.size() >= 1){
						return true;
					}
				}else if(allPlayers[currentPlayerIndex].getMyColor() == PawnColor.RED){
					if(greenPawnsOnBoard.size() >= 1){
						return true;
					}
					if(bluePawnsOnBoard.size() >= 1){
						return true;
					}
					if(yellowPawnsOnBoard.size() >= 1){
						return true;
					}
				}else if(allPlayers[currentPlayerIndex].getMyColor() == PawnColor.GREEN){
					if(redPawnsOnBoard.size() >= 1){
						return true;
					}
					if(bluePawnsOnBoard.size() >= 1){
						return true;
					}
					if(yellowPawnsOnBoard.size() >= 1){
						return true;
					}
				}else{//YELLOW
					if(redPawnsOnBoard.size() >= 1){
						return true;
					}
					if(bluePawnsOnBoard.size() >= 1){
						return true;
					}
					if(greenPawnsOnBoard.size() >= 1){
						return true;
					}
				}
			}else{
				return false;
			}
		}else if (currentCard == 7) {
			System.out.println("Elements on board = " + currentPawnsOnBoard.size());
			if (currentPawnsOnBoard.size() == 1) {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 7, false)) {
						return true;
					}
				}
				return false;
			}
			for(int k = 0; k < currentPawnsOnBoard.size(); ++k){
				for(int t = 0; t < currentPawnsOnBoard.size(); ++t){
					for(int s = 0; s <= 7; ++s) {
						if (k != t) {
							int y, x;
							y = currentPawnsOnBoard.elementAt(k).getKey();
							x = currentPawnsOnBoard.elementAt(k).getValue();
							if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, s, false)) {
								if(s == 7){
									return true;
								}
								y = currentPawnsOnBoard.elementAt(t).getKey();
								x = currentPawnsOnBoard.elementAt(t).getValue();
								if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 7 - s, false)) {
									return true;
								}
							}
						}
					}
				}
			}
			return false;
		}else if(currentCard == 8){
			if (currentPawnsOnBoard.size() == 0) {
				return false;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 8, false)) {
						return true;
					}
				}
			}
		}else if(currentCard == 10){
			if (currentPawnsOnBoard.size() == 0) {
				return false;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 10, false)) {
						return true;
					}
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, -1, false)) {
						return true;
					}
				}
			}
		}else if(currentCard == 11){
			if (currentPawnsOnBoard.size() == 1) {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 11, false)) {
						return true;
					}
				}
				return false;
			}
			for(int k = 0; k < currentPawnsOnBoard.size(); ++k){
				for(int t = 0; t < currentPawnsOnBoard.size(); ++t){
					for(int s = 0; s <= 11; ++s) {
						if (k != t) {
							int y, x;
							y = currentPawnsOnBoard.elementAt(k).getKey();
							x = currentPawnsOnBoard.elementAt(k).getValue();
							if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, s, false)) {
								if(s == 11){
									return true;
								}
								y = currentPawnsOnBoard.elementAt(t).getKey();
								x = currentPawnsOnBoard.elementAt(t).getValue();
								if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 11 - s, false)) {
									return true;
								}
							}
						}
					}
				}
			}
			return false;
		}else if(currentCard == 12){
			if (currentPawnsOnBoard.size() == 0) {
				return false;
			} else {
				for (int k = 0; k < currentPawnsOnBoard.size(); ++k) {
					int y, x;
					y = currentPawnsOnBoard.elementAt(k).getKey();
					x = currentPawnsOnBoard.elementAt(k).getValue();
					if (move(gamePanel.getAllPanels()[y][x].getMyPawn(), y, x, 12, false)) {
						return true;
					}
				}
			}
		}
		return false;

	}
	private void nextTurn() {
		//fight on
		while(true){
			currentCard = myDeck.getNext();
			if(currentCard != 9) {
				break;
			}
		}

		if (currentCard == 6) {
			gamePanel.getCardsButton().setText("Sorry!");
		}
		gamePanel.getCardsButton().setText(Integer.toString(currentCard));
		promptMessage();

		if(doIHaveValidMoves()) {
			MyInt i = new MyInt(0);
			MyInt j = new MyInt(0);
			if (currentPlayerIndex != 0) {
				playBot(i, j, allPlayers[currentPlayerIndex].getMyColor());
				revalidate();
				repaint();
				if (currentCard != 2) {
					updatePlayerIndex();
				}
			}
		}else{
			System.out.println("Current Card = " + currentCard);
			gamePanel.getCardsButton().setEnabled(true);
			updatePlayerIndex();
		}
		if(currentPlayerIndex != 0){
			nextTurn();
		}else{//my turn
			return;
		}
	}

	private void updatePlayerIndex(){
		if (currentPlayerIndex == amountOfPlayers - 1) {
			currentPlayerIndex =  0;
		} else{
			++currentPlayerIndex;
		}
	}

	private void promptMessage(){
		if(currentCard == 1) {
			JOptionPane.showMessageDialog(this, "1 - Move Pawn from start(press your Start box) \n or move one space forward" +
														" (click the pawn you want to move)", "Sorry!", JOptionPane.PLAIN_MESSAGE);
		}else if(currentCard == 2){
			JOptionPane.showMessageDialog(this, "2 - Move Pawn from start(press your Start box) \n or move two spaces forward" +
					" (click the pawn you want to move) \n You also get to draw a card again! :)"  , "Sorry!", JOptionPane.PLAIN_MESSAGE);
		}else if(currentCard == 3){
			JOptionPane.showMessageDialog(this, "3 - Move three spaces forward (click the pawn" +
					" you want to move)", "Sorry!", JOptionPane.PLAIN_MESSAGE);
		}else if(currentCard == 4){
			JOptionPane.showMessageDialog(this, "4 - Move four spaces backward (click the pawn" +
					" you want to move)", "Sorry!", JOptionPane.PLAIN_MESSAGE);
		}else if(currentCard == 5){
			JOptionPane.showMessageDialog(this, "5 - Move five spaces forward (click the pawn" +
					" you want to move)", "Sorry!", JOptionPane.PLAIN_MESSAGE);
		}else if(currentCard == 6){
			JOptionPane.showMessageDialog(this, "Sorry! - Move any one pawn from Start to a square occupied by \nany opponent, " +
					"sending that pawn back to its own Start.", "Sorry!", JOptionPane.PLAIN_MESSAGE);
		}else if(currentCard == 7){
			if(currentPlayerIndex == 0) {
				Object[] possibilities = {"7:0", "6:1", "5:2", "4:3"};
				String s = (String) JOptionPane.showInputDialog(
						this,
						"7 - Move one pawn seven spaces forward or split the seven spaces between two pawns.\n" +
								"YOU ALWAYS NEED TO CLICK TWICE,\n does not matter if it's the same pawn",
						"Sorry!",
						JOptionPane.PLAIN_MESSAGE,
						new ImageIcon("7"),
						possibilities,
						"7:0");
				if (s == "7:0") {
					split1 = 7;
					split2 = 0;
				} else if (s == "6:1") {
					split1 = 6;
					split2 = 1;
				} else if (s == "5:2") {
					split1 = 5;
					split2 = 2;
				} else if (s == "4:3") {
					split1 = 4;
					split2 = 3;
				}
			}else{
				JOptionPane.showMessageDialog(this, "7 - Move one pawn seven spaces forward or split the" +
						"\n seven spaces between two pawns.", "Sorry!", JOptionPane.PLAIN_MESSAGE);
			}
		}else if(currentCard == 8){
			JOptionPane.showMessageDialog(this, "8 - Move eight spaces forward (click the pawn" +
					" you want to move)", "Sorry!", JOptionPane.PLAIN_MESSAGE);
		}else if(currentCard == 10){
			if(currentPlayerIndex == 0) {
				Object[] possibilities = {"forward", "backward"};
				String s = (String) JOptionPane.showInputDialog(
						this,
						"10 - Move a pawn ten spaces forward or one space backward.",
						"Sorry!",
						JOptionPane.PLAIN_MESSAGE,
						new ImageIcon("10"),
						possibilities,
						"forward");
				if (s == "forward") {
					backwards = false;
				}else{
					backwards = true;
				}
			}else{
				JOptionPane.showMessageDialog(this, "10 - Move a pawn ten spaces" +
						" forward or one space backward.", "Sorry!", JOptionPane.PLAIN_MESSAGE);
			}

		}else if(currentCard == 11){
			if(currentPlayerIndex == 0) {
				Object[] possibilities = {"11:0", "10:1", "9:2", "8:3", "7:4","6:5"};
				String s = (String) JOptionPane.showInputDialog(
						this,
						"11 - Move one pawn seven spaces forward or split the eleven spaces between two pawns.\n" +
								"YOU ALWAYS NEED TO CLICK TWICE, it does not matter if it's the same pawn",
						"Sorry!",
						JOptionPane.PLAIN_MESSAGE,
						new ImageIcon("7"),
						possibilities,
						"11:0");
				if (s == "11:0") {
					split1 = 11;
					split2 = 0;
				} else if (s == "10:1") {
					split1 = 10;
					split2 = 1;
				} else if (s == "9:2") {
					split1 = 9;
					split2 = 2;
				} else if (s == "8:3") {
					split1 = 8;
					split2 = 3;
				}else if (s == "7:4") {
					split1 = 7;
					split2 = 4;
				} else if (s == "6:5") {
					split1 = 6;
					split2 = 5;
				}
			}else{
				JOptionPane.showMessageDialog(this, "11 - Move one pawn seven spaces forward or split the" +
						"\n eleven spaces between two pawns.", "Sorry!", JOptionPane.PLAIN_MESSAGE);
			}
		}else if(currentCard == 12){
			JOptionPane.showMessageDialog(this, "11 - Move twelve spaces forward (click the pawn" +
					" you want to move)", "Sorry!", JOptionPane.PLAIN_MESSAGE);
		}
	}

	//the useless function ever:
	public JFrame getMe() {
		return me;
	}
	///////////
	class ChangeStateEvent implements ActionListener{
		private final String nextPanelName;
		ChangeStateEvent(String nextPanelName){
			this.nextPanelName = nextPanelName;
		}
		@Override
		public void actionPerformed(ActionEvent ae) {
			CardLayout cl = (CardLayout) outerPanel.getLayout();
			cl.show(outerPanel, nextPanelName);
		}
		
	}
	////////////
	class CardButtonPressEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ae) {
			if(gamePanel.getCardsButton().isEnabled()) {
				currentPlayerIndex = 0;
				gamePanel.getCardsButton().setEnabled(false);
				nextTurn();
			}
		}
	}
	///////////
	class StartLabelPressedEvent extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent me) {
			super.mousePressed(me);
			if(!gamePanel.getCardsButton().isEnabled()) {
				if (currentPlayerIndex == 0) {
					if ((currentCard == 1) || (currentCard == 2)) {
						if (makeMove() == 0) {
							JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
						} else {
							if (currentCard != 2) {
								updatePlayerIndex();
								gamePanel.getCardsButton().setEnabled(true);
							}
							revalidate();
							repaint();
							nextTurn();
						}
					}
				}
			}
		}
		private int makeMove(){
			if (currentCard == 1) {
				if(insertNewToken()){
					return 1;
				}else{
					return 0;
				}
			}else if(currentCard == 2){
				if(insertNewToken()){
					return 1;
				}else{
					return 0;
				}
			}
			return 0;
		}
	}

	class MyPanelPressedEvent extends MouseAdapter{
		int i, j;
		MyPanelPressedEvent(int i, int j){
			super();
			this.i = i;
			this.j = j;
		}
		@Override
		public void mousePressed (MouseEvent me){
			super.mousePressed(me);
			if(!gamePanel.getCardsButton().isEnabled()) {
				if (currentPlayerIndex == 0) {//my turn ? or not
					if (gamePanel.getAllPanels()[i][j].hasPawn()) {
						MyPawn pawn = gamePanel.getAllPanels()[i][j].getMyPawn();
						if(pawn.getPawnColor() == allPlayers[0].getMyColor()) {
							if (currentCard == 1) {
								if (allPlayers[0].getMyColor() == pawn.getPawnColor()) {//it is my pawn
									if (!move(pawn, i, j, 1, true)) {
										JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
									} else {
										revalidate();
										repaint();
										updatePlayerIndex();
										gamePanel.getCardsButton().setEnabled(true);
										nextTurn();
									}
								}
							} else if (currentCard == 2) {
								if (allPlayers[0].getMyColor() == pawn.getPawnColor()) {//it is my pawn
									if (!move(pawn, i, j, 2, true)) {
										JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
									} else {
										revalidate();
										repaint();
										nextTurn();
									}
								}
							} else if (currentCard == 3) {
								if (!move(pawn, i, j, 3, true)) {
									JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
								} else {
									revalidate();
									repaint();
									updatePlayerIndex();
									gamePanel.getCardsButton().setEnabled(true);
									nextTurn();
								}
							} else if (currentCard == 4) {
								if (!move(pawn, i, j, -4, true)) {
									JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
								} else {
									revalidate();
									repaint();
									updatePlayerIndex();
									gamePanel.getCardsButton().setEnabled(true);
									nextTurn();
								}
							} else if (currentCard == 5) {
								if (!move(pawn, i, j, 5, true)) {
									JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
								} else {
									revalidate();
									repaint();
									updatePlayerIndex();
									gamePanel.getCardsButton().setEnabled(true);
									nextTurn();
								}
							}else if(currentCard == 7){
								if (!move(pawn, i, j, split1, true)) {
									JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
								}else{
									split1 = split2;
									split2 = -1;
								}
								revalidate();
								repaint();
								if(split1 == -1){
									updatePlayerIndex();
									gamePanel.getCardsButton().setEnabled(true);
									nextTurn();
								}
							}else if(currentCard == 8){
								if (!move(pawn, i, j, 8, true)) {
									JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
								} else {
									revalidate();
									repaint();
									updatePlayerIndex();
									gamePanel.getCardsButton().setEnabled(true);
									nextTurn();
								}
							}else if(currentCard == 10){
								if(backwards){
									if (!move(pawn, i, j, -1, true)) {
										JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
									} else {
										revalidate();
										repaint();
										updatePlayerIndex();
										gamePanel.getCardsButton().setEnabled(true);
										nextTurn();
									}
								}else{
									if (!move(pawn, i, j, 10, true)) {
										JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
									} else {
										revalidate();
										repaint();
										updatePlayerIndex();
										gamePanel.getCardsButton().setEnabled(true);
										nextTurn();
									}
								}
							}else if(currentCard == 11){
								if (!move(pawn, i, j, split1, true)) {
									JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
								}else{
									split1 = split2;
									split2 = -1;
								}
								revalidate();
								repaint();
								if(split1 == -1){
									updatePlayerIndex();
									gamePanel.getCardsButton().setEnabled(true);
									nextTurn();
								}
							}else if(currentCard == 12){
								if (!move(pawn, i, j, 12, true)) {
									JOptionPane.showMessageDialog(getMe(), "Invalid Move, click, try another thing", "Sorry!", JOptionPane.PLAIN_MESSAGE);
								} else {
									revalidate();
									repaint();
									updatePlayerIndex();
									gamePanel.getCardsButton().setEnabled(true);
									nextTurn();
								}
							}
						}else{//useful for card == 6
							if(currentCard == 6){
								if(allPlayers[0].getMyPawnsInStart().size() >= 1){
									PawnColor colorRemoved = gamePanel.getAllPanels()[i][j].getMyPawn().getPawnColor();
									increaseStart(colorRemoved);
									gamePanel.getAllPanels()[i][j].remove();
									MyPawn p = new MyPawn(colorRemoved);
									for(int s = 0; s < allPlayers.length; ++s){
										if(colorRemoved == allPlayers[s].getMyColor()){
											allPlayers[s].pushToStack(p);
											break;
										}
									}
									p = allPlayers[0].getAPawn();
									decreaseStart(p.getPawnColor());
									gamePanel.getAllPanels()[i][j].add(p);
									revalidate();

									repaint();
									updatePlayerIndex();
									gamePanel.getCardsButton().setEnabled(true);
									nextTurn();
								}
							}
						}
					}
				}
			}
		}
	}
}














