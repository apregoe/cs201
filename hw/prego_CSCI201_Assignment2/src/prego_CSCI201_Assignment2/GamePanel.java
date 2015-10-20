package prego_CSCI201_Assignment2;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
    private  MyPanel[][] allPanels = new MyPanel[16][16];
    private CardsButton cardsButton;
    private JLabel redStartlabel, greenStartLabel, blueStartLabel, yellowStartLabel;
    private JLabel redHomelabel, greenHomeLabel, blueHomeLabel, yellowHomeLabel;
    MainWindow mainWindow;

    public JLabel getRedStartlabel() {
        return redStartlabel;
    }
    public JLabel getGreenStartLabel() {
        return greenStartLabel;
    }
    public JLabel getBlueStartLabel() {
        return blueStartLabel;
    }
    public JLabel getYellowStartLabel() {
        return yellowStartLabel;
    }

    public JLabel getRedHomelabel() {
        return redHomelabel;
    }

    public JLabel getGreenHomeLabel() {
        return greenHomeLabel;
    }

    public JLabel getBlueHomeLabel() {
        return blueHomeLabel;
    }

    public JLabel getYellowHometLabel() {
        return yellowHomeLabel;
    }

    private GridBagConstraints gbc;

    public void setParent(MainWindow mw){
        mainWindow = mw;
    }

    public GridBagConstraints getGbc() {
        return gbc;
    }

    public CardsButton getCardsButton() {
        return cardsButton;
    }


    public MyPanel[][] getAllPanels() {
        return allPanels;
    }



    GamePanel(){
		super();
        initializeComponents();
		createGUI();
		addEvents();
        /*allPanels[0][5].add(new MyPawn(PawnColor.BLUE));
        allPanels[10][0].add(new MyPawn(PawnColor.RED));
        allPanels[15][10].add(new MyPawn(PawnColor.GREEN));
        allPanels[5][15].add(new MyPawn(PawnColor.YELLOW));*/
    }
	private void initializeComponents() {
	}
	private void createGUI() {
		super.setLayout(new GridBagLayout());
		paintBorders();
	}
	private void paintBorders(){
		gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		for(int i = 0; i < 16; ++i){//row i
			gbc.gridy = i;
			for(int j = 0; j < 16; ++j) {//column j
                allPanels[i][j] = new MyPanel();
                allPanels[i][j].setLayout(new GridBagLayout());
                gbc.gridx = j;
                JLabel label = new JLabel(" ", SwingConstants.CENTER);
                label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
                //borders
                if (j == 0) {//left column: column 0  '| '
                    if (((i >= 2) && (i <= 6)) || ((i >= 11) && (i <= 14))) {
                        label.setText("^");
                        label.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
                    } else {
                        label.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                    }
                    super.add(label, gbc);
                    super.add(allPanels[i][j], gbc);
                } else if ((i == 0) && (j >= 1)) {//top row: row 0... j>=1 to not repaint '-'
                    if ((j <= 4) || ((j >= 9) && (j <= 13))) {
                        label.setText(">");
                        label.setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
                    } else {
                        label.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                    }
                    super.add(label, gbc);
                    super.add(allPanels[i][j], gbc);
                } else if ((i == 15) && (j >= 1)) {//button row '_'
                    if (((j >= 2) && (j <= 6)) || ((j >= 11) && (j <= 14))) {
                        label.setText("<");
                        label.setBorder(BorderFactory.createLineBorder(Color.red, 1));
                    } else {
                        label.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                    }
                    super.add(label, gbc);
                    super.add(allPanels[i][j], gbc);
                } else if ((j == 15) && (i >= 1) && (i <= 14)) {//right column
                    if ((i <= 4) || ((i >= 9) && (i <= 13))) {
                        label.setText("v");
                        label.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                    } else {
                        label.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                    }
                    super.add(label, gbc);
                    super.add(allPanels[i][j], gbc);
                }
                //inner
                else if ((j == 2) && ((i >= 1) && (i <= 6))) {//yellow home
                    label.setText(" ");
                    label.setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
                    super.add(label, gbc);
                    super.add(allPanels[i][j], gbc);
                    if (i == 6) {
                        label.setText("Home");
                    }
                } else if ((j == 2) && (i == 7)) {//0 in yellow home
                    yellowHomeLabel = label;
                    yellowHomeLabel.setText("0");
                    super.add(yellowHomeLabel, gbc);
                    super.add(allPanels[i][j], gbc);
                } else if ((j == 4) && ((i == 1) || (i == 2))) {
                    if (i == 1) {//yellow start
                        label.setText("Start");
                        label.setBorder(BorderFactory.createLineBorder(Color.yellow, 1));
                        super.add(label, gbc);
                        super.add(allPanels[i][j], gbc);
                    } else {//4 in yellow start
                        yellowStartLabel = label;
                        yellowStartLabel.setText("4");
                        super.add(yellowStartLabel, gbc);
                        super.add(allPanels[i][j], gbc);
                    }
                } else if ((i == 13) && ((j >= 1) && (j <= 7))) {//blue home line
                    if ((j >= 1) && (j <= 6)) {
                        label.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
                        if (j == 6) {
                            label.setText("Home");
                        }
                        super.add(label, gbc);
                        super.add(allPanels[i][j], gbc);
                    }
                    if (j == 7) {
                        blueHomeLabel = label;
                        blueHomeLabel.setText("0");
                        super.add(blueHomeLabel, gbc);
                        super.add(allPanels[i][j], gbc);
                    }
                } else if ((i == 11) && (j == 1)) {//blue start
                    label.setText("Start");
                    label.setBorder(BorderFactory.createLineBorder(Color.blue, 1));
                    super.add(label, gbc);
                    super.add(allPanels[i][j], gbc);
                } else if ((i == 11) && (j == 2)) {//blue start 4
                    blueStartLabel = label;
                    blueStartLabel.setText("4");
                    super.add(blueStartLabel, gbc);
                    super.add(allPanels[i][j], gbc);
                } else if ((i == 2) && ((j >= 8) && (j <= 14))) {
                    if (j == 8) {//green home 0
                        greenHomeLabel = label;
                        greenHomeLabel.setText("0");
                        super.add(greenHomeLabel, gbc);
                        super.add(allPanels[i][j], gbc);
                    } else {//green home
                        label.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                        if (j == 9) {//green home
                            label.setText("home");
                            super.add(label, gbc);
                            super.add(allPanels[i][j], gbc);
                        } else {//green home lune
                            super.add(label, gbc);
                            super.add(allPanels[i][j], gbc);
                        }
                    }
                } else if ((i == 4) && ((j == 13) || (j == 14))) {
                    if (j == 14) {//green start
                        label.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                        label.setText("Start");
                        super.add(label, gbc);
                        super.add(allPanels[i][j], gbc);

                    } else {//green start 4
                        greenStartLabel = label;
                        greenStartLabel.setText("4");
                        super.add(greenStartLabel, gbc);
                        super.add(allPanels[i][j], gbc);
                    }
                } else if ((j == 13) && ((i >= 8) && (j <= 14))) {//red home
                    if (i == 8) {
                        redHomelabel = label;
                        redHomelabel.setText("0");
                        super.add(redHomelabel, gbc);
                        super.add(allPanels[i][j], gbc);
                    } else if (i == 9) {
                        label.setText("Home");
                        label.setBorder(BorderFactory.createLineBorder(Color.red, 1));
                        super.add(label, gbc);
                        super.add(allPanels[i][j], gbc);
                    } else {
                        label.setBorder(BorderFactory.createLineBorder(Color.red, 1));
                        super.add(label, gbc);
                        super.add(allPanels[i][j], gbc);
                    }
                } else if((i == 14) && (j == 11)) {//red start label
                    label.setText("Start");
                    label.setBorder(BorderFactory.createLineBorder(Color.red, 1));
                    super.add(label, gbc);
                    super.add(allPanels[i][j], gbc);
                }else if((i == 13) && (j == 11)){
                    redStartlabel = label;
                    redStartlabel.setText("4");
                    super.add(redStartlabel, gbc);
                    super.add(allPanels[i][j], gbc);
                } else if((i == 7) && (j == 8)){//cards Button
                    cardsButton = new CardsButton("");
                    cardsButton.setPreferredSize(new Dimension(35,25));
                    allPanels[i][j].add(cardsButton);
                    super.add(allPanels[i][j], gbc);
                }else if((i == 7) && (j == 7)){//cards label
                    label.setText("Cards:");
                    super.add(label, gbc);
                    super.add(allPanels[i][j], gbc);
                }
            }
        }
        /*allPanels[9][0].add(new MyPawn(PawnColor.YELLOW));
        allPanels[5][2].add(new MyPawn(PawnColor.YELLOW));
        allPanels[3][0].add(new MyPawn(PawnColor.BLUE));*/

    }
	private void addEvents() {
	}
    public void insertPawnTo(int i, int j, PawnColor c){
        allPanels[i][j].add(new MyPawn(c));
    }
}

















