package prego_CSCI201_Assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyPawn extends JLabel {
    private PawnColor pawnColor;
    public PawnColor getPawnColor() {
        return pawnColor;
    }

    private int currentRow, currentCol;
    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }
    public void setLocation(int row, int col){
        if(((row > 15) || (row < 0)) || ((col > 15) || (col < 0))){
            throw new IndexOutOfBoundsException("In MyPawn.setLocation()");
        }else{
            currentCol = col;
            currentRow = row;
        }
    }

    MyPawn(PawnColor c){
        super();
        pawnColor = c;
        initializeMembers();
        addEvents();
    }
    private void initializeMembers(){
        if(this.pawnColor == PawnColor.BLUE){
            super.setIcon(new ImageIcon((new ImageIcon("Images/bluePawn.jpg")).getImage().getScaledInstance(15, 15, java.awt.Image.SCALE_SMOOTH)));
        }else if(this.pawnColor == PawnColor.YELLOW){
            super.setIcon(new ImageIcon((new ImageIcon("Images/yellowPawn.png")).getImage().getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH)));
        }else if(this.pawnColor == PawnColor.GREEN){
            super.setIcon(new ImageIcon((new ImageIcon("Images/greenPawn.png")).getImage().getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH)));
        }else if(this.pawnColor == PawnColor.RED){
            super.setIcon(new ImageIcon((new ImageIcon("Images/redPawn.jpg")).getImage().getScaledInstance(15,15,java.awt.Image.SCALE_SMOOTH)));
        }
    }

    private void addEvents(){
    }


}

