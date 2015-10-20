package prego_CSCI201_Assignment2;

public class MyDeck {
    private int cards[];
    private int current;
    MyDeck(){
        cards = new int[12];
        for(int i = 0; i < 12; ++i){
            cards[i] = i;
        }
        shuffle();
    }
    public int getNext() {
        if (current == 11) {
            shuffle();
            return getNext();
        } else {
            return cards[++current] + 1;
        }
    }
    public void shuffle(){
        for ( int i = cards.length-1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            int temp = cards[i];
            cards[i] = cards[rand];
            cards[rand] = temp;
        }
        current = -1;
    }
}
