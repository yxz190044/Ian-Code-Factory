import java.util.*;

class CardIdentity { // superclass
    private char value;
    private Suit suit;
    public CardIdentity() {
        value = 'A';
        suit = Suit.spades;
    }
    boolean set(char value, Suit suit) {
        if (!isValid(value, suit)) {
            return false;
        }
        this.value = value;
        this.suit = suit;
        return true;
    }
    Suit getSuit() {
        return suit;
    }
    char getValue() {
        return value;
    }
    private static boolean isValid(char value, Suit suit) {
        boolean checkVal = false;
        boolean checkSuit = false;
        if (value <= '9' && value >= '2') {
            checkVal = true;
        } else if (value == 'A' || value == 'K' || value == 'Q'
                   || value == 'J' || value == 'T') {
            checkVal = true;
        }
        for (Suit s : Suit.values()) {
            if (suit.equals(s)) {
                checkSuit = true;
            }
        }
        return checkVal && checkSuit;
    }
}
