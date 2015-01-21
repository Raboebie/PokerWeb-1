package model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Chris on 1/9/2015.
 */
public class Hand {

    List<Card> cards = new ArrayList<Card>();

    public Hand(String c1, String c2, String c3, String c4, String c5) {
        cards.add(new Card(c1));
        cards.add(new Card(c2));
        cards.add(new Card(c3));
        cards.add(new Card(c4));
        cards.add(new Card(c5));
    }

    public Hand(Card c1, Card c2, Card c3, Card c4, Card c5){
        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        cards.add(c5);
    }

    public List<Card> getCards() {
        return cards;
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < cards.size() - 1; i++) {
            temp += cards.get(i).toString() + ",";
        }
        temp += cards.get(4).toString();
        return temp;
    }
}
