import java.util.*;

public class Assignment2 {
    public static void main(String args[]) {
        
        //phase 1
        System.out.println("**** phase 1 ****");
        
        Deck deck1 = new Deck(2);
        while(!deck1.inspectCard(deck1.topCard()).isCardError()) {
            System.out.print(deck1.dealCard().toString() + ", ");
        }
        System.out.println();
        Deck deck2 = new Deck(2);
        deck2.shuffle();
        while(!deck2.inspectCard(deck2.topCard()).isCardError()) {
            System.out.print(deck2.dealCard().toString() + ", ");
        }
        System.out.println();
        
        Deck deck3 = new Deck(1);
        while(!deck3.inspectCard(deck3.topCard()).isCardError()) {
            System.out.print(deck3.dealCard().toString() + ", ");
        }
        System.out.println();
        Deck deck4 = new Deck(1);
        deck4.shuffle();
        while(!deck4.inspectCard(deck4.topCard()).isCardError()) {
            System.out.print(deck4.dealCard().toString() + ", ");
        }
        System.out.println();
        
        
        //phase 2
        System.out.println("**** phase 2 ****");
        System.out.print("How many hands? (1 - 10, please): ");
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        sc.close();
        System.out.println("Here are our hands, from unshuffled deck: ");
        Hand[] hands = new Hand[num];
        for (int i = 0; i < num; i++) {
            hands[i] = new Hand();
        }
        Deck deck5 = new Deck(1);
        int k = 0;
        while(!deck5.inspectCard(deck5.topCard()).isCardError()) {
            int index = k % num;
            k++;
            hands[index].takeCard(deck5.dealCard());
        }
        for (int i = 0; i < num; i++) {
            System.out.println("Hand" + (i+1) + " = (" + hands[i].toString() + " )");
        }
        
        System.out.println("Here are our hands, from shuffled deck: ");
        hands = new Hand[num];
        for (int i = 0; i < num; i++) {
            hands[i] = new Hand();
        }
        Deck deck6 = deck5;
        deck6.initializePack(1);
        deck6.shuffle();
        k = 0;
        while(!deck6.inspectCard(deck6.topCard()).isCardError()) {
            int index = k % num;
            k++;
            hands[index].takeCard(deck6.dealCard());
        }
        for (int i = 0; i < num; i++) {
            System.out.println("Hand" + (i+1) + " = (" + hands[i].toString() + " )");
        }
    }
    
}
