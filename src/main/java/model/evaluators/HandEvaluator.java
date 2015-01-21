package model.evaluators;

import model.cards.Hand;
import model.cards.Card;
import model.cards.Rank;
import model.cards.Suit;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Chris on 1/9/2015.
 */
public class HandEvaluator {
    public static boolean isStraightFlush(Hand hand){
        List<Rank> ranks = hand.getCards().stream().map(c -> c.getRank()).collect(Collectors.toList());
        return hand.getCards().stream().allMatch(c -> c.getSuit() == hand.getCards().get(0).getSuit())
                && ranks.stream().mapToInt(r -> r.ordinal()).max().getAsInt() - ranks.stream().mapToInt(r -> r.ordinal()).min().getAsInt() == 4
                && ranks.stream().distinct().count() == 5;
    }

    public static boolean isFourOfAKind(Hand hand){
        List<Long> counted = hand.getCards().stream().collect(Collectors.groupingBy(o -> o.getRank().toString(), Collectors.counting())) //Map to <Rank, Count of Ranks>
                .values().stream().sorted().collect(Collectors.toList()); //List Count of Ranks ordered
        if(counted.size() == 2 && counted.get(1) == 4) return true;
        return false;
    }

    public static boolean isFullHouse(Hand hand){
        List<Long> counted = hand.getCards().stream().collect(Collectors.groupingBy(o -> o.getRank().toString(), Collectors.counting())) //Map to <Rank, Count of Ranks>
                .values().stream().sorted().collect(Collectors.toList()); //List Count of Ranks ordered
        if(counted.size() == 2 && counted.get(0) == 2) return true;
        return false;
    }

    public static boolean isFlush(Hand hand){
        List<Rank> ranks = hand.getCards().stream().map(c -> c.getRank()).collect(Collectors.toList());
        return hand.getCards().stream().allMatch(c -> c.getSuit() == hand.getCards().get(0).getSuit())
                && !(ranks.stream().mapToInt(r -> r.ordinal()).max().getAsInt() - ranks.stream().mapToInt(r -> r.ordinal()).min().getAsInt() == 4
                && ranks.stream().distinct().count() == 5);
    }

    public static boolean isStraight(Hand hand){
        List<Rank> ranks = hand.getCards().stream().map(c -> c.getRank()).collect(Collectors.toList());
        return ranks.stream().mapToInt(r -> r.ordinal()).max().getAsInt() - ranks.stream().mapToInt(r -> r.ordinal()).min().getAsInt() == 4
                && ranks.stream().distinct().count() == 5
                && !(hand.getCards().stream().allMatch(c -> c.getSuit() == hand.getCards().get(0).getSuit()));
    }

    public static boolean isThreeOfAKind(Hand hand){
        List<Long> counted = hand.getCards().stream().collect(Collectors.groupingBy(o -> o.getRank().toString(), Collectors.counting())) //Map to <Rank, Count of Ranks>
                .values().stream().sorted().collect(Collectors.toList()); //List Count of Ranks ordered
        if(counted.size() == 3 && counted.get(2) == 3) return true;
        return false;
    }

    public static boolean isTwoPair(Hand hand){
        List<Long> counted = hand.getCards().stream().collect(Collectors.groupingBy(o -> o.getRank().toString(), Collectors.counting())) //Map to <Rank, Count of Ranks>
                .values().stream().sorted().collect(Collectors.toList()); //List Count of Ranks ordered
        if(counted.size() == 3 && counted.get(2) == 2) return true;
        return false;
    }

    public static boolean isOnePair(Hand hand){
        List<Long> counted = hand.getCards().stream().collect(Collectors.groupingBy(o -> o.getRank().toString(), Collectors.counting())) //Map to <Rank, Count of Ranks>
                .values().stream().sorted().collect(Collectors.toList()); //List Count of Ranks ordered
        if(counted.size() == 4 && counted.get(3) == 2) return true;
        return false;
    }
}
