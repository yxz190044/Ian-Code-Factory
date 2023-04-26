import java.util.*;

public class Assignment1 {
    public static void main(String args[]) {
        
        //phase 1
        System.out.println("**** phase 1 ****");
        
        Card legal1 = new Card();
        Card legal2 = new Card('K', Suit.hearts);
        Card illegal = new Card('B', Suit.spades);
        
        System.out.println(legal1.toString());
        System.out.println(legal2.toString());
        System.out.println(illegal.toString());
        
        legal1.set('C', Suit.diamonds);
        illegal.set('Q', Suit.clubs);
        
        System.out.println(legal1.toString());
        System.out.println(legal2.toString());
        System.out.println(illegal.toString());
        System.out.println();
        //phase 2
        System.out.println("**** phase 2 ****");
        
        Hand hand = new Hand();
        for (int i = 0; i < 10; i++) {
            hand.takeCard(new Card(randomValue(), randomSuit()));
        }
        System.out.println(hand.toString());
        hand.playCard(); hand.playCard(); hand.playCard();
        hand.playCard(); hand.playCard(); hand.playCard();
        System.out.println(hand.toString());
        for (int i = 0; i < 4; i++) {
            hand.takeCard(new Card(randomValue(), randomSuit()));
        }
        System.out.println(hand.toString());
        System.out.println(hand.getNumCards());
        hand.takeCard(new Card(randomValue(), randomSuit()));
        System.out.println(hand.getNumCards());
        hand.takeCard(new Card('B', Suit.spades));
        System.out.println(hand.getNumCards());
        
        for (int i = 0; i < 77; i++) {
            hand.takeCard(new Card(randomValue(), randomSuit()));
        }
        System.out.println(hand.getNumCards());
        
        
        //Assignment2
    }
    
    static private Suit randomSuit() {
        int num = (int) (Math.random() * 4);
        if (num == 0) {
            return Suit.clubs;
        } else if (num == 1) {
            return Suit.diamonds;
        } else if (num == 2) {
            return Suit.hearts;
        } else {
            return Suit.spades;
        }
    }
    
    static private char randomValue() {
        int num = (int) (Math.random() * 13);
        if (num == 0) {
            return 'A';
        } else if (num <= 8 && num >= 1) {
            return (char) (num + '1');
        } else if (num == 9) {
            return 'T';
        } else if (num == 10) {
            return 'J';
        } else if (num == 11) {
            return 'Q';
        } else {
            return 'K';
        }
    }
    
}
