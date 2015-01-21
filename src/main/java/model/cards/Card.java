package model.cards;

/**
 * Created by Chris on 1/9/2015.
 */
public class Card {

    private Suit suit;
    private Rank rank;

    public Card(String card) {
        switch (card.charAt(0)) {
            case 'A':
                rank = Rank.ACE;
                break;
            case '2':
                rank = Rank.TWO;
                break;
            case '3':
                rank = Rank.THREE;
                break;
            case '4':
                rank = Rank.FOUR;
                break;
            case '5':
                rank = Rank.FIVE;
                break;
            case '6':
                rank = Rank.SIX;
                break;
            case '7':
                rank = Rank.SEVEN;
                break;
            case '8':
                rank = Rank.EIGHT;
                break;
            case '9':
                rank = Rank.NINE;
                break;
            case 'T':
                rank = Rank.TEN;
                break;
            case 'J':
                rank = Rank.JACK;
                break;
            case 'Q':
                rank = Rank.QUEEN;
                break;
            case 'K':
                rank = Rank.KING;
                break;
        }

        switch (card.charAt(1)) {
            case 'D':
                suit = Suit.DIAMONDS;
                break;
            case 'H':
                suit = Suit.HEARTS;
                break;
            case 'S':
                suit = Suit.SPADES;
                break;
            case 'C':
                suit = Suit.CLUBS;
                break;
        }
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {

        return suit;
    }

    public String toString() {
        return rank.displayValue + suit.suitValue;
    }
}
