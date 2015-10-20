package prego_CSCI201_Assignment2;

import javax.swing.*;

/**
 * Created by albertoprego on 9/28/15.
 */
public class CardsButton extends JButton {
    CardsButton(String name){
        super(name);
    }
    public void setText(String text){
        if(text == "6"){
            super.setText("Sorry!");
        }else{
            super.setText(text);
        }
    }
}
