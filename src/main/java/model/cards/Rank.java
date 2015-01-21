package model.cards;

/**
 * Created by Chris on 1/9/2015.
 */
public enum Rank {
    ACE(1, "A"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9, "9"),
    TEN(10, "T"),
    JACK(11, "J"),
    QUEEN(12, "Q"),
    KING(13, "K");

    public int rankValue;
    public String displayValue;

    Rank(int rank, String display) {
        rankValue = rank;
        displayValue = display;
    }

    public static Rank fromOrdinal(int ordinal) {
        for (Rank rank : values()) {
            if (rank.ordinal() == ordinal) {
                return rank;
            }
        }

        throw new IllegalArgumentException(ordinal + " doesn't exist.");
    }
}
