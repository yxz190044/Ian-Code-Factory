import java.util.*;

public class Card extends CardIdentity { // subclass
    private boolean cardError;
    public Card() {
        super();
        cardError = false;
    }
    public Card(char value, Suit suit) {
        super();
        set(value, suit);
        
    }
    
    public String toString() {
        if (cardError) {
            return "[ invalid ]";
        }
        return getValue() + " " + "of" + " " + getSuit();
    }
    
    public boolean isCardError() {
        return cardError;
    }
    
    public boolean equals(Card card) {
        return this.cardError == card.cardError &&
        this.getValue() == card.getValue() && this.getSuit() == card.getSuit();
    }
    
    
    @Override
    boolean set(char value, Suit suit) {
        if (super.set(value, suit)) {
            cardError = false;
        } else {
            cardError = true;
        }
        return !cardError;
    }
    
    public Card deepCopyCard() {
        return new Card(getValue(), getSuit());
    }
    
}
