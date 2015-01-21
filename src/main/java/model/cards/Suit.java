package model.cards;

/**
 * Created by Chris on 1/9/2015.
 */
public enum Suit {
    SPADES("S"),
    HEARTS("H"),
    DIAMONDS("D"),
    CLUBS("C");

    public String suitValue;

    Suit(String suit) {
        suitValue = suit;
    }

    public static Suit fromOrdinal(int ordinal) {
        for (Suit suit : values()) {
            if (suit.ordinal() == ordinal) {
                return suit;
            }
        }

        throw new IllegalArgumentException(ordinal + " doesn't exist.");
    }
}
