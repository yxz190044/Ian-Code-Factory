//package assignment1;
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

enum Suit {
clubs,
diamonds,
hearts,
spades
}

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
class Card extends CardIdentity { // subclass
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
	
}

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
