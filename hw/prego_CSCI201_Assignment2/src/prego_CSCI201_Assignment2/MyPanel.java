package prego_CSCI201_Assignment2;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private MyPawn myPawn;
    private boolean hasPawn_;
    public MyPawn getMyPawn() {
        return myPawn;
    }
    public boolean hasPawn(){
        return hasPawn_;
    }

    public Component add(Component comp){
        if(comp instanceof MyPawn) {
            this.myPawn = (MyPawn)comp;
            hasPawn_ = true;
        }
        return super.add(comp);
    }

    public void remove(){
        hasPawn_ = false;
        super.remove(myPawn);
        revalidate();
    }
    MyPanel(){
        super();
        hasPawn_ = false;
        myPawn = null;
    }
}
