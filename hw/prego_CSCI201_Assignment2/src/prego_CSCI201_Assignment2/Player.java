package prego_CSCI201_Assignment2;

import java.util.Stack;
import java.util.Vector;

public class Player {
    private Stack<MyPawn> myPawnsInStart;
    private PawnColor myColor;
    private Vector<MyPawn> allMyPawns;

    public Stack<MyPawn> getMyPawnsInStart() {
        return myPawnsInStart;
    }

    public void pushToStack(MyPawn p){
        myPawnsInStart.push(p);
    }

    public int pawnsLeftInStart(){
        return myPawnsInStart.size();
    }

    public MyPawn getAPawn(){
        return myPawnsInStart.pop();
    }

    public PawnColor getMyColor() {
        return myColor;
    }

    Player(PawnColor myColor){
        this.myColor = myColor;
        initializeMembers();
    }
    private void initializeMembers(){
        myPawnsInStart = new Stack<>();
        allMyPawns = new Vector<>();
        for (int i = 0; i < 4; i++) {
            MyPawn pawn = new MyPawn(this.myColor);
            myPawnsInStart.push(pawn);
            allMyPawns.add(pawn);
        }
    }
}
