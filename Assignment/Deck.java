import java.util.*;

class Deck {
    
    static private final int MAX_PACKS = 6;
    static private final int NUM_CARDS_PER_PACK = 52;
    static private final int MAX_CARDS_PER_DECK = MAX_PACKS * NUM_CARDS_PER_PACK;
    
    static private Card[] masterPack;
    private Card[] cards;
    private int topCard;
    private int numPacks;
    private int cardCount;
    
    public Deck(int numPacks) {
        allocateMasterPack();
        if (!initializePack(numPacks)){
            initializePack(1);
        }
    }
    
    public Deck() { // not sure about this
        this(1);
    }
    
    public boolean initializePack(int numPacks) {
        if (numPacks > MAX_PACKS) {
            return false;
        }
        this.numPacks = numPacks;
        int size = 52 * numPacks;
        cards = new Card[size];
        for (int i = 0; i < size; i++) {
            cards[i] = masterPack[i % 52];
        }
        topCard = 0;
        cardCount = size;
        return true;
    }
    
    public void shuffle() {
        Card myCard = new Card();
        for (int i = topCard + cardCount - 1; i > topCard; i--){
            int randInt = (int) (Math.random() * (i - topCard + 1)) + topCard;
            randInt %= cards.length;
            int index = i % cards.length;
            myCard = cards[index];
            cards[index] = cards[randInt];
            cards[randInt] = myCard;
        }
    }
    
    public Card dealCard() {// remove the top card
        if (cardCount == 0) {
            return null;
        }
        Card card = cards[topCard].deepCopyCard();
        topCard = (topCard + 1) % 52;
        cardCount--;
        return card;
    }
    
    public int topCard() {// return the index?
        return topCard;
    }
    
    public Card inspectCard(int k) { // do try catch
        
        //        if (k < 0 || k >= cards.length) {
        //            return new Card('B', Suit.spades);
        //        }
        //        if (k >= topCard && k - topCard >= cardCount) {
        //            return new Card('B', Suit.spades);
        //        }
        //        if (k <= topCard && k + cards.length - topCard >= cardCount) {
        //            return new Card('B', Suit.spades);
        //        }
        
        try {
            if (cardCount == 0) {
                return new Card('B', Suit.spades);
            }
            Card card = cards[k];
            return card.deepCopyCard();
        } catch (Exception e) {
            return new Card('B', Suit.spades);
        }
        
        //        return cards[k].deepCopyCard();
    }
    
    private static void allocateMasterPack() {
        if (masterPack != null) {
            return;
        }
        masterPack = new Card[52];
        int index = 0;
        for (Suit suit : Suit.values()) {
            for (int i = 1; i <= 13; i++) {
                char value = '0';
                if (i == 1) {
                    value = 'A';
                } else if (i == 10) {
                    value = 'T';
                } else if (i == 11) {
                    value = 'J';
                } else if (i == 12) {
                    value = 'Q';
                } else if (i == 13) {
                    value = 'K';
                } else {
                    value = (char) (value + i);
                }
                masterPack[index++] = new Card(value, suit);
            }
        }
        
    }
}


