import java.util.*;

class Hand {
    static public int MAX_CARDS = 50;
    private Card[] myCards;
    private int numCards;
    
    private int begin;
    
    public Hand() {
        myCards = new Card[MAX_CARDS];
        numCards = 0;
        begin = 0;
    }
    
    public void resetHand() {
        myCards = new Card[MAX_CARDS];
        numCards = 0;
        begin = 0;
    }
    
    public boolean takeCard(Card card) {
        if (numCards == MAX_CARDS) {
            return false;
        }
        if (card.isCardError()) {
            return false;
        }
        int index = begin + numCards;
        index %= MAX_CARDS;
        myCards[index] = new Card(card.getValue(), card.getSuit());
        numCards++;
        return true;
    }
    
    public Card playCard() {
        if (numCards == 0) {
            return null;
        }
        Card playing = myCards[begin];
        System.out.println("Playing " + playing.toString());
        numCards--;
        begin++;
        begin %= MAX_CARDS;
        return playing;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numCards; i++) {
            int index = (i + begin)%MAX_CARDS;
            sb.append(myCards[index].toString());
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
    
    public int getNumCards() {
        return numCards;
    }
    
    public Card inspectCard(int k) {
        if (k < 0 || k >= MAX_CARDS) {
            return new Card('B', Suit.spades);
        }
        if (k >= begin && k - begin >= numCards) {
            return new Card('B', Suit.spades);
        }
        if (k <= begin && k + MAX_CARDS - begin >= numCards) {
            return new Card('B', Suit.spades);
        }
        return myCards[k];
    }
}
